package com.bouke.IJsvogelgezien.service;

import com.bouke.IJsvogelgezien.model.Comment;
import com.bouke.IJsvogelgezien.model.Observation;
import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.repository.CommentRepository;
import com.bouke.IJsvogelgezien.repository.ObservationRepository;
import com.bouke.IJsvogelgezien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObservationRepository observationRepository;

    @Autowired
    private UserRepository userRepository;

    public Comment addComment(String text, Long observationId, Long userId) {
        Observation observation = observationRepository.findById(observationId).orElseThrow(() -> new RuntimeException("Observation not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Comment comment = new Comment(text, LocalDateTime.now(), observation, user);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByObservation(Long observationId) {
        return commentRepository.findByObservationId(observationId);
    }
}
