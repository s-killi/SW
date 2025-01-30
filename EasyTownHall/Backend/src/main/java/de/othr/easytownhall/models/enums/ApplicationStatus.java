package de.othr.easytownhall.models.enums;

import jakarta.persistence.Entity;

public enum ApplicationStatus {
    CREATED,         // The application has been created but not submitted.
    SUBMITTED,       // The application has been submitted and is awaiting review.
    //UNDER_REVIEW,    // The application is currently being reviewed.
    //QUESTIONS,       // There are questions or issues that need to be resolved by the applicant.
    //UPDATED,         // The application has been updated and resubmitted after questions.
    APPROVED,        // The application has been reviewed and approved.
    REJECTED,        // The application has been reviewed and rejected.
    //COMPLETED,       // The application process has been successfully completed.
    CANCELED
}
