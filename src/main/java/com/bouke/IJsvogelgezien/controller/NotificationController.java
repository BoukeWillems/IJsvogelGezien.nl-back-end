package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.dto.NotificationDTO;
import com.bouke.IJsvogelgezien.security.UserPrincipal;
import com.bouke.IJsvogelgezien.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/create")
    public ResponseEntity<NotificationDTO> createNotification(@RequestParam("userId") Long userId, @RequestParam("message") String message) {
        NotificationDTO notificationDTO = notificationService.createNotification(userId, message);
        return ResponseEntity.ok(notificationDTO);
    }

    @GetMapping("/unread")
    public ResponseEntity<List<NotificationDTO>> getUnreadNotifications(Authentication authentication) {
        Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
        List<NotificationDTO> notifications = notificationService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/all")
    public ResponseEntity<List<NotificationDTO>> getAllNotifications(Authentication authentication) {
        Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
        List<NotificationDTO> notifications = notificationService.getAllNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/read/{notificationId}")
    public ResponseEntity<?> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok("Notification marked as read");
    }
}