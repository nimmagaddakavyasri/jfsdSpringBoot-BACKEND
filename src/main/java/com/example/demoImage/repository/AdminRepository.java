package com.example.demoImage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demoImage.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByEmail(String email);  // Updated to match the 'email' field in Admin entity
}
