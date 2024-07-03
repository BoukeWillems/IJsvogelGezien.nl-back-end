//    •	Interface voor databaseoperaties met betrekking tot de User entiteit.
//	•	Uitbreiding van JpaRepository<User, Long>.


package com.bouke.IJsvogelgezien.repository;

import com.bouke.IJsvogelgezien.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
