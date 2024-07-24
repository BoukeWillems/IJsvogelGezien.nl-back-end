package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.dto.ObservationDTO;
import com.bouke.IJsvogelgezien.service.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestParam("date") String date,
            @RequestParam("photo") MultipartFile photo,
            Authentication authentication) {
        try {
            double latitudeDouble = Double.parseDouble(latitude);
            double longitudeDouble = Double.parseDouble(longitude);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss");
            Date date1 = formatter.parse(date);
            String username = authentication.getName();
            ObservationDTO observation = observationService.saveObservation(username, description, latitudeDouble, longitudeDouble, date1, photo);
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