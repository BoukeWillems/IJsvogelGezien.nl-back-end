package com.bouke.IJsvogelgezien.repository;

import com.bouke.IJsvogelgezien.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByUploadId(Long uploadId);
    Boolean existsByUserIdAndUploadId(Long userId, Long uploadId);
    void deleteByUserIdAndUploadId(Long userId, Long uploadId);
    List<Like> findByUploadId(Long uploadId);
}