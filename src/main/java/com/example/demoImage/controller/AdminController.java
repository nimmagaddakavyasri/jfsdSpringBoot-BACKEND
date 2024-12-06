package com.example.demoImage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

import com.example.demoImage.model.Teacher;
import com.example.demoImage.model.User;
import com.example.demoImage.service.AdminService;
import com.example.demoImage.service.TeacherService;
import com.example.demoImage.service.UserService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private TeacherService teacherService;
    

    // API endpoint for admin login
    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password, 
                        @RequestParam String role) {
        boolean isValid = adminService.validateAdmin(email, password, role);
        if (isValid) {
            return "Login successful!";
        } else {
            return "Invalid credentials!";
        }
    }
    
    @GetMapping("/students")
    public List<User> getAllStudents() {
        return userService.getAllUsers(); // Return list of students
    }
    
    @GetMapping("/acceptedFaculty")
    public ResponseEntity<List<Teacher>> getAcceptedFaculty() {
        List<Teacher> acceptedFaculty = teacherService.getAcceptedFaculty();
        return ResponseEntity.ok(acceptedFaculty);
    }
    
    

}
