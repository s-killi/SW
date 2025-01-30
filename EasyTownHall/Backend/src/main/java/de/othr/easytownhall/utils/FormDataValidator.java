package de.othr.easytownhall.utils;


import de.othr.easytownhall.models.entities.application.ApplicationForm;
import de.othr.easytownhall.models.entities.application.FilledFormData;
import de.othr.easytownhall.models.entities.application.FormField;
import de.othr.easytownhall.models.enums.FieldType;

public class FormDataValidator {

    public static boolean validate(ApplicationForm form, FilledFormData data) {
        for (FormField field : form.getFormFields()) {
            Object value = data.getFieldValues().get(field.getFieldName());

            if (field.isRequired() && (value == null || value.toString().isEmpty())) {
                return false; // Pflichtfeld fehlt
            }
            // Zusätzliche Validierungen, z.B. Datentypprüfung, Wertebereich
            if (field.getFieldType().equals(FieldType.NUMBER) && !(value instanceof Number)) {
                return false;
            }
            // Weitere Prüfungen je nach FieldType...
        }
        return true;
    }
}