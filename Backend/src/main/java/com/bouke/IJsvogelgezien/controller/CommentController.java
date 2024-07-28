package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.dto.CommentDTO;
import com.bouke.IJsvogelgezien.model.Comment;
import com.bouke.IJsvogelgezien.repository.CommentRepository;
import com.bouke.IJsvogelgezien.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentRepository commentRepository;

//    @PostMapping("/add")
//    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO request) {
//        CommentDTO commentDTO = commentService.addComment(request);
//        return ResponseEntity.ok(commentDTO);
//    }

    @PostMapping("/add")
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO request) {
        CommentDTO commentDTO = commentService.addComment(request);
        return ResponseEntity.ok(commentDTO);
    }

    @GetMapping("/observation/{observationId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByObservation(@PathVariable Long observationId) {
        List<CommentDTO> comments = commentService.getCommentsByObservation(observationId);
        return ResponseEntity.ok(comments);
    }

//    @PutMapping("/update/{commentId}")
//    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId,
//                                                    @RequestParam String newText) {
//        CommentDTO updatedComment = commentService.updateComment(commentId, newText);
//        return ResponseEntity.ok(updatedComment);
//    }


    @PutMapping("/update/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO request) {
        CommentDTO updatedComment = commentService.updateComment(commentId, request.getText());
        return ResponseEntity.ok(updatedComment);
    }


    @DeleteMapping("/delete/{commentId}")
    public ResponseEntity<Map<String, String>> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(Map.of(
                "message", "Comment deleted successfully",
                "status", "200"
        ));
    }


    //    get replies to a specific comment of given id
    @GetMapping("/replies/{parentCommentId}")
    public ResponseEntity<List<CommentDTO>> getReplies(@PathVariable Long parentCommentId) {
        List<CommentDTO> replies = commentService.getCommentReplies(parentCommentId);
        return ResponseEntity.ok(replies);
    }
}