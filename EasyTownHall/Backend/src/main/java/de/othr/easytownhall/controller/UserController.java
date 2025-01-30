package de.othr.easytownhall.controller;

import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.enums.RoleEnum;
import de.othr.easytownhall.services.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private MyUserService userService;

    @GetMapping("/getAll")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/updateRole/{id}")
    public ResponseEntity<User> updateRole(@PathVariable long id ,@RequestBody String role){
        User updatedUser = this.userService.updateRole(id, RoleEnum.valueOf(role));
        if(updatedUser != null){
            return ResponseEntity.ok(updatedUser);
        }
        return ResponseEntity.notFound().build();
    }
}
