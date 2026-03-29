package com.lalitha.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lalitha.backend.entity.Alert;

@Repository
public interface AlertRepository extends JpaRepository<Alert, Long> {

    // Custom query (Spring will auto-implement this)
    Alert findTopByOrderByIdDesc();

}