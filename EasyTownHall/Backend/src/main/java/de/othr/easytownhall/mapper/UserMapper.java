package de.othr.easytownhall.mapper;


import de.othr.easytownhall.models.dtos.ProfilDto;
import de.othr.easytownhall.models.dtos.SignUpDto;
import de.othr.easytownhall.models.dtos.UserDto;
import de.othr.easytownhall.models.entities.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setRoles(user.getRoles());
        return dto;
    }


    public User signUpToCitizen(SignUpDto dto) {
        /* Ignores Password
         *
         */
        User user = new User();
        user.setEmail(dto.getEmail());
        return user;
    }

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setRoles(dto.getRoles());
        return user;
    }

    public ProfilDto toProfilDto(User user) {
        ProfilDto dto = new ProfilDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setActive(user.isActive());
        return dto;
    }

    public User profileToUser(ProfilDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setActive(dto.isActive());
        user.setPassword(dto.getPassword());
        return user;
    }


    public User updateUserFromProfilDto(User user, ProfilDto dto) {
        if(dto.getUsername() != null &&
                !dto.getUsername().equals(user.getUsername())) {
            user.setUsername(dto.getUsername());
        }
        if(dto.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        if(dto.getEmail() != null &&
                !dto.getEmail().equals(user.getEmail())) {
            user.setEmail(dto.getEmail());
        }
        if(dto.getUsername() != null
        && !dto.getUsername().equals(user.getUsername())) {
            user.setUsername(dto.getUsername());
        }
        user.setActive(dto.isActive());
        return user;
    }
}
