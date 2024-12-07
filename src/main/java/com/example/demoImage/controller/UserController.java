package com.example.demoImage.controller;

import java.io.IOException;


import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.demoImage.dto.UserProfileResponse;
import com.example.demoImage.model.Course;
import com.example.demoImage.model.Student;
import com.example.demoImage.model.User;
import com.example.demoImage.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/student")
public class UserController {
	@Autowired
    private UserService userService;
	
	@Autowired
    private JavaMailSender mailSender;
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/webapp/images" ;
	
	public static String uploadDirectorycv = System.getProperty("user.dir") + "/src/main/webapp/cv";

    @PostMapping("/signup")
    public RedirectView signUpUser(@RequestBody User user) {
        userService.saveUser(user);
        return new RedirectView("/login");
    }
    
   
    @PostMapping("/saveData")
    public User saveStudent(@ModelAttribute User user, @RequestParam("image") MultipartFile file) throws IOException {
        if (file != null && !file.isEmpty()) {
            String originalFilename = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
            Files.write(fileNameAndPath, file.getBytes());
            user.setProfilePhoto(originalFilename);  
        }
        System.out.println("Received user: " + user);
        User savedUserData = userService.saveUser(user);
        return savedUserData;
    }
       
    @GetMapping("studentDetails/{id}")
    public User getStudentDetails(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }
    
    @PutMapping("/acceptUser/{id}")
    public String acceptStudent(@PathVariable Long id) {
        userService.acceptUser(id);
        return "Student accepted and email sent.";
    }
    
    @GetMapping("/acceptedStudents")
    public List<User> getAcceptedStudents() {
        return userService.getAcceptedStudents();
    }
    
    
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String role) {
        
        Optional<User> user = userService.authenticateAcceptedUser(email, password, role);

        if (user.isPresent()) {
            String responseMessage = switch (role) {
                case "admin" -> "Admin login successful";
                case "student" -> "Student login successful";
                default -> "Teacher login successful";
            };
            
            return ResponseEntity.ok(responseMessage);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials or user not accepted.");
        }
    }
    
    @GetMapping("/courses")
    public ResponseEntity<List<Course>> getUserCourses(@RequestParam String email) {
        try {
            List<Course> courses = userService.getCoursesForUser(email);
            return ResponseEntity.ok(courses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    
    @GetMapping("/profile")
    public UserProfileResponse getStudentProfile(@RequestParam String email) {
        User user = userService.getUserByEmail(email);

        // Construct the response
        UserProfileResponse response = new UserProfileResponse();
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setId(user.getId());
        
        response.setEnrolledCourses(
            user.getCourses().stream().map(course -> course.getCourseName()).toList()
        );

        return response;
    }
    
    
    @PostMapping("/upload-cv")
    public ResponseEntity<String> uploadCV(@RequestParam("email") String email, @RequestParam("cv") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file uploaded.");
            }

            // Save the file to the specified directory
            String originalFilename = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
            Files.write(fileNameAndPath, file.getBytes());

            // Update the user with the CV file path
            Optional<User> userOptional = userService.getUserByEmailOptional(email);

            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            User user = userOptional.get();
            user.setCvFilePath(originalFilename); // Save only the file name or the relative path
            userService.saveUser(user);

            return ResponseEntity.ok("CV uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload CV: " + e.getMessage());
        }
    }

}
