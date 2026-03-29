package com.lalitha.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lalitha.backend.entity.Alert;
import com.lalitha.backend.service.AlertService;

@RestController
@RequestMapping("/api/alerts")
@CrossOrigin
public class AlertController {

    @Autowired
    private AlertService alertService;

    // 🔥 Create Alert (simulate AI detection)
    @PostMapping("/detect")
    public Alert createAlert(
            @RequestParam Long suspectId,
            @RequestParam Long userId,
            @RequestParam String location) {

        return alertService.createAlert(suspectId, userId, location);
    }

    // Get all alerts
    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }
}