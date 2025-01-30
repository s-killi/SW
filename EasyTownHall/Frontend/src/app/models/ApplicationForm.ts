import { FormField } from "./FormField";

export interface ApplicationForm {
    id: number; // Eindeutige ID des Formulars
    title: string; // Titel des Formulars
    description: string; // Beschreibung des Formulars
    version: number; // Version des Formulars
    formFields: FormField[]; // Liste der Eingabefelder
  }