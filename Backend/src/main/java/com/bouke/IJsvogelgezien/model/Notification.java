package com.bouke.IJsvogelgezien.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date date;

    private Boolean read;

    public Notification() {
        this.date = new Date();
        this.read = false;
    }

    public Notification(User user, String message) {
        this.user = user;
        this.message = message;
        this.date = new Date();
        this.read = false;
    }
}