package com.bouke.IJsvogelgezien.service;

import com.bouke.IJsvogelgezien.model.Role;
import com.bouke.IJsvogelgezien.model.RoleName;
import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.repository.RoleRepository;
import com.bouke.IJsvogelgezien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roleRepository.findByName(RoleName.ROLE_USER).ifPresent(roles::add);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public UserDetails loadUserById(Long userId) {
        return null;
    }

    public void registerUser(String username, String email, String password) {
    }
}
