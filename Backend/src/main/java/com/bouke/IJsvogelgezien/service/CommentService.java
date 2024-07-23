package com.bouke.IJsvogelgezien.service;

import com.bouke.IJsvogelgezien.dto.CommentDTO;
import com.bouke.IJsvogelgezien.model.Comment;
import com.bouke.IJsvogelgezien.model.Observation;
import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.repository.CommentRepository;
import com.bouke.IJsvogelgezien.repository.ObservationRepository;
import com.bouke.IJsvogelgezien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private NotificationService notificationService;

    public Comment addComment(Long userId, Long uploadId, String text, Long parentCommentId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Observation upload = observationRepository.findById(uploadId).orElseThrow(() -> new RuntimeException("Upload not found"));

        Comment comment = new Comment();
        comment.setText(text);
        comment.setDate(new Date());
        comment.setUser(user);
        comment.setUpload(upload);

        if (parentCommentId != null) {
            Comment parentComment = commentRepository.findById(parentCommentId).orElseThrow(() -> new RuntimeException("Parent comment not found"));
            comment.setParentComment(parentComment);
        }

        Comment savedComment = commentRepository.save(comment);

        String message = user.getUsername() + " commented on your upload.";
        notificationService.createNotification(upload.getUser().getId(), message);

        return savedComment;
    }

    public List<CommentDTO> getCommentsByUploadId(Long uploadId) {
        return commentRepository.findByUploadId(uploadId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CommentDTO> getRepliesByCommentId(Long parentCommentId) {
        return commentRepository.findByParentCommentId(parentCommentId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CommentDTO mapToDTO(Comment comment) {
        CommentDTO dto = new CommentDTO();
        dto.setId(comment.getId());
        dto.setText(comment.getText());
        dto.setDate(comment.getDate());
        dto.setUserId(comment.getUser().getId());
        dto.setUsername(comment.getUser().getUsername());
        dto.setUploadId(comment.getUpload().getId());
        if (comment.getParentComment() != null) {
            dto.setParentCommentId(comment.getParentComment().getId());
        }
        dto.setReplies(comment.getReplies().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList()));
        return dto;
    }
}