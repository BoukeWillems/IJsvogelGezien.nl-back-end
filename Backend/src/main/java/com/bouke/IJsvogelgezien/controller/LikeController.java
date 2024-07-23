package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.dto.LikeDTO;
import com.bouke.IJsvogelgezien.security.UserPrincipal;
import com.bouke.IJsvogelgezien.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/like")
    public ResponseEntity<?> likeUpload(@RequestParam("uploadId") Long uploadId, Authentication authentication) {
        try {
            Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
            LikeDTO likeDTO = likeService.likeUpload(userId, uploadId);
            return ResponseEntity.ok(likeDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/unlike")
    public ResponseEntity<?> unlikeUpload(@RequestParam("uploadId") Long uploadId, Authentication authentication) {
        try {
            Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
            likeService.unlikeUpload(userId, uploadId);
            return ResponseEntity.ok("Upload unliked successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/count/{uploadId}")
    public ResponseEntity<Long> getLikeCount(@PathVariable Long uploadId) {
        Long likeCount = likeService.getLikeCount(uploadId);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/hasLiked/{uploadId}")
    public ResponseEntity<Boolean> hasUserLiked(@PathVariable Long uploadId, Authentication authentication) {
        Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
        Boolean hasLiked = likeService.hasUserLiked(userId, uploadId);
        return ResponseEntity.ok(hasLiked);
    }

    @GetMapping("/upload/{uploadId}")
    public ResponseEntity<List<LikeDTO>> getLikesByUploadId(@PathVariable Long uploadId) {
        List<LikeDTO> likes = likeService.getLikesByUploadId(uploadId);
        return ResponseEntity.ok(likes);
    }
}