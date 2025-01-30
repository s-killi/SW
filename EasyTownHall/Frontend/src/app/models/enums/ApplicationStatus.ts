export enum ApplicationStatus {
    /**
     * The application has been created but not submitted.
     */
    CREATED = 'CREATED',
  
    /**
     * The application has been submitted and is awaiting review.
     */
    SUBMITTED = 'SUBMITTED',
  
    /**
     * The application has been reviewed and approved.
     */
    APPROVED = 'APPROVED',
  
    /**
     * The application has been reviewed and rejected.
     */
    REJECTED = 'REJECTED',
  
    /**
     * The application has been canceled.
     */
    CANCELED = 'CANCELED',
  }
  