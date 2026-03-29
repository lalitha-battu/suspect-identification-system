package com.lalitha.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lalitha.backend.entity.Alert;
import com.lalitha.backend.entity.Suspect;
import com.lalitha.backend.entity.User;
import com.lalitha.backend.repository.AlertRepository;
import com.lalitha.backend.repository.SuspectRepository;
import com.lalitha.backend.repository.UserRepository;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private SuspectRepository suspectRepository;

    @Autowired
    private UserRepository userRepository;

    // 🔥 MAIN LOGIC: Create Alert with Duplicate Check
    public Alert createAlert(Long suspectId, Long userId, String location) {

        // ✅ Fetch Suspect
        Suspect suspect = suspectRepository.findById(suspectId)
                .orElseThrow(() -> new RuntimeException("Suspect not found"));

        // ✅ Fetch User
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Get last alert
        Alert lastAlert = alertRepository.findTopByOrderByIdDesc();

        // ✅ Prevent duplicate alert (same suspect + same location within short time)
        if (lastAlert != null &&
            lastAlert.getSuspect().getId().equals(suspectId) &&
            lastAlert.getLocation().equalsIgnoreCase(location)) {

            // ⏱️ Optional: allow new alert after 5 seconds
            if (lastAlert.getDetectedTime() != null &&
                lastAlert.getDetectedTime().plusSeconds(5).isAfter(LocalDateTime.now())) {

                return lastAlert; // ❌ Skip duplicate
            }
        }

        // ✅ Create new alert
        Alert alert = new Alert();
        alert.setSuspect(suspect);
        alert.setUser(user);
        alert.setLocation(location);
        alert.setDetectedTime(LocalDateTime.now());

        return alertRepository.save(alert);
    }

    // ✅ Get all alerts
    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }
}