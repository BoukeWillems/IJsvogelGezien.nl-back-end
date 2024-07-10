package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        try {
            userService.registerUser(username, email, password);
            return "User registered successfully";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }

    @GetMapping("/login")
    public String login() {
        return "Login page";
    }

    @GetMapping("/home")
    public String home() {
        return "Welcome to the home page!";
    }
}
