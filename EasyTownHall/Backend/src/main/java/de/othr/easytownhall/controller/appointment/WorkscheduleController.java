package de.othr.easytownhall.controller.appointment;

import de.othr.easytownhall.models.dtos.workschedule.AppointmentDTO;
import de.othr.easytownhall.models.dtos.workschedule.ServiceworkerAppointmentDTO;
import de.othr.easytownhall.models.dtos.workschedule.SlotRequestDTO;
import de.othr.easytownhall.models.dtos.workschedule.WorkscheduleDTO;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.Workschedule;
import de.othr.easytownhall.models.entities.department.Department;
import de.othr.easytownhall.services.DepartmentService;
import de.othr.easytownhall.services.EmailService;
import de.othr.easytownhall.services.MyUserService;
import de.othr.easytownhall.services.appointments.WorkscheduleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("api/v1/workschedule")
public class WorkscheduleController {

    @Autowired
    private WorkscheduleService workscheduleService;
    @Autowired
    private DepartmentService departmentService;

    private static final Logger logger = LoggerFactory.getLogger(WorkscheduleController.class);
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private EmailService emailService;


    @PostMapping("/checkSlots/{depId}")
    public ResponseEntity<WorkscheduleDTO> checkSlots(@PathVariable Long depId, @RequestBody LocalDate date) {
        WorkscheduleDTO scheduleDto = workscheduleService.getResponseSchedule(date, depId);
        if(scheduleDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(scheduleDto);
    }

    @GetMapping("/{depId}")
    public ResponseEntity<List<Workschedule>> getAllWorkschedules(@PathVariable Long depId, @RequestBody LocalDate date) {
       return ResponseEntity.ok(workscheduleService.getAllWorkschedules(date, depId));
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<Department> getAllDepartmentWorkschedules(@PathVariable Long id) {
        return  ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @GetMapping()
    public ResponseEntity<List<Workschedule>> getAll() {
        return ResponseEntity.ok(workscheduleService.getAll());
    }

    //TODO wenn im frontend die form abgebrochen wird. Den potenziell erstellten Workschedule löschen
    @PostMapping("/deleteLastWorkschedule")
    public ResponseEntity<WorkscheduleDTO> deleteLastWorkschedule(@RequestBody SlotRequestDTO slotsRequestDTO) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bookTimeslot")
    public ResponseEntity<Void> bookTimeslot(@RequestBody SlotRequestDTO slotRequest) {
        try {
            workscheduleService.updateTimeSlotBooking(slotRequest);
            workscheduleService.sendConfirmationEmail(slotRequest);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("getAllAppointments/{userId}")
    public ResponseEntity<List<AppointmentDTO>> getAllAppointmentsForUser(@PathVariable Long userId) {
        List<AppointmentDTO> appointments = workscheduleService.getAllAppointmentsForUser(userId);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("getServiceworkerAppointments/{userId}")
    public ResponseEntity<List<ServiceworkerAppointmentDTO>> getAllAppointmentsForSerciceworker(@PathVariable Long userId) {
            List<ServiceworkerAppointmentDTO> appointments = workscheduleService.getAllAppointmentsForServiceworker(userId);
            if(appointments.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(appointments);
    }


    @GetMapping("/getAppointment/{timeslotId}")
    public ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Long timeslotId) {
       AppointmentDTO appointment = workscheduleService.getAppointmentByTimeslotId(timeslotId);
       if(appointment != null) {
           return ResponseEntity.ok(appointment);
       }
       return ResponseEntity.notFound().build();
    }

    @GetMapping("/cancelAppointment/{timeSlotId}")
    public ResponseEntity<Boolean> cancelAppointment(@PathVariable Long timeSlotId) {
        return ResponseEntity.ok(workscheduleService.cancelAppointment(timeSlotId));
    }

    //update appointment: endpunkt cancel appointment rufen -> alter termin gelöscht und dann wieder checkSlots endpoint
    //und /bookTimeslot endpunkt um einen neuen zu buchen

}
