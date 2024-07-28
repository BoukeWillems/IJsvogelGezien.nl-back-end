package com.bouke.IJsvogelgezien.service;

import com.bouke.IJsvogelgezien.dto.CommentDTO;
import com.bouke.IJsvogelgezien.exception.NotFound;
import com.bouke.IJsvogelgezien.model.Comment;
import com.bouke.IJsvogelgezien.model.Observation;
import com.bouke.IJsvogelgezien.model.User;
import com.bouke.IJsvogelgezien.repository.CommentRepository;
import com.bouke.IJsvogelgezien.repository.ObservationRepository;
import com.bouke.IJsvogelgezien.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

//    public CommentDTO addComment(CommentDTO request) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Observation observation = observationRepository.findById(request.getUploadId())
//                .orElseThrow(() -> new RuntimeException("Observation not found"));
//
//        Comment comment = new Comment();
//        comment.setText(request.getText());
//        comment.setDate(new Date());
//        comment.setUser(user);
//        comment.setUpload(observation);
//
//        Comment savedComment = commentRepository.save(comment);
//        return mapToDTO(savedComment);
//    }


    public CommentDTO addComment(CommentDTO request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Observation observation = observationRepository.findById(request.getUploadId())
                .orElseThrow(() -> new RuntimeException("Observation not found"));

        Comment parentComment = null;
        if (request.getParentCommentId() != null) {
            parentComment = commentRepository.findById(request.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
        }

        Comment comment = new Comment();
        comment.setText(request.getText());
        comment.setDate(new Date());
        comment.setUser(user);
        comment.setUpload(observation);
        comment.setParentComment(parentComment);

        Comment savedComment = commentRepository.save(comment);
        return mapToDTO(savedComment);
    }

    public List<CommentDTO> getCommentsByObservation(Long observationId) {
        Observation observation = observationRepository.findById(observationId)
                .orElseThrow(() -> new NotFound("No Observation Found with Id "+observationId));

        List<Comment> comments = commentRepository.findByUploadId(observation.getId());
        return comments.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CommentDTO updateComment(Long commentId, String newText) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("User is not authorized to update this comment");
        }

        comment.setText(newText);
        Comment updatedComment = commentRepository.save(comment);
        return mapToDTO(updatedComment);
    }

    public void deleteComment(Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("User is not authorized to delete this comment");
        }

        commentRepository.delete(comment);
    }

//    implementation of comment replay


    public List<CommentDTO> getCommentReplies(Long parentCommentId) {
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        List<Comment> replies = commentRepository.findByParentCommentId(parentCommentId);
        return replies.stream().map(this::mapToDTO).collect(Collectors.toList());
    }




    private CommentDTO mapToDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setText(comment.getText());
        commentDTO.setDate(comment.getDate());
        commentDTO.setUserId(comment.getUser().getId());
        commentDTO.setUsername(comment.getUser().getUsername());
        commentDTO.setUploadId(comment.getUpload().getId());
        commentDTO.setParentCommentId(comment.getParentComment() != null ? comment.getParentComment().getId() : null);
        commentDTO.setReplies(comment.getReplies() != null ? comment.getReplies().stream().map(this::mapToDTO).collect(Collectors.toList()) : null);
        return commentDTO;
    }
}