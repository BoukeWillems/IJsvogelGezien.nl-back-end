package com.bouke.IJsvogelgezien.repository;

import com.bouke.IJsvogelgezien.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByObservationId(Long observationId);
}
