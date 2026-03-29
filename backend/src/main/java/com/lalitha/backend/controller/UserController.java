package com.lalitha.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lalitha.backend.entity.User;
import com.lalitha.backend.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // ✅ Add User
    @PostMapping
    public User addUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    // ✅ Get All Users
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}