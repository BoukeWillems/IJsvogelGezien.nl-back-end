package com.bouke.IJsvogelgezien.repository;

import com.bouke.IJsvogelgezien.model.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ObservationRepository extends JpaRepository<Observation, Long> {
    List<Observation> findTop5ByOrderByDateTimeDesc();
    List<Observation> findByUserId(Long userId);
    // Voeg andere methoden toe indien nodig, zoals voor het vinden van waarnemingen dichtbij een locatie
}
