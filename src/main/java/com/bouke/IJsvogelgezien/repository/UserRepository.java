package com.bouke.IJsvogelgezien.repository;

import com.bouke.IJsvogelgezien.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}