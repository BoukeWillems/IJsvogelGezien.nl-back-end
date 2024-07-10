package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.model.Comment;
import com.bouke.IJsvogelgezien.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public String addComment(@RequestParam String text, @RequestParam Long observationId, @RequestParam Long userId) {
        commentService.addComment(text, observationId, userId);
        return "Comment added successfully";
    }

    @GetMapping("/observation/{observationId}")
    public List<Comment> getCommentsByObservation(@PathVariable Long observationId) {
        return commentService.getCommentsByObservation(observationId);
    }
}
