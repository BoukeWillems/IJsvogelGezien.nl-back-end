package com.bouke.IJsvogelgezien.repository;

import com.bouke.IJsvogelgezien.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Long> {
    @Query("SELECT o FROM Observation o JOIN o.user u WHERE u.username = :username")
    List<Observation> findByUsername(@Param("username") String username);
}
