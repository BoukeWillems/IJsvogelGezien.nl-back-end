package com.bouke.IJsvogelgezien.repository;

import com.bouke.IJsvogelgezien.model.Role;
import com.bouke.IJsvogelgezien.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
