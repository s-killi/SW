package de.othr.easytownhall.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.othr.easytownhall.models.dtos.UserDto;
import de.othr.easytownhall.models.entities.Role;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.enums.RoleEnum;
import de.othr.easytownhall.repositories.UsersRepository;
import de.othr.easytownhall.services.MyUserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class UsersAuthenticationProvider {
    private final UsersRepository usersRepository;
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    private final MyUserService myUserService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDto userDto) {
        // Rollen des Benutzers abrufen
        List<String> roles = userDto.getRoles().stream()
                .map(Role::getName)
                .map(RoleEnum::getDescription)
                .collect(Collectors.toList());

        // Token erstellen
        Date now = new Date();
        Date validity = new Date(now.getTime() + 3600000 ); // Token-Gültigkeit: 1 Stunde -> 3600000

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        return JWT.create()
                .withSubject(String.valueOf(userDto.getId()))
                .withClaim("email", userDto.getEmail())
                .withClaim("roles", roles) // Rollen in den Token einfügen
                .withIssuedAt(now)         // Erstellungszeit
                .withExpiresAt(validity)   // Ablaufzeit
                .sign(algorithm);          // Signatur
    }

    public Authentication validateToken(String token) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            // Token validieren
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);

            // Benutzerinformationen aus dem Token extrahieren
            Long id = Long.parseLong(jwt.getSubject());
            String email = jwt.getClaim("email").asString();


            // Benutzer aus der Datenbank laden
            User user = usersRepository.findById(id)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));

            List<String> roles = jwt.getClaim("roles").asList(String.class);

            // Authorities aus Rollen und Privileges erstellen
            List<SimpleGrantedAuthority> authorities = myUserService.getPrivilegesById(id, roles);

            // Benutzerinformationen zusammenstellen
            UserDto userDto = UserDto.builder()
                    .email(user.getEmail())
                    .id(user.getId())
                    .build();

            // Authentication-Objekt zurückgeben
            return new UsernamePasswordAuthenticationToken(userDto, null, authorities);
        }catch (TokenExpiredException e) {
            throw e;
        }catch (Exception e) {
            throw new UsernameNotFoundException("Validation failed ", e);
        }
    }


    public String refreshToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);

        Long id = Long.parseLong(jwt.getSubject());

        User user = usersRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
        UserDto userDto = UserDto.builder()
                .email(user.getEmail())
                .roles(user.getRoles())
                .id(user.getId())
                .build();

        return this.createToken(userDto);
    }

    public Long getIdFromToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        Long id = Long.parseLong(jwt.getSubject());
        return id;
    }

    public boolean isAdmin(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        List<RoleEnum> roles = jwt.getClaim("roles").asList(RoleEnum.class);
        if(roles.contains(RoleEnum.ADMIN)){
            return true;
        }
        return false;
    }

    public boolean isServiceworker(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        List<RoleEnum> roles = jwt.getClaim("roles").asList(RoleEnum.class);
        if(roles.contains(RoleEnum.SERVICEWORKER) || roles.contains(RoleEnum.ADMIN)){
            return true;
        }
        return false;
    }

    public boolean isCitizen(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        List<RoleEnum> roles = jwt.getClaim("roles").asList(RoleEnum.class);
        if(roles.contains(RoleEnum.CITIZEN) || roles.contains(RoleEnum.ADMIN)){
            return true;
        }
        return false;
    }

}
