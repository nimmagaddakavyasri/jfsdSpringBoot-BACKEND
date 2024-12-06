package com.example.demoImage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoImage.model.Admin;
import com.example.demoImage.repository.AdminRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;  // Use AdminRepository instead of AdminService

    // Validating admin credentials
    public boolean validateAdmin(String email, String password, String role) {
        // Hardcoding the admin credentials
        if (email.equals("admin@gmail.com") && password.equals("admin") && role.equals("admin")) {
            return true;
        }

        // Checking if the admin exists in the repository
        Admin admin = adminRepository.findByEmail(email);
        return admin != null && admin.getPassword().equals(password) && admin.getRole().equals(role);
    }

    // Optional: Save an admin (can be used for testing or adding more admins)
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
}
