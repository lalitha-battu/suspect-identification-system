package com.lalitha.backend.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lalitha.backend.entity.Suspect;
import com.lalitha.backend.repository.SuspectRepository;

import jakarta.annotation.PostConstruct;

@Service
public class SuspectService {

    @Autowired
    private SuspectRepository repo;

    @Value("${file.upload-dir}")
    private String uploadDir;
    
    @PostConstruct
    public void init() {
        System.out.println("UPLOAD DIR: " + uploadDir);
    }

    // ✅ GET ALL SUSPECTS
    public List<Suspect> getAllSuspects() {
        return repo.findAll();
    }

    // ✅ GET BY ID
    public Suspect getSuspectById(Long id) {
        return repo.findById(id).orElse(null);
    }

    // ✅ SAVE SUSPECT
    public Suspect saveSuspect(Suspect suspect) {
        return repo.save(suspect);
    }

    // ✅ DELETE SUSPECT
    public void deleteSuspect(Long id) {
        repo.deleteById(id);
    }

    // ✅ IMAGE UPLOAD
    public String uploadImage(MultipartFile file) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        File folder = new File("C:/suspect-uploads/");

        if (!folder.exists()) {
            folder.mkdirs(); // ✅ create folder
        }

        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

        String newFileName = UUID.randomUUID().toString() + extension;

        File destination = new File(folder, newFileName);

        file.transferTo(destination); // ✅ save file

        return "/uploads/" + newFileName;
    }

    // 🔍 SEARCH BY NAME
    public List<Suspect> searchByName(String name) {
        return repo.findByNameContainingIgnoreCase(name);
    }

    // 🔥 GLOBAL SEARCH (NAME + CITY + CRIME)
    public List<Suspect> searchAll(String keyword) {
        return repo
            .findByNameContainingIgnoreCaseOrCityContainingIgnoreCaseOrCrimeContainingIgnoreCase(
                keyword, keyword, keyword
            );
    }
}