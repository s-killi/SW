export interface AppointmentDTO {
    departmentName: string;
    date: Date; // ISO format, e.g., '2023-12-25'
    reason: string;
    timeslotId: number;
    startTime: string; // ISO time, e.g., '14:00:00'
  }