package de.othr.easytownhall.mapper;


import de.othr.easytownhall.models.dtos.CitizenProfilDTO;
import de.othr.easytownhall.models.entities.citizen.Citizen;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class CitizenMapper {

    public CitizenProfilDTO toDto(Citizen citizen) {
        CitizenProfilDTO dto = new CitizenProfilDTO();
        dto.setFirstName(citizen.getFirstName());
        dto.setLastName(citizen.getLastName());
        if(citizen.getGender() != null){
            dto.setGender(citizen.getGender().toString());
        }
        if(citizen.getBirthDate() != null){
            dto.setDob(citizen.getBirthDate().toString());
        }
        return dto;
    }
}
