package de.othr.easytownhall.services;

import de.othr.easytownhall.models.entities.application.ApplicationForm;
import de.othr.easytownhall.models.entities.application.FormField;
import de.othr.easytownhall.repositories.ApplicationFormRepository;
import de.othr.easytownhall.repositories.FormFieldRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationFormService {

    private final ApplicationFormRepository applicationFormRepository;

    public ApplicationForm getApplicationFormWithFields(long id, int version) {
        Optional<ApplicationForm> applicationFormOptional = applicationFormRepository
                .findByIdAndVersion(id, version);

        if (applicationFormOptional.isEmpty()) {
            throw new IllegalArgumentException("ApplicationForm with ID " + id + " and version " + version + " not found.");
        }

        // Fetches form fields by accessing the property (ensures fields are initialized if using Lazy loading)
        ApplicationForm applicationForm = applicationFormOptional.get();
        applicationForm.getFormFields().size(); // Force Hibernate to initialize lazy-loaded collection

        return applicationForm;
    }

    public ApplicationForm getLatestApplicationFormWithFields(long id) {
        Optional<ApplicationForm> applicationFormOptional = applicationFormRepository
                .findTopByIdOrderByVersionDesc(id);

        if (applicationFormOptional.isEmpty()) {
            throw new IllegalArgumentException("ApplicationForm with ID " + id + " not found.");
        }

        // Fetches form fields by accessing the property (ensures fields are initialized if using Lazy loading)
        ApplicationForm applicationForm = applicationFormOptional.get();
        applicationForm.getFormFields().size(); // Force Hibernate to initialize lazy-loaded collection

        return applicationForm;
    }


    public ApplicationForm saveApplicationForm(ApplicationForm applicationForm) {
        Long applicationNumber = applicationForm.getApplicationNumber();



        if (applicationNumber == null) {
            // Setze neue ApplicationNumber und initiale Version
            applicationForm.setApplicationNumber(this.fetchMaxApplicationNumber() + 1L);
            applicationForm.setVersion(1);
        } else {
            // Setze die nächste Version basierend auf der höchsten existierenden Version
            int nextVersion = this.fetchMaxVersionForApplicationNumber(applicationNumber) + 1;
            applicationForm.setVersion(nextVersion);
        }
        ApplicationForm toSave = this.setCorrectConnections(applicationForm);

        // Speichere das ApplicationForm in der Datenbank
        return applicationFormRepository.save(toSave);
    }

    private ApplicationForm setCorrectConnections(ApplicationForm applicationForm) {
        ApplicationForm appUpdate = applicationForm;
        List<FormField> updated = new ArrayList<>();
        for (FormField formField : applicationForm.getFormFields()) {
            formField.setAntragsformular(applicationForm);
            formField.setId(null);
            List<String> updatedDate = new ArrayList<>();
            for(String data : formField.getData()) {
                if(data != null && !data.trim().isEmpty()) {
                    updatedDate.add(data);
                }
            }
            formField.setData(updatedDate);
            updated.add(formField);
        }
        appUpdate.setFormFields(updated);
        return appUpdate;
    }


    private Integer fetchMaxVersionForApplicationNumber(Long applicationNumber) {
        return Optional.ofNullable(applicationFormRepository.getMaxVersion(applicationNumber)).orElse(0);
    }

    private Long fetchMaxApplicationNumber() {
        // Aufruf einer Service- oder Repository-Methode, um die höchste Version zu holen
        // Muss z. B. über einen @Transient-Repository-Zugriff implementiert werden
        return Optional.ofNullable(applicationFormRepository.getMaxApplicationNumber()).orElse(0L);
    }

    public List<ApplicationForm> findAllUnique(String direction, String title) {
        // Dynamische Sortierung
        Sort sort = Sort.by(direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, "title");

        // Abrufen der Daten mit Filter und Sortierung
        return applicationFormRepository.findAllApplicationsUniqueAndActiveWithTitle(title, sort);
    }



    public ApplicationForm updateApplicationForm(ApplicationForm applicationForm, Long applicationNumber) {
        ApplicationForm data = applicationFormRepository.findById(applicationNumber).orElseThrow(() -> new RuntimeException("ApplicationForm not found"));

        applicationForm.setApplicationNumber(data.getApplicationNumber());
        applicationForm.setVersion(applicationFormRepository.getMaxVersion(data.getApplicationNumber()) + 1);

        ApplicationForm toSave = this.setCorrectConnections(applicationForm);

        // Speichere das ApplicationForm in der Datenbank
        return applicationFormRepository.save(toSave);
    }

    public List<ApplicationForm> archive(Long id) {
        ApplicationForm data = applicationFormRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ApplicationForm not found with id: " + id));



        List<ApplicationForm> applications =
                applicationFormRepository.findAllByApplicationNumber(data.getApplicationNumber());

        for (ApplicationForm app : applications) {
            app.setActive(false);
        }

        return applicationFormRepository.saveAll(applications);
    }

}

