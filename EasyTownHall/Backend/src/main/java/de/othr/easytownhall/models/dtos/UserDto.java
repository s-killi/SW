package de.othr.easytownhall.models.dtos;

import de.othr.easytownhall.models.entities.Role;
import de.othr.easytownhall.models.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String email;
    private String token;
    private Collection<Role> roles;
}
