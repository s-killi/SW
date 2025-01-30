package de.othr.easytownhall.models.dtos;

import de.othr.easytownhall.models.enums.Gender;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@RequiredArgsConstructor
@Data
public class SignUpDto {
    @NonNull
    private String email;
    @NonNull
    private String password;

}
