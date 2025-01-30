import { FieldType } from "./enums/FieldType";

export interface FormField {
  id: number;
  fieldName: string; // Der Name des Feldes
  fieldType: FieldType; // Typ des Feldes (z. B. TEXT, NUMBER, etc.)
  isRequired: boolean; // Gibt an, ob das Feld obligatorisch ist
  defaultValue?: string; // Standardwert für das Feld (optional)
  labelHelp?: string; // Hilfstext für das Feld (optional)
  data: string[]; // Array von Strings für die Daten
}