package de.othr.easytownhall.services;

import de.othr.easytownhall.exceptions.AppException;
import de.othr.easytownhall.mapper.UserMapper;
import de.othr.easytownhall.models.dtos.CredentialsDto;
import de.othr.easytownhall.models.dtos.ProfilDto;
import de.othr.easytownhall.models.dtos.SignUpDto;
import de.othr.easytownhall.models.dtos.UserDto;
import de.othr.easytownhall.models.dtos.workschedule.AppointmentDTO;
import de.othr.easytownhall.models.entities.Role;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.application.ApplicationInstance;
import de.othr.easytownhall.models.entities.appointment.TimeSlot;
import de.othr.easytownhall.models.entities.citizen.Citizen;
import de.othr.easytownhall.models.entities.newsArticle.NewsSubscription;
import de.othr.easytownhall.models.enums.RoleEnum;
import de.othr.easytownhall.repositories.*;
import de.othr.easytownhall.services.appointments.WorkscheduleService;
import de.othr.easytownhall.services.news.NewsSubscriptionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;


@RequiredArgsConstructor
@Service
public class MyUserService {
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final CitizenRepository citizenRepository;
    private final ApplicationInstanceRepository applicationInstanceRepository;
    private final EmailService emailService;
    private final FilledFormFieldRepository filledFormFieldRepository;
    private final NewsSubscriptionService newsSubscriptionService;

    @Autowired
    private WorkscheduleService workscheduleService;

    public UserDto login(CredentialsDto credentialsDto) {
        User user = usersRepository.findByEmail(credentialsDto.getEmail())
                .orElseThrow(() -> new AppException("Unkown user", HttpStatus.NOT_FOUND));
        if(passwordEncoder.matches(credentialsDto.getPassword(), user.getPassword())) {
            return userMapper.toDto(user);
        }
        throw new AppException("Wrong password", HttpStatus.BAD_REQUEST);
    }

    public UserDto register(SignUpDto signUpDto) {
        Optional<User> optionalCitizen = usersRepository.findByEmail(signUpDto.getEmail());
        if(optionalCitizen.isPresent()) {
            throw new AppException("Email already in use", HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.signUpToCitizen(signUpDto);
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Role citizenRole = roleRepository.findByName(RoleEnum.CITIZEN);
        user.setRoles(Arrays.asList(citizenRole));
        User savedUser = usersRepository.save(user);

        Citizen citizen = new Citizen();
        citizen.setUser(savedUser);
        this.citizenRepository.save(citizen);

        // Platzhalter für die E-Mail
        Map<String, String> placeholders = new HashMap<>();

        // E-Mail senden
        String subject = "Willkommen bei EasyTownHall Regensburg!";
        emailService.sendEmailTemplate(savedUser.getEmail(), subject, "emails/welcome_email.html", placeholders);



        return userMapper.toDto(savedUser);
    }

    public User updateRole(Long id, RoleEnum role) {
        Optional<User> optionalUser = usersRepository.findById(id);
        if(optionalUser.isPresent()) {
            User user = optionalUser.get();
            Role assignedRole = roleRepository.findByName(role);
            user.getRoles().add(assignedRole);
            User savedUser = usersRepository.save(user);
            return savedUser;
        }
        return null;
    }

    public UserDto findByEmail(String email) {
        User user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new AppException("Unkown user", HttpStatus.NOT_FOUND));
        user.getRoles().size();
        return userMapper.toDto(user);
    }

    public List<SimpleGrantedAuthority> getRolesAndPrivilegesByEmail(String email) {
        // Benutzer mit Rollen und Privileges laden
        List<String> roles = usersRepository.findRolesByEmail(email); // Rollen aus der Datenbank
        List<String> privileges = usersRepository.findPrivilegesByEmail(email); // Privileges aus der Datenbank

        // Authorities aus Rollen und Privileges erstellen
        return Stream.concat(
                roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)), // Rollen als Authorities
                privileges.stream().map(SimpleGrantedAuthority::new) // Privileges als Authorities
        ).collect(Collectors.toList());
    }

    public List<SimpleGrantedAuthority> getPrivilegesByEmail(String email, List<String> roles) {

        List<String> privileges = usersRepository.findPrivilegesByEmail(email); // Privileges aus der Datenbank

        // Authorities aus Rollen und Privileges erstellen
        return Stream.concat(
                roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)), // Rollen als Authorities
                privileges.stream().map(SimpleGrantedAuthority::new) // Privileges als Authorities
        ).collect(Collectors.toList());
    }

    public List<SimpleGrantedAuthority> getPrivilegesById(Long id, List<String> roles) {

        List<String> privileges = usersRepository.findPrivilegesById(id); // Privileges aus der Datenbank

        // Authorities aus Rollen und Privileges erstellen
        return Stream.concat(
                roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)), // Rollen als Authorities
                privileges.stream().map(SimpleGrantedAuthority::new) // Privileges als Authorities
        ).collect(Collectors.toList());
    }

    public User getById(long id){
        User user = usersRepository.findById(id).orElseThrow(() -> new AppException("Unkown user", HttpStatus.NOT_FOUND));
        return  user;
    }

    public User updateUser(User user){
        usersRepository.findById(user.getId())
                .orElseThrow(() -> new AppException("Unkown user", HttpStatus.NOT_FOUND));
        User saved = usersRepository.save(user);
        return saved;
    }

    public ProfilDto updateUserProzess(long id, ProfilDto profilDto){
        User existingUser = this.getById(id);

        User updatedUser = userMapper.updateUserFromProfilDto(existingUser, profilDto);

        User savedUser = this.updateUser(updatedUser);

        ProfilDto updatedProfilDto = userMapper.toProfilDto(savedUser);
        return updatedProfilDto;
    }

    public List<User> getAllUsers(){
        return usersRepository.findAll();
    }

    @Transactional
    public void deleteUser(User user) {
        if (user == null || user.getId() == null) {
            throw new IllegalArgumentException("User or User ID must not be null");
        }
        if (!usersRepository.existsById(user.getId())) {
            throw new AppException("User not found with id: " + user.getId(), HttpStatus.NOT_FOUND);
        }
        workscheduleService.deleteAllAppointmentsForUser(user.getId());
        newsSubscriptionService.unsubscribe(user.getId());
        //Jetzt funktioniert das deleten der Termine für User delete?
        //DB Transaktion direkt nacheinander Probleme oder Transaktionen async? Es ist und bleibt ein Rääääätsel
        List<AppointmentDTO> dtos = workscheduleService.getAllAppointmentsForUser(user.getId());
        this.deleteApplicationInstanceByUserId(user.getId());
        citizenRepository.deleteByUserIdQuery(user.getId());
        usersRepository.delete(user);

        // Platzhalter für die E-Mail
        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("NAME", user.getUsername() != null ? user.getUsername() : "");

        // Aktuelles Datum im Format 18.01.2025
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String currentDate = LocalDate.now().format(formatter);
        placeholders.put("DELETION_DATE", currentDate);

        // E-Mail senden
        String subject = "Dein Konto wurde gelöscht";
        emailService.sendEmailTemplate(user.getEmail(), subject, "emails/delete_email.html", placeholders);
    }


    // here to avoid circular references
    private void deleteApplicationInstanceByUserId(Long id) {
        List<ApplicationInstance> applicationInstanceList = applicationInstanceRepository.findByUser(id);
        for (ApplicationInstance a : applicationInstanceList) {
            filledFormFieldRepository.deleteAllByApplicationId(a.getId());
        }
        applicationInstanceRepository.deleteByUserIdQuery(id);
    }

}
