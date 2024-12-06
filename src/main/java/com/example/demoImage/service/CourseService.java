package com.example.demoImage.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demoImage.model.Course;
import com.example.demoImage.model.User;
import com.example.demoImage.repository.CourseRepository;
import com.example.demoImage.repository.UserRepository;

@Service
public class CourseService {
	@Autowired
    private CourseRepository courseRepository;
	
	@Autowired
    private UserRepository userRepository;

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }
    public List<Course> getOfferedCourses() {
        return courseRepository.findAll(); // Or apply any filter to fetch only offered courses
    }
    
    public Course getCourseById(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        return course.orElse(null);  // Return the course or null if not found
    }
    
    public void registerUserForCourse(String userEmail, Long courseId) throws Exception {
        // Retrieve user and course from repositories
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new Exception("User with email " + userEmail + " not found"));
        
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new Exception("Course with ID " + courseId + " not found"));

        // Check if the user is already enrolled in the course
        if (user.getCourses().contains(course)) {
            throw new Exception("User is already registered for this course.");
        }

        // Add the course to the user's set of courses
        user.getCourses().add(course);

        // Persist the updated user entity with the new course association
        userRepository.save(user);
    }

    
    

}
