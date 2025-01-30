export interface TimeSlotDto {
    timeslotId: number;
    startTime: string; // ISO time, e.g., '14:00:00'
    available: boolean;
  }