package de.othr.easytownhall.controller;


import de.othr.easytownhall.models.dtos.ApplicationFilledDTO;
import de.othr.easytownhall.models.dtos.ApplicationInstanceDTO;
import de.othr.easytownhall.models.dtos.ApplicationInstanceShowDTO;
import de.othr.easytownhall.models.dtos.ApplicationSubmitDTO;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.application.ApplicationInstance;
import de.othr.easytownhall.models.enums.ApplicationStatus;
import de.othr.easytownhall.repositories.ApplicationFormRepository;
import de.othr.easytownhall.repositories.ApplicationInstanceRepository;
import de.othr.easytownhall.services.ApplicationInstanceService;
import de.othr.easytownhall.services.MyUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/application")
public class ApplicationInstanceController {

    private final ApplicationInstanceRepository applicationInstanceRepository;
    private final ApplicationFormRepository applicationFormRepository;
    private final ApplicationInstanceService applicationInstanceService;
    private final MyUserService myUserService;

    @PostMapping("/{id}/submit")
    public ResponseEntity<ApplicationInstanceDTO> submitApplication(
            @PathVariable Long id,
            @RequestBody ApplicationSubmitDTO filledData) {

        return ResponseEntity.ok(applicationInstanceService.saveApplicationSubmitDTOAsInstance(filledData, id));
    }

    @GetMapping("/submitted")
    public ResponseEntity<List<ApplicationInstanceShowDTO>> getSubmittedApplication(){
        return applicationInstanceService.getSubmittedApplication();
    }

    @GetMapping("/submitted/{id}")
    public ResponseEntity<ApplicationFilledDTO> getApplicationById(@PathVariable Long id){
        return applicationInstanceService.getSubApplicationById(id);
    }

    @PostMapping("/submitted/{id}/accept")
    public ResponseEntity<Map<String, String>> changeApplicationStatus(@PathVariable Long id, @RequestBody boolean isAccepted) {
        Optional<ApplicationInstance> applicationInstanceOpt = applicationInstanceRepository.findById(id);

        if (applicationInstanceOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Application instance with ID " + id + " not found."));
        }

        ApplicationInstance applicationInstance = applicationInstanceOpt.get();
        applicationInstance.setStatus(isAccepted ? ApplicationStatus.APPROVED : ApplicationStatus.REJECTED);
        applicationInstanceRepository.save(applicationInstance);

        return ResponseEntity.ok(Map.of("message", "Application status updated to " +
                (isAccepted ? "ACCEPTED" : "DENIED") + "."));
    }
    @DeleteMapping("/submitted/{id}")
    public ResponseEntity deleteApplication(@PathVariable Long id){
        Optional<ApplicationInstance> applicationInstanceOpt = applicationInstanceRepository.findById(id);
        if (applicationInstanceOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ApplicationInstance applicationInstance = applicationInstanceOpt.get();
        applicationInstance.setStatus(ApplicationStatus.CANCELED);
        applicationInstanceRepository.save(applicationInstance);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/completted/{id}/submitted")
    public ResponseEntity<List<ApplicationInstanceShowDTO>> getApplicationsForUserSubmitted(@PathVariable Long id) {
        User user;
        try {
            user = myUserService.getById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        List<ApplicationStatus> statuses = List.of(
                ApplicationStatus.APPROVED,
                ApplicationStatus.REJECTED
        );

        List<ApplicationInstanceShowDTO> result = applicationInstanceService.getApplicationForUser(user, statuses);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/completted/{id}/pending")
    public ResponseEntity<List<ApplicationInstanceShowDTO>> getApplicationsForUserPending(@PathVariable Long id) {
        User user;

        List<ApplicationStatus> statuses = List.of(
                ApplicationStatus.SUBMITTED,
                ApplicationStatus.CREATED
        );


        try {
            user = myUserService.getById(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }


        List<ApplicationInstanceShowDTO> result = applicationInstanceService.getApplicationForUser(user, statuses);

        return ResponseEntity.ok(result);
    }


}
