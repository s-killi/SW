import { FormField } from "../FormField";

export interface ApplicationUpdateDTO {
    title: string; // Titel des Formulars
    description: string; // Beschreibung des Formulars
    version: number; // Version des Formulars
    formFields: FormField[]; // Liste der Eingabefelder
  }