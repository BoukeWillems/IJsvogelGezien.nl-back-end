//    •	Interface voor databaseoperaties met betrekking tot de Comment entiteit.
//	•	Uitbreiding van JpaRepository<Comment, Long>.


package com.bouke.IJsvogelgezien.repository;

import com.bouke.IJsvogelgezien.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
}
