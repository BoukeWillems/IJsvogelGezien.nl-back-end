package com.bouke.IJsvogelgezien.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LikeDTO {
    private Long id;
    private Long userId;
    private Long uploadId;
    private Date date;
}