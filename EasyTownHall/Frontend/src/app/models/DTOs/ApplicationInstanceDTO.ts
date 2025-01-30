export interface ApplicationInstanceDTO {
    id: number; // Entspricht Long in Java
    submissionDate: string; // Verwende string für ISO-Datum, alternativ Date
    status: string; // Status als String
    user: {id: number, email: string};
    applicationFormName: string; // Name des Formulars
    filledFieldDTOS: {
        data: string,
        fieldName: string,
        fieldType: string,
        required: boolean
    }[]; // Liste der Formulardaten als Array von Strings
}
  