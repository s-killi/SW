export interface ApplicationDTO {
    id: number;
    submissionDate: string; // Use string for ISO date format in TypeScript
    status: string;
    applicationFormName: string;
}
  