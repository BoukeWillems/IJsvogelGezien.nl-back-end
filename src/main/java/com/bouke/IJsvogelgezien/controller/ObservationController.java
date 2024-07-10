package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.model.Observation;
import com.bouke.IJsvogelgezien.service.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/observations")
public class ObservationController {

    @Autowired
    private ObservationService observationService;

    @PostMapping("/add")
    public String addObservation(@RequestParam String photoUrl, @RequestParam String dateTime,
                                 @RequestParam String location, @RequestParam String description,
                                 @RequestParam Long userId) {
        LocalDateTime parsedDateTime = LocalDateTime.parse(dateTime);
        observationService.saveObservation(photoUrl, parsedDateTime, location, description, userId);
        return "Observation added successfully";
    }

    @GetMapping("/recent")
    public List<Observation> getRecentObservations() {
        return observationService.getRecentObservations();
    }

    @GetMapping("/user/{userId}")
    public List<Observation> getUserObservations(@PathVariable Long userId) {
        return observationService.getUserObservations(userId);
    }

    // Voeg methoden toe voor andere benodigde functionaliteiten, zoals waarnemingen dichtbij een locatie
}
