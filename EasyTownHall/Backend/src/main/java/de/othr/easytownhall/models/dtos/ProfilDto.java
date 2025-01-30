package de.othr.easytownhall.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ProfilDto {
    private long id;
    private String email;
    private String password;
    private String username;
    private boolean active;
}
