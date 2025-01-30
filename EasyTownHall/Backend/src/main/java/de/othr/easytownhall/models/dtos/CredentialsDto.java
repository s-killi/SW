package de.othr.easytownhall.models.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CredentialsDto {
    private String email;
    private String password;
}
