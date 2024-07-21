package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.dto.ObservationDTO;
import com.bouke.IJsvogelgezien.service.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/observations")
public class ObservationController {

    @Autowired
    private ObservationService observationService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadObservation(
            @RequestParam("description") String description,
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("date") Date date,
            @RequestParam("photo") MultipartFile photo,
            Authentication authentication) {
        try {
            String username = authentication.getName();
            ObservationDTO observation = observationService.saveObservation(username, description, latitude, longitude, date, photo);
            return ResponseEntity.ok(observation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<ObservationDTO>> getObservationsByUsername(@PathVariable String username) {
        try {
            List<ObservationDTO> observations = observationService.getObservationsByUsername(username);
            return ResponseEntity.ok(observations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}