package com.lalitha.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lalitha.backend.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}