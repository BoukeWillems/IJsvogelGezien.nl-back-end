package com.bouke.IJsvogelgezien.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class NotificationDTO {
    private Long id;
    private Long userId;
    private String message;
    private Date date;
    private Boolean read;
}