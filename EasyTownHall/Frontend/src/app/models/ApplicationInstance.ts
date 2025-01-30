import { ApplicationForm } from "./ApplicationForm";

export interface ApplicationInstance {
    id: number; // Eindeutige ID der Instanz
    applicationForm: ApplicationForm; // Referenz auf das zugehörige Formular
    user: { id: number; username: string }; // Benutzer, der die Instanz erstellt hat
    submissionDate: string; // Datum der Einreichung (ISO-String)
    status: string; // Status des Antrags (z. B. SUBMITTED, APPROVED, etc.)
    filledData: { [key: string]: any }; // Die ausgefüllten Felder als Key-Value-Paare
  }