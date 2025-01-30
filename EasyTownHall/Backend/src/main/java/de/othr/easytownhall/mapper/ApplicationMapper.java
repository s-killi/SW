package de.othr.easytownhall.mapper;

import de.othr.easytownhall.models.dtos.ApplicationFilledDTO;
import de.othr.easytownhall.models.dtos.ApplicationInstanceDTO;
import de.othr.easytownhall.models.dtos.ApplicationInstanceShowDTO;
import de.othr.easytownhall.models.dtos.FilledFieldDTO;
import de.othr.easytownhall.models.entities.application.ApplicationInstance;
import de.othr.easytownhall.models.entities.application.FilledFormField;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ApplicationMapper {
    private final UserMapper userMapper;

    public ApplicationMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public ApplicationInstanceShowDTO toApplicationInstanceShowDTO(ApplicationInstance applicationInstance) {
        ApplicationInstanceShowDTO applicationInstanceShowDTO = new ApplicationInstanceShowDTO();
        applicationInstanceShowDTO.setId(applicationInstance.getId());
        applicationInstanceShowDTO.setApplicationFormName(applicationInstance.getApplicationForm().getTitle());
        applicationInstanceShowDTO.setStatus(applicationInstance.getStatus().toString());
        applicationInstanceShowDTO.setSubmissionDate(applicationInstance.getSubmissionDate());
        return applicationInstanceShowDTO;
    }

    public ApplicationFilledDTO toApplicationFilledDTO(ApplicationInstance applicationInstance) {
        ApplicationFilledDTO applicationFilledDTO = new ApplicationFilledDTO();
        applicationFilledDTO.setId(applicationInstance.getId());
        applicationFilledDTO.setApplicationFormId(applicationInstance.getApplicationForm().getId());
        applicationFilledDTO.setUser(userMapper.toDto(applicationInstance.getUser()));
        applicationFilledDTO.setApplicationFormName(applicationInstance.getApplicationForm().getTitle());
        applicationFilledDTO.setStatus(applicationInstance.getStatus());
        applicationFilledDTO.setSubmissionDate(applicationInstance.getSubmissionDate());
        List<FilledFieldDTO> filledFieldDTOS = new ArrayList<>();
        for(FilledFormField fff : applicationInstance.getFilledFields()){
            filledFieldDTOS.add(toFilledFieldDTo(fff));
        }
        applicationFilledDTO.setFilledFieldDTOS(filledFieldDTOS);
        return applicationFilledDTO;
    }

    public FilledFieldDTO toFilledFieldDTo(FilledFormField filledFormField) {
        FilledFieldDTO filledFieldDTO = new FilledFieldDTO();
        filledFieldDTO.setFieldName(filledFormField.getFormField().getFieldName());
        filledFieldDTO.setFieldType(filledFormField.getFormField().getFieldType());
        filledFieldDTO.setData(filledFormField.getData());
        filledFieldDTO.setRequired(filledFormField.getFormField().isRequired());
        return filledFieldDTO;
    }
}
