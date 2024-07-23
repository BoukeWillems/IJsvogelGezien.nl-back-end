package com.bouke.IJsvogelgezien.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "observations")
public class Observation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private double latitude;

    private double longitude;

    private String photoPath;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "upload", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Like> likes;

}