package com.lalitha.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lalitha.backend.entity.Suspect;
import java.util.List;
@Repository
public interface SuspectRepository extends JpaRepository<Suspect, Long> {

    // 🔍 Search by name (case-insensitive)
    List<Suspect> findByNameContainingIgnoreCase(String name);

    // 🔍 Search by city
    List<Suspect> findByCityContainingIgnoreCase(String city);

    // 🔍 Search by crime
    List<Suspect> findByCrimeContainingIgnoreCase(String crime);

    // 🔥 ADVANCED: search by multiple fields
    List<Suspect> findByNameContainingIgnoreCaseOrCityContainingIgnoreCaseOrCrimeContainingIgnoreCase(
        String name, String city, String crime
    );
}
