package com.bouke.IJsvogelgezien.service;

import com.bouke.IJsvogelgezien.model.Observation;
import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.repository.ObservationRepository;
import com.bouke.IJsvogelgezien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ObservationService {

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private UserRepository userRepository;

    public Observation saveObservation(String photoUrl, LocalDateTime dateTime, String location, String description, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Observation observation = new Observation(photoUrl, dateTime, location, description, user);
        return observationRepository.save(observation);
    }

    public List<Observation> getRecentObservations() {
        return observationRepository.findTop5ByOrderByDateTimeDesc();
    }

    public List<Observation> getUserObservations(Long userId) {
        return observationRepository.findByUserId(userId);
    }

    // Voeg methoden toe voor andere benodigde functionaliteiten, zoals waarnemingen dichtbij een locatie
}
