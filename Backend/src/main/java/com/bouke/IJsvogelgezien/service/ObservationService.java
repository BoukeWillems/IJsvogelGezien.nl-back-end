package com.bouke.IJsvogelgezien.service;

import com.bouke.IJsvogelgezien.dto.ObservationDTO;
import com.bouke.IJsvogelgezien.model.Observation;
import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.repository.ObservationRepository;
import com.bouke.IJsvogelgezien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ObservationService {

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private UserRepository userRepository;

    public ObservationDTO saveObservation(String username, String description, double latitude, double longitude, Date date, MultipartFile photo) throws IOException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        String photoPath = savePhoto(photo);

        Observation observation = new Observation();
        observation.setDescription(description);
        observation.setLatitude(latitude);
        observation.setLongitude(longitude);
        observation.setDate(date);
        observation.setPhotoPath(photoPath);
        observation.setUser(user);

        Observation savedObservation = observationRepository.save(observation);
        return mapToDTO(savedObservation);
    }

    public List<ObservationDTO> getObservationsByUsername(String username) {
        List<Observation> observations = observationRepository.findByUsername(username);
        return observations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ObservationDTO getObservationById(Long observationId) throws Exception {
        Optional<Observation> observationOptional = observationRepository.findById(observationId);
        if (observationOptional.isPresent()) {
            Observation observation = observationOptional.get();
            return mapToDTO(observation);
        } else {
            throw new Exception("Observation not found");
        }
    }

    public List<ObservationDTO> getAllObservations() {
        List<Observation> observations = observationRepository.findAll();
        return observations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ObservationDTO> getObservationsByRadius(double latitude, double longitude, double radius) {
        List<Observation> observations = observationRepository.findByLocationWithinRadius(latitude, longitude, radius);
        return observations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }


    public List<ObservationDTO> getObservationsByPeriod(String startDate, String endDate) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date start = formatter.parse(startDate);
        Date end = formatter.parse(endDate);
        List<Observation> observations = observationRepository.findByDateBetween(start, end);
        return observations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ObservationDTO> getRecentObservations() {
        List<Observation> observations = observationRepository.findTop5ByOrderByDateDesc();
        return observations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<ObservationDTO> getNearbyObservations(Double latitude, Double longitude, Double radius) {
        List<Observation> observations = observationRepository.findTop5ByLocationWithinRadius(latitude, longitude, radius);
        return observations.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private String savePhoto(MultipartFile photo) throws IOException {
        if (photo.isEmpty()) {
            throw new RuntimeException("Empty photo file");
        }

        byte[] bytes = photo.getBytes();
        String uploadDir = "Backend/src/main/resources/uploads/";
        Path path = Paths.get(uploadDir);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
        Path filePath = Paths.get(uploadDir + photo.getOriginalFilename());
        Files.write(filePath, bytes);

        return filePath.toString();
    }

    public ObservationDTO mapToDTO(Observation observation) {
        ObservationDTO dto = new ObservationDTO();
        dto.setId(observation.getId());
        dto.setDescription(observation.getDescription());
        dto.setDate(observation.getDate());
        dto.setLatitude(observation.getLatitude());
        dto.setLongitude(observation.getLongitude());
        dto.setPhotoPath(observation.getPhotoPath());
        dto.setUsername(observation.getUser().getUsername());
        return dto;
    }
}