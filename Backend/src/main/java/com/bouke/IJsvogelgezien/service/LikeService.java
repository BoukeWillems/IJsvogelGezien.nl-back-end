package com.bouke.IJsvogelgezien.service;

import com.bouke.IJsvogelgezien.dto.LikeDTO;
import com.bouke.IJsvogelgezien.model.Like;
import com.bouke.IJsvogelgezien.model.Observation;
import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.repository.LikeRepository;
import com.bouke.IJsvogelgezien.repository.ObservationRepository;
import com.bouke.IJsvogelgezien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObservationRepository observationRepository;

    public ResponseEntity<Map<String, String>> likeUnlike(Long observationId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Observation observation = observationRepository.findById(observationId)
                .orElseThrow(() -> new RuntimeException("Observation not found"));

        Optional<Like> existingLike = likeRepository.findByUserAndObservation(user, observation);

        if (existingLike.isPresent()) {
            // If like exists, remove it
            likeRepository.delete(existingLike.get());
            return ResponseEntity.ok(Map.of(
                    "message", "Observation unliked",
                    "status", "success",
                    "userId", user.getId().toString()
            ));
        } else {
            // If like does not exist, add it
            Like like = new Like(user, observation);
            likeRepository.save(like);
            return ResponseEntity.ok(Map.of(
                    "message", "Observation liked",
                    "status", "success",
                    "userId", user.getId().toString()
            ));
        }
    }



    public List<LikeDTO> getLikesByObservation(Long observationId) {
        List<Like> likes = likeRepository.findByObservationId(observationId);
        return likes.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private LikeDTO mapToDTO(Like like) {
        LikeDTO likeDTO = new LikeDTO();
        likeDTO.setId(like.getId());
        likeDTO.setUserId(like.getUser().getId());
        likeDTO.setObservationId(like.getObservation().getId());
        likeDTO.setDate(like.getDate());
        return likeDTO;
    }
}