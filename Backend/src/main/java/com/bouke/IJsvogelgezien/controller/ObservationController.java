package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.dto.ObservationDTO;
import com.bouke.IJsvogelgezien.security.UserPrincipal;
import com.bouke.IJsvogelgezien.service.ObservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/observations")
public class ObservationController {

    @Autowired
    private ObservationService observationService;

    private final Path fileStorageLocation = Paths.get("Backend/src/main/resources/uploads").toAbsolutePath().normalize();

    @PostMapping("/upload")
    public ResponseEntity<?> uploadObservation(
            @RequestParam("description") String description,
            @RequestParam("latitude") String latitude,
            @RequestParam("longitude") String longitude,
            @RequestParam("date") String date,
            @RequestParam("photo") MultipartFile photo,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            double latitudeDouble = Double.parseDouble(latitude);
            double longitudeDouble = Double.parseDouble(longitude);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date1 = formatter.parse(date);
            String username = userPrincipal.getUsername();
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

    @GetMapping("/{observationId}")
    public ResponseEntity<ObservationDTO> getObservationById(@PathVariable Long observationId) {
        try {
            ObservationDTO observation = observationService.getObservationById(observationId);
            return ResponseEntity.ok(observation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<ObservationDTO>> getAllObservations() {
        List<ObservationDTO> observations = observationService.getAllObservations();
        return ResponseEntity.ok(observations);
    }

    @GetMapping("/radius")
    public ResponseEntity<List<ObservationDTO>> getObservationsByRadius(
            @RequestParam("latitude") double latitude,
            @RequestParam("longitude") double longitude,
            @RequestParam("radius") double radius) {
        List<ObservationDTO> observations = observationService.getObservationsByRadius(latitude, longitude, radius);
        return ResponseEntity.ok(observations);
    }

    @GetMapping("/period")
    public ResponseEntity<List<ObservationDTO>> getObservationsByPeriod(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate) {
        try {
            List<ObservationDTO> observations = observationService.getObservationsByPeriod(startDate, endDate);
            return ResponseEntity.ok(observations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/recent")
    public ResponseEntity<List<ObservationDTO>> getRecentObservations() {
        try {
            List<ObservationDTO> observations = observationService.getRecentObservations();
            return ResponseEntity.ok(observations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<ObservationDTO>> getNearbyObservations(
            @RequestParam("lat") double latitude,
            @RequestParam("lon") double longitude) {
        try {
            List<ObservationDTO> observations = observationService.getNearbyObservations(latitude, longitude, 25);
            return ResponseEntity.ok(observations);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        try {
            Path filePath = fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}