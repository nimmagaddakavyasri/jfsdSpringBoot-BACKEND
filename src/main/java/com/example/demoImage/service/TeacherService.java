package com.example.demoImage.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoImage.model.Teacher;
import com.example.demoImage.repository.TeacherRepository;

@Service
public class TeacherService {
	@Autowired
    private TeacherRepository teacherRepository;

	 public void saveTeacher(Teacher teacher) {
		 teacher.setRole("teacher");
	        teacherRepository.save(teacher);
	        System.out.println("Teacher registered successfully");
	    }
	 
	 public List<Teacher> getPendingTeachers() 
	 {
	        return teacherRepository.findByAcceptFalse();
	 }
	 
	 public boolean acceptFaculty(Long facultyId) 
	 {
	        Optional<Teacher> teacherOpt = teacherRepository.findById(facultyId);
	        if (teacherOpt.isPresent()) 
	        {
	            Teacher teacher = teacherOpt.get();
	            teacher.setAccept(true); 
	            teacherRepository.save(teacher);
	            return true; 
	        }
	        return false;
    }
	 
	 public List<Teacher> getAcceptedFaculty() 
	 {
	        return teacherRepository.findByAccept(true); 
	 }
	 
	 public List<Teacher> getRejectedTeachers() 
	 {
	        return teacherRepository.findByAccept(false);	    
	 }
	 
	 
	 public String login(String email, String password, String role) 
	 {
	        Teacher teacher = teacherRepository.findByEmailAndPasswordAndRole(email, password, role);
	        if (teacher == null) 
	        {
	            return "Invalid credentials";
	        } 
	        if (!teacher.isAccept()) 
	        {
	            return "Account not accepted by admin";
	        }
	        return "Teacher login successful";
	 }
	 
	 public Teacher getTeacherByEmail(String email) 
	 {
	        return teacherRepository.findByEmail(email);  // Find teacher by email
	 }


}
