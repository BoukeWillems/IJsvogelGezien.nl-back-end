package com.bouke.IJsvogelgezien.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String text;
    private Date date;
    private Long userId;
    private String username;
    private Long uploadId;
    private Long parentCommentId;
    private List<CommentDTO> replies;
}