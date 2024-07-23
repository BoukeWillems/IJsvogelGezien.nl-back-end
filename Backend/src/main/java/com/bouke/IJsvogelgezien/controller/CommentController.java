package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.dto.CommentDTO;
import com.bouke.IJsvogelgezien.model.Comment;
import com.bouke.IJsvogelgezien.security.UserPrincipal;
import com.bouke.IJsvogelgezien.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<?> addComment(
            @RequestParam("uploadId") Long uploadId,
            @RequestParam("text") String text,
            @RequestParam(value = "parentCommentId", required = false) Long parentCommentId,
            Authentication authentication) {
        try {
            Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
            Comment comment = commentService.addComment(userId, uploadId, text, parentCommentId);
            CommentDTO commentDTO = commentService.mapToDTO(comment);
            return ResponseEntity.ok(commentDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/upload/{uploadId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByUploadId(@PathVariable Long uploadId) {
        List<CommentDTO> comments = commentService.getCommentsByUploadId(uploadId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/{commentId}/replies")
    public ResponseEntity<List<CommentDTO>> getRepliesByCommentId(@PathVariable Long commentId) {
        List<CommentDTO> replies = commentService.getRepliesByCommentId(commentId);
        return ResponseEntity.ok(replies);
    }
}