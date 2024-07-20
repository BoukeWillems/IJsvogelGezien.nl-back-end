package com.bouke.IJsvogelgezien.service;

import com.bouke.IJsvogelgezien.dto.SignUpRequestDTO;
import com.bouke.IJsvogelgezien.model.Role;
import com.bouke.IJsvogelgezien.model.RoleName;
import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.repository.RoleRepository;
import com.bouke.IJsvogelgezien.repository.UserRepository;
import com.bouke.IJsvogelgezien.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    @Lazy
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

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.error("User not found with username: {}", username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });

        logger.info("User found with username: {}", username);
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("User not found with id: {}", id);
                    return new UsernameNotFoundException("User not found with id: " + id);
                });

        logger.info("User found with id: {}", id);
        return UserPrincipal.create(user);
    }

    public User registerUser(SignUpRequestDTO signUpRequestDTO) {
        if (existsByUsername(signUpRequestDTO.getUsername())) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        if (existsByEmail(signUpRequestDTO.getEmail())) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        User user = new User();
        user.setUsername(signUpRequestDTO.getUsername());
        user.setEmail(signUpRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequestDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(userRole);
        user.setRoles(roles);

        User registeredUser = userRepository.save(user);
        logger.info("User registered successfully with username: {}", registeredUser.getUsername());
        return registeredUser;
    }
}
