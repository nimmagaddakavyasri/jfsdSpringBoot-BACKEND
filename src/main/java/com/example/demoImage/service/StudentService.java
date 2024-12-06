package com.example.demoImage.service;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demoImage.model.Student;
import com.example.demoImage.repository.StudentRepository;

@Service
public class StudentService {
	/*
	 * @Autowired private StudentRepository studentRepository;
	 * 
	 * public Student saveStudentData(Student student) { // TODO Auto-generated
	 * method stub Student savedStudent=studentRepository.save(student); return
	 * savedStudent;
	 * 
	 * }
	 */
	
	
	 @Autowired
	    private StudentRepository studentRepository;

	    public Student saveStudentData(Student student) {
	        // Save the student and return the saved instance
	        return studentRepository.save(student);
	    }
	    
	    public Student getStudentById(int id) {
	    Optional<Student> findById = studentRepository.findById(id);
	    Student student = findById.get();
	    return student;
	    }
	    
	    public List<Student> getAllStudentData() {
		    List<Student> studentList = studentRepository.findAll();
		    return studentList;
		    }
	    
	    
	      
	    
}
