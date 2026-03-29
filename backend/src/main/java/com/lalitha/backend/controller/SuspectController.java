package com.lalitha.backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.lalitha.backend.entity.Suspect;
import com.lalitha.backend.service.SuspectService;

@RestController
@RequestMapping("/api/suspects")
@CrossOrigin(origins = "*")
public class SuspectController {

    @Autowired
    private SuspectService service;

    // ✅ GET all suspects
    @GetMapping
    public List<Suspect> getAll() {
        return service.getAllSuspects();
    }

    // ✅ GET suspect by ID
    @GetMapping("/{id}")
    public Suspect getById(@PathVariable Long id) {
        return service.getSuspectById(id);
    }

    // ✅ POST add new suspect
    @PostMapping
    public Suspect add(@RequestBody Suspect suspect) {
        return service.saveSuspect(suspect);
    }

    // ✅ DELETE suspect
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteSuspect(id);
        return "Deleted Successfully";
    }

    // ✅ POST upload image
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            return service.uploadImage(file);
        } catch (IOException e) {
            return "Image upload failed: " + e.getMessage();
        }
    }

    // 🔍 SEARCH BY NAME
    @GetMapping("/search/name")
    public List<Suspect> searchByName(@RequestParam String name) {
        return service.searchByName(name);
    }

    // 🔥 GLOBAL SEARCH (NAME + CITY + CRIME)
    @GetMapping("/search")
    public List<Suspect> searchAll(@RequestParam String keyword) {
        return service.searchAll(keyword);
    }
}