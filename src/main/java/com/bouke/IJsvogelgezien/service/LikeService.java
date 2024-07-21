package com.bouke.IJsvogelgezien.service;

import com.bouke.IJsvogelgezien.dto.LikeDTO;
import com.bouke.IJsvogelgezien.model.Like;
import com.bouke.IJsvogelgezien.model.Observation;
import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.repository.LikeRepository;
import com.bouke.IJsvogelgezien.repository.ObservationRepository;
import com.bouke.IJsvogelgezien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeService {
    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private NotificationService notificationService;

    public LikeDTO likeUpload(Long userId, Long uploadId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Observation upload = observationRepository.findById(uploadId).orElseThrow(() -> new RuntimeException("Upload not found"));

        if (likeRepository.existsByUserIdAndUploadId(userId, uploadId)) {
            throw new RuntimeException("You have already liked this upload");
        }

        Like like = new Like();
        like.setUser(user);
        like.setUpload(upload);
        like.setDate(new Date());

        Like savedLike = likeRepository.save(like);

        // Create notification
        String message = user.getUsername() + " liked your upload.";
        notificationService.createNotification(upload.getUser().getId(), message);

        return mapToDTO(savedLike);
    }

    public void unlikeUpload(Long userId, Long uploadId) {
        if (!likeRepository.existsByUserIdAndUploadId(userId, uploadId)) {
            throw new RuntimeException("You have not liked this upload");
        }

        likeRepository.deleteByUserIdAndUploadId(userId, uploadId);
    }

    public Long getLikeCount(Long uploadId) {
        return likeRepository.countByUploadId(uploadId);
    }

    public Boolean hasUserLiked(Long userId, Long uploadId) {
        return likeRepository.existsByUserIdAndUploadId(userId, uploadId);
    }

    public List<LikeDTO> getLikesByUploadId(Long uploadId) {
        return likeRepository.findByUploadId(uploadId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public LikeDTO mapToDTO(Like like) {
        LikeDTO dto = new LikeDTO();
        dto.setId(like.getId());
        dto.setUserId(like.getUser().getId());
        dto.setUploadId(like.getUpload().getId());
        dto.setDate(like.getDate());
        return dto;
    }
}