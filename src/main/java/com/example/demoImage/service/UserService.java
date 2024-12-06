package com.example.demoImage.service;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.demoImage.model.Course;
import com.example.demoImage.model.User;
import com.example.demoImage.model.UserCourse;
import com.example.demoImage.repository.CourseRepository;
import com.example.demoImage.repository.UserCourseRepository;
import com.example.demoImage.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private UserCourseRepository userCourseRepository; 

    public User saveUser(User user) {
        return userRepository.save(user);     
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public void acceptUser(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setAccept(1);
            userRepository.save(user);

            // Send an acceptance email
            sendAcceptanceEmail(user.getEmail(), user.getName());
        } else {
            throw new RuntimeException("Student not found for ID: " + userId);
        }
    }

    private void sendAcceptanceEmail(String email, String name) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Congratulations on Your Registration!");
            message.setText("Hello " + name + ",\n\nCongratulations on getting accepted on our platform! "
                    + "We are excited to have you onboard and look forward to your active participation.\n\n"
                    + "Best regards,\nYour Company Name");
            message.setFrom("nimmagaddakavya5@gmail.com");

            mailSender.send(message);
            System.out.println("Acceptance email sent successfully to: " + email);
        } catch (Exception e) {
            System.err.println("Error sending acceptance email: " + e.getMessage());
        }
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendMail() {
        sendEmail("kavyanimmagadda12@gmail.com",
                  "Your credentials will be validated by the admin",
                  "This is the body of the email.");
    }

    public void sendEmail(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("nimmagaddakavya5@gmail.com");

            mailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
    
    public List<User> getAcceptedStudents() {
        return userRepository.findByAccept(1);
    }
    
    public Optional<User> authenticateUser(String email, String password, String role) {
        // Only return the user if role is "student" and the user is accepted
        if ("student".equals(role)) {
            return userRepository.findByEmailAndPasswordAndRoleAndAccept(email, password, role, 1);
        }
        return Optional.empty();
    }
    
    public Optional<User> authenticateAcceptedUser(String email, String password, String role) {
        return userRepository.findByEmailAndPasswordAndRoleAndAccept(email, password, role, 1);
    }
    
    public void registerUserForCourse(Long userId, Long courseId) throws Exception {
        User user = userRepository.findById(userId).orElseThrow(() -> new Exception("User not found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new Exception("Course not found"));

        UserCourse userCourse = new UserCourse(user, course);
        userCourseRepository.save(userCourse);  // This will work without auto-generated conflicts
    }


    
    public List<Course> getCoursesForUser(String userEmail) throws Exception {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User not found"));
        return new ArrayList<>(user.getCourses());
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}
