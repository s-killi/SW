import { TimeSlotDto } from "./TimeSlotDTO";

export interface WorkscheduleDTO {
    id: number;
    departmentId: number;
    serviceworkerId: number;
    date: string; // ISO format, e.g., '2023-12-25'
    timeSlots: TimeSlotDto[];
  }