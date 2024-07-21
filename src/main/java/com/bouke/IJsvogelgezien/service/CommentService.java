package com.bouke.IJsvogelgezien.service;

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


@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObservationRepository observationRepository;

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

        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByUploadId(Long uploadId) {
        return commentRepository.findByUploadId(uploadId);
    }

    public List<Comment> getRepliesByCommentId(Long parentCommentId) {
        return commentRepository.findByParentCommentId(parentCommentId);
    }
}
