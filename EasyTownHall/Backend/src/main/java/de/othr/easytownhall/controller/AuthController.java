package de.othr.easytownhall.controller;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import de.othr.easytownhall.config.UsersAuthenticationProvider;
import de.othr.easytownhall.models.dtos.UserDto;
import de.othr.easytownhall.models.dtos.CredentialsDto;
import de.othr.easytownhall.models.dtos.SignUpDto;
import de.othr.easytownhall.models.entities.citizen.Citizen;
import de.othr.easytownhall.models.response.RolesResponse;
import de.othr.easytownhall.repositories.RoleRepository;
import de.othr.easytownhall.repositories.UsersRepository;
import de.othr.easytownhall.services.MyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final MyUserService myUserService;
    private final UsersAuthenticationProvider usersAuthenticationProvider;
    private final RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody CredentialsDto credentialsDto){
        UserDto userDto = myUserService.login(credentialsDto);
        userDto.setToken(usersAuthenticationProvider.createToken(userDto));
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@RequestBody SignUpDto user){
        UserDto userDto = myUserService.register(user);
        userDto.setToken(usersAuthenticationProvider.createToken(userDto));
        return ResponseEntity.created(URI.create("/users/" + userDto.getId())).body(userDto);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request){
        String currentToken = request.getOrDefault("token", "");

        String newToken = this.usersAuthenticationProvider.refreshToken(currentToken);
        Map<String, String> response = new HashMap<>();

        response.put("token", newToken);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/isAdmin")
    public ResponseEntity<Boolean> isAdmin(@RequestBody Map<String, String> request){
        String currentToken = request.getOrDefault("token", "");
        return ResponseEntity.ok(this.usersAuthenticationProvider.isAdmin(currentToken));
    }

    @PostMapping("/isServiceworker")
    public ResponseEntity<Boolean> isServiceworker(@RequestBody Map<String, String> request){
        String currentToken = request.getOrDefault("token", "");
        return ResponseEntity.ok(this.usersAuthenticationProvider.isServiceworker(currentToken));
    }

    @PostMapping("/isCitizen")
    public ResponseEntity<Boolean> isCitizen(@RequestBody Map<String, String> request){
        String currentToken = request.getOrDefault("token", "");
        return ResponseEntity.ok(this.usersAuthenticationProvider.isCitizen(currentToken));
    }

    @PostMapping("/checkRoles")
    public ResponseEntity<RolesResponse> checkRoles(@RequestBody Map<String, String> request) {
        String currentToken = request.getOrDefault("token", "");

        boolean isAdmin = this.usersAuthenticationProvider.isAdmin(currentToken);
        boolean isServiceWorker = this.usersAuthenticationProvider.isServiceworker(currentToken);

        RolesResponse rolesResponse = new RolesResponse(isAdmin, isServiceWorker);

        return ResponseEntity.ok(rolesResponse);
    }

}
