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
import java.util.Date;
import java.util.List;
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

    private String savePhoto(MultipartFile photo) throws IOException {
        if (photo.isEmpty()) {
            throw new RuntimeException("Empty photo file");
        }

        byte[] bytes = photo.getBytes();
        String uploadDir = "src/main/resources/uploads/";
        Path path = Paths.get(uploadDir + photo.getOriginalFilename());
        Files.write(path, bytes);

        return path.toString();
    }

    private ObservationDTO mapToDTO(Observation observation) {
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