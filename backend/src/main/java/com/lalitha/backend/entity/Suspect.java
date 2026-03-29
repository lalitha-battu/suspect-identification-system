package com.lalitha.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Suspect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String crime;
    private String city;
    private String crimeDetails;
    private String imagePath;

    // Default Constructor
    public Suspect() {}

    // Parameterized Constructor
    public Suspect(Long id, String name, int age, String crime, String city,
                   String crimeDetails, String imagePath) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.crime = crime;
        this.city = city;
        this.crimeDetails = crimeDetails;
        this.imagePath = imagePath;
    }

    // ✅ Getters & Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    // 🔥 IMPORTANT FIX
    public String getCrime() { return crime; }
    public void setCrime(String crime) { this.crime = crime; }

    // 🔥 IMPORTANT FIX
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getCrimeDetails() { return crimeDetails; }
    public void setCrimeDetails(String crimeDetails) { this.crimeDetails = crimeDetails; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
}