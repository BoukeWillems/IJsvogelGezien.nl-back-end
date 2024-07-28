package com.bouke.IJsvogelgezien.controller;

import com.bouke.IJsvogelgezien.dto.LikeDTO;
import com.bouke.IJsvogelgezien.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    @Autowired
    private LikeService likeService;

    @PostMapping("/like_unlike/{uploadId}")
    public ResponseEntity<Map<String,String>> addLike(@PathVariable Long uploadId) {
        return likeService.likeUnlike(uploadId);
    }


    @GetMapping("/observation/{uploadId}")
    public ResponseEntity<List<LikeDTO>> getLikesByObservation(@PathVariable Long uploadId) {
        List<LikeDTO> likes = likeService.getLikesByObservation(uploadId);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/observation/count/{uploadId}")
    public ResponseEntity<Map<String,String>> ObservationLikeCounter(@PathVariable long uploadId){
        List<LikeDTO> likes = likeService.getLikesByObservation(uploadId);
        int count = (int) likes.stream().count();
        return ResponseEntity.ok(Map.of(

                "ObservationId", String.valueOf(uploadId),
                "Total Likes",String.valueOf(count) ,
                "status", "success"

        ));
    }
}