package de.othr.easytownhall.config;

import de.othr.easytownhall.models.entities.Privilege;
import de.othr.easytownhall.models.entities.Role;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.Workschedule;
import de.othr.easytownhall.models.entities.application.ApplicationForm;
import de.othr.easytownhall.models.entities.application.FormField;
import de.othr.easytownhall.models.entities.appointment.TimeSlot;
import de.othr.easytownhall.models.enums.FieldType;
import de.othr.easytownhall.repositories.ApplicationFormRepository;
import de.othr.easytownhall.repositories.UsersRepository;
import de.othr.easytownhall.repositories.PrivilegeRepository;
import de.othr.easytownhall.repositories.RoleRepository;
import de.othr.easytownhall.services.ApplicationFormService;

import de.othr.easytownhall.models.entities.citizen.Citizen;
import de.othr.easytownhall.models.entities.department.Department;
import de.othr.easytownhall.models.entities.department.ServiceWorker;
import de.othr.easytownhall.models.enums.Gender;
import de.othr.easytownhall.models.enums.PriviligeEnum;
import de.othr.easytownhall.models.enums.RoleEnum;
import de.othr.easytownhall.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ApplicationFormRepository applicationFormRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ApplicationFormService applicationFormService;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CitizenRepository citizenRepository;
    @Autowired
    private ServiceWorkerRepository serviceWorkerRepository;
    @Autowired
    private WorkscheduleRepository workscheduleRepository;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(alreadySetup) {
            return;
        }
        Privilege readPrivilege = createPrivilegeIfNotFound(PriviligeEnum.READ_PRIVILEGE);
        Privilege writePrivilege = createPrivilegeIfNotFound(PriviligeEnum.WRITE_PRIVILEGE);

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound(RoleEnum.ADMIN, adminPrivileges);
        createRoleIfNotFound(RoleEnum.SERVICEWORKER, adminPrivileges);
        createRoleIfNotFound(RoleEnum.CITIZEN, Arrays.asList(readPrivilege));

        // Setup admin
        Role adminRole = roleRepository.findByName(RoleEnum.ADMIN);
        User admin = new User();
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setEmail("admin@admin.com");
        admin.setActive(true);
        admin.setRoles(Arrays.asList(adminRole));
        usersRepository.save(admin);

        // Setup ServiceWorker
        Role serviceWorkerRole = roleRepository.findByName(RoleEnum.SERVICEWORKER);
        User serviceWorker = new User();
        serviceWorker.setPassword(passwordEncoder.encode("serviceworker"));
        serviceWorker.setEmail("sw@sw.com");
        serviceWorker.setActive(true);
        serviceWorker.setRoles(Arrays.asList(serviceWorkerRole));
        usersRepository.save(serviceWorker);

        // Setup ServiceWorker
        User serviceWorker2 = new User();
        serviceWorker2.setPassword(passwordEncoder.encode("serviceworker"));
        serviceWorker2.setEmail("sw2@sw2.com");
        serviceWorker2.setActive(true);
        serviceWorker2.setRoles(Arrays.asList(serviceWorkerRole));
        usersRepository.save(serviceWorker2);

        //Department for Serviceworker
        Department department = new Department();
        department.setName("Führerscheinstelle");

        ServiceWorker sw = new ServiceWorker();
        sw.setFirstname("David");
        sw.setLastname("Hinke");
        sw.setUser(serviceWorker);
        sw.setDepartment(department);

        ServiceWorker sw2 = new ServiceWorker();
        sw2.setFirstname("Timmy");
        sw2.setLastname("Test");
        sw2.setUser(serviceWorker2);

        //serviceWorkerRepository.save(sw2);


        //Weiteres Department
        Department bürgerBüro = new Department();
        bürgerBüro.setName("Bürgerbüro");
        sw2.setDepartment(bürgerBüro);


        // Setup Citizen
        Role citizenRole = roleRepository.findByName(RoleEnum.CITIZEN);
        User citizen = new User();
        citizen.setPassword(passwordEncoder.encode("citizen"));
        citizen.setEmail("citizen@citizen.com");
        citizen.setActive(true);
        citizen.setRoles(Arrays.asList(citizenRole));
        usersRepository.save(citizen);

        // sample antrag
        createSampleAntrag(1);
        createSampleAntrag(2);

        Citizen actualCitizen = new Citizen();
        actualCitizen.setUser(citizen);
        actualCitizen.setFirstName("John");
        actualCitizen.setLastName("Smith");
        actualCitizen.setBirthDate(LocalDate.now());
        actualCitizen.setGender(Gender.FEMALE);
        citizenRepository.save(actualCitizen);

        //Dummy Workschedule
        Workschedule workschedule = new Workschedule();
        workschedule.setTimeSlots(TimeSlot.initializeTimeSlots());
        workschedule.setDate(LocalDate.of(2024, 11, 1));
        workschedule.setDepartment(department);
        sw.getWorkschedules().add(workschedule);

        //alles speichern
        serviceWorkerRepository.save(sw);
        serviceWorkerRepository.save(sw2);
        workscheduleRepository.save(workschedule);
        departmentRepository.save(department);
        departmentRepository.save(bürgerBüro);

        alreadySetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(PriviligeEnum name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            RoleEnum name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    private void createSampleAntrag(int version){
        ApplicationForm exampleForm = new ApplicationForm();
        exampleForm.setTitle("Bürgerausweis beantragen " + version);
        exampleForm.setDescription("Formular für die Beantragung eines neuen Bürgerausweises.");
        exampleForm.setApplicationNumber(1L);

        FormField firstNameField = new FormField();
        firstNameField.setFieldName("Vorname");
        firstNameField.setFieldType(FieldType.TEXT);
        firstNameField.setRequired(true);
        firstNameField.setLabelHelp("Hier bitte vorname eingeben");
        firstNameField.setDefaultValue("Vorname");
        firstNameField.setAntragsformular(exampleForm);

        FormField lastNameField = new FormField();
        lastNameField.setFieldName("Nachname");
        lastNameField.setFieldType(FieldType.TEXT);
        lastNameField.setRequired(true);
        lastNameField.setAntragsformular(exampleForm);

        FormField dobField = new FormField();
        dobField.setFieldName("Geburtsdatum");
        dobField.setFieldType(FieldType.DATE);
        dobField.setRequired(true);
        dobField.setAntragsformular(exampleForm);

        FormField idTypeField = new FormField();
        idTypeField.setFieldName("Art des Ausweises");
        idTypeField.setFieldType(FieldType.DROPDOWN);

        List<String> dropdownmenu = new ArrayList<>();
        dropdownmenu.add("Reisepass");
        dropdownmenu.add("Visa");
        dropdownmenu.add("Schlagmichtot");
        idTypeField.setData(dropdownmenu);
        idTypeField.setRequired(true);
        idTypeField.setAntragsformular(exampleForm);

        FormField upload = new FormField();
        upload.setFieldName("Dokument");
        upload.setFieldType(FieldType.FILE_UPLOAD);
        upload.setRequired(true);
        upload.setAntragsformular(exampleForm);


        FormField checkbox = new FormField();
        checkbox.setFieldName("Checkbox");
        checkbox.setFieldType(FieldType.CHECKBOX);
        checkbox.setRequired(true);
        List<String> checkboxmenu = new ArrayList<>();
        checkboxmenu.add( "Reisepass");
        checkboxmenu.add( "Visa");
        checkboxmenu.add( "Schlagmichtot");
        checkbox.setData(checkboxmenu);
        checkbox.setAntragsformular(exampleForm);

        FormField number = new FormField();
        number.setFieldName("Number");
        number.setFieldType(FieldType.NUMBER);
        number.setRequired(true);
        number.setAntragsformular(exampleForm);

        FormField radio = new FormField();
        radio.setFieldName("Radio");
        radio.setFieldType(FieldType.RADIO_BUTTON);
        radio.setRequired(true);
        List<String> radiomenu = new ArrayList<>();
        radiomenu.add("Reisepass");
        radiomenu.add("Visa");
        radiomenu.add("Schlagmichtot");
        radio.setData(radiomenu);
        radio.setAntragsformular(exampleForm);

        exampleForm.setFormFields(Arrays.asList(firstNameField, lastNameField, dobField, idTypeField, upload, checkbox, number, radio));
        applicationFormService.saveApplicationForm(exampleForm);

        System.out.println("Beispielantrag wurde erfolgreich erstellt!");
    }
}
