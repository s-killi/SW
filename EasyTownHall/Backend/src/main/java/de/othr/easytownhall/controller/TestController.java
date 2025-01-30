package de.othr.easytownhall.controller;

import de.othr.easytownhall.models.entities.TestUserModel;
import de.othr.easytownhall.services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
import java.util.*;


@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    private TestService testService;

    //Autowired ist für Dependency Injection
    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/getFirst")
    public ResponseEntity<TestUserModel> getTestModel() {
        return ResponseEntity.ok(testService.getFirst());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello from Spring Boot!");
    }

    @GetMapping("/getUserById")
    public ResponseEntity<TestUserModel> getUserById(@RequestParam Long id) {
        Optional<TestUserModel> user = testService.getById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<TestUserModel>> getAllUsers() {
        return ResponseEntity.ok(testService.getAll());
    }

    @PostMapping("/addUser")
    public ResponseEntity<TestUserModel> addUser(@RequestBody TestUserModel user) {
        TestUserModel savedUser = testService.addUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<TestUserModel> updateUser(
            @PathVariable Long id,
            @RequestBody TestUserModel updatedUser) {
        // Bestehenden Benutzer aus der Datenbank abrufen
        Optional<TestUserModel> existingUserOpt = testService.getById(id);

        if (existingUserOpt.isEmpty()) {
            // Benutzer existiert nicht
            return ResponseEntity.notFound().build();
        }

        TestUserModel existingUser = existingUserOpt.get();

        // Nur die Felder aktualisieren, die gesetzt sind und nicht gleich sind
        if (updatedUser.getName() != null &&
                !updatedUser.getName().equals(existingUser.getName())){
            existingUser.setName(updatedUser.getName());
        }

        if (updatedUser.getAge() != 0 &&
                updatedUser.getAge() != existingUser.getAge()) { // Achtung: 0 ist der Standardwert für int, falls nicht gesetzt
            existingUser.setAge(updatedUser.getAge());
        }

        // Benutzer speichern
        TestUserModel savedUser = testService.updateUser(existingUser);
        return ResponseEntity.ok(savedUser);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        boolean isDeleted = testService.deleteUserById(id);

        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }


}
