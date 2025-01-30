  import { FieldType } from "../enums/FieldType";


export interface FormFieldDTO {
    fieldName: string; // Der Name des Feldes
    fieldType: FieldType; // Typ des Feldes (z. B. TEXT, NUMBER, etc.)
    isRequired: boolean; // Gibt an, ob das Feld obligatorisch ist
    defaultValue?: string; // Standardwert für das Feld (optional)
    labelHelp?: string; // Hilfstext für das Feld (optional)
    data?: Map<string, string>;
  }