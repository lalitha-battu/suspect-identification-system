package com.lalitha.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String location;
    private LocalDateTime detectedTime;

    // Many alerts for one suspect
    @ManyToOne
    @JoinColumn(name = "suspect_id")
    private Suspect suspect;

    // Many alerts for one user (police)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Getters & Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getLocation() { return location; }

    public void setLocation(String location) { this.location = location; }

    public LocalDateTime getDetectedTime() { return detectedTime; }

    public void setDetectedTime(LocalDateTime detectedTime) { this.detectedTime = detectedTime; }

    public Suspect getSuspect() { return suspect; }

    public void setSuspect(Suspect suspect) { this.suspect = suspect; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}