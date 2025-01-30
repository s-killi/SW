export interface ServiceworkerAppointmentDTO {
    departmentName: string;
    citizenName: string;
    startTime: string; // ISO 8601 format for time, e.g., "HH:mm:ss"
    date: string; // ISO 8601 format for date, e.g., "yyyy-MM-dd"
    reason: string;
}