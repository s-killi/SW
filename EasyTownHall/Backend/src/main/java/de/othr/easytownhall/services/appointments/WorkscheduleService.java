package de.othr.easytownhall.services.appointments;

import de.othr.easytownhall.mapper.WorkscheduleMapper;
import de.othr.easytownhall.models.dtos.workschedule.AppointmentDTO;
import de.othr.easytownhall.models.dtos.workschedule.ServiceworkerAppointmentDTO;
import de.othr.easytownhall.models.dtos.workschedule.SlotRequestDTO;
import de.othr.easytownhall.models.dtos.workschedule.WorkscheduleDTO;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.Workschedule;
import de.othr.easytownhall.models.entities.appointment.TimeSlot;
import de.othr.easytownhall.models.entities.citizen.Citizen;
import de.othr.easytownhall.models.entities.department.Department;
import de.othr.easytownhall.models.entities.department.ServiceWorker;
import de.othr.easytownhall.repositories.*;
import de.othr.easytownhall.services.DepartmentService;
import de.othr.easytownhall.services.EmailService;
import de.othr.easytownhall.services.MyUserService;
import de.othr.easytownhall.services.TemplateService;
import jakarta.transaction.Transactional;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkscheduleService {

    @Autowired
    private WorkscheduleRepository workscheduleRepository;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private WorkscheduleMapper workscheduleMapper;
    @Autowired
    private CitizenRepository citizenRepository;
    @Autowired
    private TimeSlotRepository timeSlotRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ServiceWorkerRepository serviceWorkerRepository;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private UsersRepository usersRepository;


    public AppointmentDTO getAppointmentByTimeslotId(Long timeslotId) {
        Optional<TimeSlot> slot = timeSlotRepository.findById(timeslotId);
        if (slot.isPresent()) {
            AppointmentDTO appointmentDTO = new AppointmentDTO();
            appointmentDTO.setReason(slot.get().getReason());
            appointmentDTO.setStartTime(slot.get().getStartTime());
            appointmentDTO.setTimeslotId(timeslotId);
            return appointmentDTO;
        }
        return null;
    }

    public List<Workschedule> getAllWorkschedules(LocalDate date, Long depId) {
        List<Workschedule> workschedules = workscheduleRepository.findAllByDateAndDepartment(date, depId);
        return workschedules;
    }

    public WorkscheduleDTO getResponseSchedule(LocalDate date, Long depId) {
        Department department = departmentService.getDepartmentById(depId);
        List<Workschedule> schedules = getAllWorkschedules(date, depId);
        List<ServiceWorker> workers = departmentService.getAllServiceWorkers(depId);
        if (workers.isEmpty()) {
            return null;
        }
        if (schedules.isEmpty()) {
            Long swId = null;

            Workschedule freeSchedule = null;
            for (ServiceWorker worker : workers) {
                freeSchedule = new Workschedule();
                freeSchedule.setDate(date);
                freeSchedule.setTimeSlots(TimeSlot.initializeTimeSlots());
                freeSchedule.setDepartment(department);
                swId = worker.getId();
                worker.getWorkschedules().add(freeSchedule); // dem serviceworker den Terminplan hinzufügen
                workscheduleRepository.save(freeSchedule);
            }
                return workscheduleMapper.toDto(freeSchedule, swId);

        }

        //momentan wird einfach das erste returned. //TODO Datenstruktur müsste komplett geändert werden das Timeslots SW id enthält
        for (ServiceWorker worker : workers) {
           List<Workschedule> workerSchedules = worker.getWorkschedules();
           for (Workschedule workschedule : workerSchedules) {
               if(workschedule.getDate().equals(date)) {
                   return workscheduleMapper.toDto(workschedule, worker.getId());
               }
           }
        }
        return null;
    }

    public List<Workschedule> getAll(){
        return workscheduleRepository.findAll();
    }

    //Methode zum time slot updaten
    public void updateTimeSlotBooking(SlotRequestDTO slotRequest) {
        Optional<Citizen> optionalCitizen = citizenRepository.findByUserId(slotRequest.getUserId());
        Optional<TimeSlot> slot = timeSlotRepository.findById(slotRequest.getTimeslotId());
        if(slot.isPresent() && optionalCitizen.isPresent()) {
            TimeSlot timeSlot = slot.get();
            Citizen citizen = optionalCitizen.get();

            if(timeSlot.isAvailable()){
                timeSlot.setAvailable(false);
                timeSlot.setReason(slotRequest.getReason());
                timeSlot.setCitizen(citizen);
                timeSlotRepository.save(timeSlot);
                return;
            }
        }

        throw new IllegalArgumentException("TimeSlot mit ID " + slotRequest.getTimeslotId() + " nicht gefunden.");
    }

    public Workschedule getWorkscheduleById(Long workScheduleId) {
        return workscheduleRepository.findById(workScheduleId).orElse(null);
    }

    public List<AppointmentDTO> getAllAppointmentsForUser(Long userId){
        Optional<Citizen> optCitizen = citizenRepository.findByUserId(userId);
        List<AppointmentDTO> appointments = new ArrayList<>();
        if(optCitizen.isPresent()){
            Citizen citizen = optCitizen.get();
            List<TimeSlot> slots = timeSlotRepository.findAllByCitizenId(citizen.getId());
            for (TimeSlot timeSlot : slots) {
                AppointmentDTO appointment = new AppointmentDTO();
                appointment.setTimeslotId(timeSlot.getId());
                appointment.setStartTime(timeSlot.getStartTime());
                appointment.setReason(timeSlot.getReason());
                Optional<Workschedule> optWs = workscheduleRepository.findByTimeSlotId(timeSlot.getId());
                if(optWs.isPresent()){
                    Workschedule ws = optWs.get();
                    appointment.setDate(ws.getDate());
                    appointment.setDepartmentName(ws.getDepartment().getName());
                }
                appointments.add(appointment);
            }
        }
        return appointments;
    }

    @Transactional
    public void deleteAllAppointmentsForUser(Long userId){
        Optional<Citizen> optCitizen = citizenRepository.findByUserId(userId);
        if(optCitizen.isPresent()){
            Citizen citizen = optCitizen.get();
            List<TimeSlot> slots = timeSlotRepository.findAllByCitizenId(citizen.getId());
            for (TimeSlot timeSlot : slots) {
                timeSlot.setAvailable(true);
                timeSlot.setReason(null);
                timeSlot.setCitizen(null);
            }
            timeSlotRepository.saveAll(slots);
        }
    }

    public boolean cancelAppointment(Long timeSlotId) {
        if(timeSlotRepository.findById(timeSlotId).isEmpty()){
            return false;
        }
        timeSlotRepository.findById(timeSlotId).ifPresent(timeSlot -> {
           timeSlot.setAvailable(true);
           timeSlot.setReason(null);
           timeSlot.setCitizen(null);
           timeSlotRepository.save(timeSlot);
        });
        return true;
    }

    public void sendConfirmationEmail(SlotRequestDTO slotRequestDTO){
        Optional<Citizen> optCitizen = citizenRepository.findByUserId(slotRequestDTO.getUserId());
        Optional<User> userOpt = usersRepository.findById(slotRequestDTO.getUserId());
        Optional<TimeSlot> optTimeslot = timeSlotRepository.findById(slotRequestDTO.getTimeslotId());
        Optional<Workschedule> ws = workscheduleRepository.findByTimeSlotId(slotRequestDTO.getTimeslotId());
        if(optTimeslot.isPresent() && ws.isPresent() && userOpt.isPresent()){
            User user = userOpt.get();
            StringBuilder htmlBuilder = new StringBuilder();
            if(optCitizen.isPresent()){
                Citizen citizen = optCitizen.get();
                if(citizen.getFirstName() != null && citizen.getLastName() != null){
                    htmlBuilder
                            .append("<h1>").append("Hallo " + citizen.getFirstName() +  " " + citizen.getLastName() + ",").append("</h1>");

                }
                else  {
                    htmlBuilder
                            .append("<h1>").append("Hallo Bürger,").append("</h1>");
                }
            }


            TimeSlot timeSlot = optTimeslot.get();
            Workschedule workschedule = ws.get();
            LocalDate date = workschedule.getDate();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd. MMMM. yyyy");
            String formattedDate = date.format(formatter);

            htmlBuilder
                    .append("<p>").append("Sie haben erfolgreich einen Termin beantragt.").append("</p>")
                    .append("<p>").append("Kommen Sie bitte am " + formattedDate + " um " + timeSlot.getStartTime() + " zu: " + workschedule.getDepartment().getName()).append("</p>")
                    .append("<p>").append("Mit freundlichen Grüßen").append("</p>")
                    .append("<p>").append("Ihr Rathaus Regensburg").append("</p>");
            String body = htmlBuilder.toString();
            String header = "Terminbestätigung";
            String template = templateService.loadTemplateSubscription("emails/email_template.html");

            String templateBody = template
                    .replace("{{header}}", header)
                    .replace("{{body}}", body);

            emailService.sendEmail(user.getEmail(),"Terminbestätigung", templateBody);
        }
    }

    public List<ServiceworkerAppointmentDTO> getAllAppointmentsForServiceworker(Long userId) {
        Optional<ServiceWorker> swOpt = serviceWorkerRepository.findServiceWorkerByUserId(userId);
        List<ServiceworkerAppointmentDTO> appointments = new ArrayList<>();
        if(swOpt.isPresent()){
            ServiceWorker sw = swOpt.get();
            List<Workschedule> schedules = serviceWorkerRepository.findAllWorkschedulesByServiceWorkerId(sw.getId());

            for (Workschedule workschedule : schedules) {
                for(TimeSlot timeSlot : workschedule.getTimeSlots()) {
                    if(!timeSlot.isAvailable()){
                        ServiceworkerAppointmentDTO appointment = new ServiceworkerAppointmentDTO();
                        appointment.setDate(workschedule.getDate());
                        appointment.setDepartmentName(workschedule.getDepartment().getName());
                        appointment.setStartTime(timeSlot.getStartTime());
                        appointment.setCitizenName(timeSlot.getCitizen().getFirstName() + " " + timeSlot.getCitizen().getLastName());
                        appointment.setReason(timeSlot.getReason());
                        appointments.add(appointment);
                    }
                }
            }
        }
        return appointments;
    }
}
