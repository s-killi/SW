package de.othr.easytownhall.services;

import de.othr.easytownhall.config.UsersAuthenticationProvider;
import de.othr.easytownhall.mapper.ApplicationMapper;
import de.othr.easytownhall.models.dtos.ApplicationFilledDTO;
import de.othr.easytownhall.models.dtos.ApplicationInstanceDTO;
import de.othr.easytownhall.models.dtos.ApplicationInstanceShowDTO;
import de.othr.easytownhall.models.dtos.ApplicationSubmitDTO;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.application.ApplicationForm;
import de.othr.easytownhall.models.entities.application.ApplicationInstance;
import de.othr.easytownhall.models.entities.application.FilledFormField;
import de.othr.easytownhall.models.entities.application.FormField;
import de.othr.easytownhall.models.enums.ApplicationStatus;
import de.othr.easytownhall.models.enums.FieldType;
import de.othr.easytownhall.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@AllArgsConstructor
@Service
public class ApplicationInstanceService {
    @Autowired
    private ApplicationInstanceRepository applicationInstanceRepository;
    @Autowired
    private ApplicationFormRepository applicationFormRepository;
    @Autowired
    private UsersAuthenticationProvider usersAuthenticationProvider;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private FormFieldRepository formFieldRepository;
    @Autowired
    private ApplicationMapper applicationMapper;
    @Autowired
    private FilledFormFieldRepository filledFormFieldRepository;
    @Autowired
    private EmailService emailService;


    public ApplicationInstanceDTO saveApplicationSubmitDTOAsInstance(ApplicationSubmitDTO dto, Long id) {
        String token = dto.getToken();
        int applicationId = dto.getApplicationId();
        ApplicationInstance applicationInstance = new ApplicationInstance();
        ApplicationForm applicationForm = applicationFormRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ApplicationForm mit ID " + applicationId + " nicht gefunden." + dto.toString())
        );
        applicationInstance.setApplicationForm(applicationForm);
        LocalDate date = LocalDate.now();
        applicationInstance.setSubmissionDate(date);
        applicationInstance.setStatus(ApplicationStatus.SUBMITTED);

        Long idUser = usersAuthenticationProvider.getIdFromToken(token);
        User user = usersRepository.findById(idUser).orElse(null);
        applicationInstance.setUser(user);

        List<FilledFormField> collectFilledFields = new ArrayList<>();
        for (Map.Entry<String, Object> entry : dto.getData().entrySet()) {
            String key = entry.getKey();        // FormFieldID
            Long key_int = Long.parseLong(key);

            FormField formField = formFieldRepository.getReferenceById(key_int);
            FilledFormField filledFormField = new FilledFormField();
            filledFormField.setApplicationInstance(applicationInstance);
            filledFormField.setFormField(formField);
            String value;
            if(formField.getFieldType() == FieldType.CHECKBOX){
                List<String> values = (List<String>) entry.getValue();
                value = String.join("#;#", values);
            }else{
                if(formField.getFieldType() == FieldType.NUMBER){
                    value = String.valueOf(entry.getValue());
                }else {
                    value = (String) entry.getValue();
                }
            }
            filledFormField.setData(value);

            collectFilledFields.add(filledFormField);
        }
        applicationInstance.setFilledFields(collectFilledFields);
        ApplicationInstance saved = applicationInstanceRepository.save(applicationInstance);


        Map<String, String> placeholders = new HashMap<>();
        placeholders.put("NAME", user.getUsername() != null ? user.getUsername() : "");
        Long antragsId = saved.getId();
        String applicationLink = "localhost:4200/application/service/review/" + antragsId.toString();
        placeholders.put("APPLICATION_LINK", applicationLink);

        emailService.sendEmailTemplate(
                user.getEmail(),
                "Dein Antrag ist eingegangen",
                "emails/application_confirmation_email.html",
                placeholders
        );

        return convertToDTO(saved);
    }

    public ApplicationInstanceDTO convertToDTO(ApplicationInstance instance) {
        ApplicationInstanceDTO dto = new ApplicationInstanceDTO();
        dto.setId(instance.getId());
        dto.setSubmissionDate(instance.getSubmissionDate());
        dto.setStatus(instance.getStatus().name());
        dto.setApplicationFormName(instance.getApplicationForm().getTitle());

        List<String> formData = instance.getFilledFields()
                .stream()
                .map(f -> f.getFormField().getFieldName() + ": " + f.getData())
                .collect(Collectors.toList());
        dto.setFilledFormData(formData);

        return dto;
    }


    public ResponseEntity<List<ApplicationInstanceShowDTO>> getSubmittedApplication() {
        List<ApplicationInstance> submittedApplications =
                applicationInstanceRepository.findByStatus(ApplicationStatus.SUBMITTED);

        List<ApplicationInstanceShowDTO> dtos = new ArrayList<>();

        for(ApplicationInstance a : submittedApplications){
            dtos.add(applicationMapper.toApplicationInstanceShowDTO(a));
        }


        return ResponseEntity.ok(dtos);
    }

    public ResponseEntity<ApplicationFilledDTO> getSubApplicationById(Long id) {
        ApplicationInstance applicationInstance = applicationInstanceRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("ApplicationInstance mit ID " + id + " nicht gefunden.")
        );

        return ResponseEntity.ok(applicationMapper.toApplicationFilledDTO(applicationInstance));
    }


    public ApplicationInstance changeApplicationStatus(Long id, boolean isAccepted) {
        ApplicationInstance applicationInstance = applicationInstanceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Application instance with ID " + id + " not found."));

        // Update the status based on the isAccepted flag
        applicationInstance.setStatus(isAccepted ? ApplicationStatus.APPROVED : ApplicationStatus.REJECTED);

        // Save the updated instance
        return applicationInstanceRepository.save(applicationInstance);
    }

    public List<ApplicationInstanceShowDTO> getApplicationForUser(User user, List<ApplicationStatus> statuses) {
        List<ApplicationInstance> list = this.applicationInstanceRepository.findAllByStatusInAndUser(statuses, user);

        List<ApplicationInstanceShowDTO> dtos = new ArrayList<>();
        for (ApplicationInstance a : list) {
            dtos.add(applicationMapper.toApplicationInstanceShowDTO(a));
        }
        return dtos;
    }





}
