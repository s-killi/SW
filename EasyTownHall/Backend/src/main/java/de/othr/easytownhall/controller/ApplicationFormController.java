package de.othr.easytownhall.controller;

import de.othr.easytownhall.models.entities.application.ApplicationForm;
import de.othr.easytownhall.services.ApplicationFormService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // Wichtig für den Logger
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/application-forms")
public class ApplicationFormController {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationFormController.class);

    private final ApplicationFormService applicationFormService;

    @GetMapping
    public List<ApplicationForm> getApplicationFormsParams(
            @RequestParam(defaultValue = "asc") String direction,
            @RequestParam(name = "titleLike", defaultValue = "") String title
    ) {
        if (!direction.equalsIgnoreCase("asc") && !direction.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException("Invalid sort direction. Use 'asc' or 'desc'.");
        }

        return applicationFormService.findAllUnique(direction, title);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationForm> getApplicationForm(@PathVariable Long id) {
        try {
            ApplicationForm applicationForm = applicationFormService.getLatestApplicationFormWithFields(id);
            return ResponseEntity.ok(applicationForm);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApplicationForm> createForm(@RequestBody ApplicationForm form) {
        // Inhalt des Objekts ausgeben
        logger.info("Received ApplicationForm: {}", form);

        // Falls du mehr Details möchtest, könntest du einzelne Felder ausgeben oder eine JSON-Repräsentation:
        // logger.debug("Department: {}", form.getDepartment());
        // logger.debug("FormFields: {}", form.getFormFields());

        // Objekt speichern
        ApplicationForm savedForm = applicationFormService.saveApplicationForm(form);
        return ResponseEntity.ok(savedForm);
    }

    @PutMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<ApplicationForm> updateForm(@PathVariable Long id, @RequestBody ApplicationForm form) {
        ApplicationForm updatedForm = applicationFormService.updateApplicationForm(form, id);
        return ResponseEntity.ok(updatedForm);
    }

    @PutMapping("/archive/{id}")
    public List<ApplicationForm> archiveForm(@PathVariable Long id, @RequestBody ApplicationForm form) {
        return applicationFormService.archive(id);
    }
}
