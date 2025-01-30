package de.othr.easytownhall.controller;


import de.othr.easytownhall.exceptions.AppException;
import de.othr.easytownhall.mapper.CitizenMapper;
import de.othr.easytownhall.mapper.UserMapper;
import de.othr.easytownhall.models.dtos.CitizenProfilDTO;
import de.othr.easytownhall.models.dtos.ProfilDto;
import de.othr.easytownhall.models.dtos.UserDto;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.citizen.Citizen;
import de.othr.easytownhall.services.CitizenService;
import de.othr.easytownhall.services.MyUserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/profil")
public class ProfilController {
    //TODO: Implement profile controller
    private final MyUserService myUserService;
    private final UserMapper userMapper;
    private final CitizenService citizenService;
    private final CitizenMapper citizenMapper;


    @GetMapping("/{id}")
    public ResponseEntity<ProfilDto> getFullProfile(
            @PathVariable Long id
    ){
        User user = myUserService.getById(id);
        ProfilDto profilDto = userMapper.toProfilDto(user);
        return ResponseEntity.ok(profilDto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ProfilDto> updateFullProfile(
            @PathVariable Long id,
            @RequestBody ProfilDto profilDto
    ){
        ProfilDto updatedProfilDto = myUserService.updateUserProzess(id, profilDto);
        return ResponseEntity.ok(updatedProfilDto);

    }

    @GetMapping("/citizen/{id}")
    public ResponseEntity<CitizenProfilDTO> getCitizenFullProfile(@PathVariable Long id){
        User user = myUserService.getById(id);
        Citizen citizen = citizenService.getCitizenByUser(user);
        CitizenProfilDTO dto = citizenMapper.toDto(citizen);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/citizen/{id}")
    public ResponseEntity<CitizenProfilDTO> updateCitizen(@PathVariable Long id, @RequestBody CitizenProfilDTO profilDto){
        User existingUser = myUserService.getById(id);
        CitizenProfilDTO dto = citizenService.updateCitizen(profilDto, existingUser);
        return  ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProfilDto> deleteFullProfile(@PathVariable Long id) {
        try {
            // Benutzer abrufen
            User user = myUserService.getById(id);

            // Benutzer löschen
            myUserService.deleteUser(user);

            // Gelöschten Benutzer in DTO umwandeln
            ProfilDto profilDto = userMapper.toProfilDto(user);


            // Erfolgsantwort zurückgeben
            return ResponseEntity.ok(profilDto);
        } catch (AppException e) {
            // Falls der Benutzer nicht gefunden wurde, Fehler melden
            return ResponseEntity.status(e.getStatus()).body(null);
        } catch (Exception e) {
            // Allgemeine Fehlerbehandlung
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
