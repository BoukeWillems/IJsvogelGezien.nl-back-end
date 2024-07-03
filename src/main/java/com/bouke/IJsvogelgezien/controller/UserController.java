//    •	Behandelt verzoeken met betrekking tot gebruikersregistratie, login en profielbeheer.
//	•	Endpoints voor registreren, inloggen, en ophalen van gebruikersinformatie.



package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    // Constructor-injectie
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            return ResponseEntity.badRequest().body("Error: Username is already taken!");
        }
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }
        userService.save(user);
        return ResponseEntity.ok("User registered successfully!");
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Voeg hier andere endpoints toe als dat nodig is
}