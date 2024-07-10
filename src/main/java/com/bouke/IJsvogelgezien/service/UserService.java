package com.bouke.IJsvogelgezien.service;

import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String email, String password) {
        if (userRepository.findByUsername(username) != null || userRepository.findByEmail(email) != null) {
            throw new RuntimeException("Username or email already taken");
        }

        User user = new User(username, email, passwordEncoder.encode(password), Set.of("ROLE_USER"));
        return userRepository.save(user);
    }
}
