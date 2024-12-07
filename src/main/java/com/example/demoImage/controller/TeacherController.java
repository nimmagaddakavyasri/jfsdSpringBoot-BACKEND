package com.example.demoImage.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demoImage.model.Teacher;
import com.example.demoImage.service.TeacherService;

@RestController
@CrossOrigin
@RequestMapping("/teacher")
public class TeacherController {
	@Autowired
    private TeacherService teacherService;
	public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/webapp/images";
	
	@PostMapping("/signup")
    public ResponseEntity<String> signUpTeacher(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("role") String role,
            @RequestParam("profilePhoto") MultipartFile profilePhoto) {
        
        try {
            Teacher teacher = new Teacher();
            teacher.setName(name);
            teacher.setEmail(email);
            teacher.setPassword(password);
            teacher.setRole(role); 
            
            if (profilePhoto != null && !profilePhoto.isEmpty()) {
                String originalFilename = profilePhoto.getOriginalFilename();
                Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
                Files.write(fileNameAndPath, profilePhoto.getBytes());
                teacher.setProfilePhoto(originalFilename); 
            }
            
            teacherService.saveTeacher(teacher);       
            return ResponseEntity.ok("Teacher registration successful");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving teacher data");
        }
	}
		
	@GetMapping("/pendingFaculty")
    public ResponseEntity<List<Teacher>> getPendingFaculty() {
        List<Teacher> pendingTeachers = teacherService.getPendingTeachers();
        return ResponseEntity.ok(pendingTeachers);
    }
	
	 @PutMapping("/acceptFaculty/{facultyId}")
	    public ResponseEntity<String> acceptFaculty(@PathVariable Long facultyId) {
	        boolean isAccepted = teacherService.acceptFaculty(facultyId);
	        if (isAccepted) {
	            return ResponseEntity.ok("Faculty accepted successfully.");
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error accepting faculty.");
	        }
	    }
	 
	 /*
	 @GetMapping("/rejectedFaculty")
	    public List<Teacher> getRejectedFaculty() {
	        return teacherService.getRejectedFaculty();
	    }
	 */
	 
	 @PostMapping("/login")
	    public String loginTeacher(
	        @RequestParam String email,
	        @RequestParam String password,
	        @RequestParam String role
	    ) {
	        return teacherService.login(email, password, role);
	    }
	 
	 @GetMapping("/rejected-faculty")
	    public List<Teacher> getRejectedTeachers() {
	        // Delegate the logic to the service layer
	        return teacherService.getRejectedTeachers();
	    }
	 
	 @GetMapping("/profile/{email}")
	    public Teacher getTeacherProfile(@PathVariable String email) {
	        Teacher teacher = teacherService.getTeacherByEmail(email);
	        if (teacher == null) {
	            throw new RuntimeException("Teacher not found for email: " + email);
	        }
	        return teacher;
	    }

}
