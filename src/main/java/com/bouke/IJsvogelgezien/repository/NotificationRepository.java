package com.bouke.IJsvogelgezien.repository;

import com.bouke.IJsvogelgezien.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndReadFalse(Long userId);
    List<Notification> findByUserId(Long userId);
}