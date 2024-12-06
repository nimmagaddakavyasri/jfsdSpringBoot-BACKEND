package com.example.demoImage.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoImage.model.Course;
import com.example.demoImage.model.CourseRegistrationDTO;
import com.example.demoImage.model.RegistrationRequest;
import com.example.demoImage.service.CourseService;
import com.example.demoImage.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/course")
public class CourseController {
	@Autowired
    private CourseService courseService;
	@Autowired
    private UserService userService;

    @PostMapping("/add-course")
    public ResponseEntity<String> addCourse(@RequestBody Course course) {
        try {
            courseService.addCourse(course);
            return ResponseEntity.ok("Course added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error adding course");
        }
    }
    /*
    @GetMapping("/offered")
    public ResponseEntity<List<Course>> getOfferedCourses() {
        try {
            List<Course> offeredCourses = courseService.getOfferedCourses();
            return ResponseEntity.ok(offeredCourses);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    } */
    
    @GetMapping("/offeredCourses")
    public List<Course> getOfferedCourses() {
        return courseService.getOfferedCourses();  // Returns list of courses from the service
    }
    
    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable Long courseId) {
        return courseService.getCourseById(courseId);
    }
    
    
    @PostMapping("/register")
    public ResponseEntity<String> registerUserForCourse(@RequestBody RegistrationRequest request) {
        try {
            userService.registerUserForCourse(request.getUserId(), request.getCourseId());
            return ResponseEntity.ok("Registration successful!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering for the course.");
        }
    }
   

}
