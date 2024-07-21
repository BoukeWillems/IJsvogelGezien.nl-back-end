package com.bouke.IJsvogelgezien.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ObservationDTO {
    private Long id;
    private String description;
    private Date date;
    private double latitude;
    private double longitude;
    private String photoPath;
    private String username;
}