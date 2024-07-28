package com.bouke.IJsvogelgezien.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "observation_id", nullable = false)
    private Observation observation;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;


    public Like() {
        this.date = new Date();
    }

    public Like(User user, Observation observation) {
        this.user = user;
        this.observation = observation;
        this.date = new Date();
    }
}