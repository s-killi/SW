package de.othr.easytownhall.services;

import de.othr.easytownhall.mapper.CitizenMapper;
import de.othr.easytownhall.models.dtos.CitizenProfilDTO;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.citizen.Citizen;
import de.othr.easytownhall.models.enums.Gender;
import de.othr.easytownhall.repositories.CitizenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CitizenService {
    private final CitizenRepository citizenRepository;
    private final CitizenMapper citizenMapper;
    public Citizen getCitizenByUser(User user) {
        return citizenRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Citizen not found for user: " + user.getId()));
    }

    public CitizenProfilDTO updateCitizen(CitizenProfilDTO profilDto, User user) {
        Citizen existingCitizen = citizenRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Citizen not found for user: " + user.getId()));
        existingCitizen.setFirstName(profilDto.getFirstName());
        existingCitizen.setLastName(profilDto.getLastName());
        if(profilDto.getGender() != null){
            existingCitizen.setGender(Gender.fromDescription(profilDto.getGender()));
        }
        if(profilDto.getDob() != null){
            existingCitizen.setBirthDate(LocalDate.parse(profilDto.getDob()));
        }
        return this.citizenMapper.toDto(this.citizenRepository.save(existingCitizen));
    }


}
