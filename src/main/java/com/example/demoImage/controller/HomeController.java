package com.example.demoImage.controller;

import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demoImage.model.Student;
import com.example.demoImage.service.StudentService;



@RestController
@CrossOrigin
public class HomeController {
	/*
	 * @Autowired private StudentService studentService;
	 * 
	 * // api for testing purpose
	 * 
	 * @GetMapping("/") public String home() { return
	 * "welcome to image uploading app in spring boot!!"; }
	 * 
	 * 
	 * //saving the student data in database
	 * 
	 * @PostMapping("/saveDatadata") public Student saveStudent(@RequestBody Student
	 * student) {
	 * 
	 * String originalFilename= file.getOriginalFilename(); Pat
	 * 
	 * Student savedStudentsDate=studentService.saveStudentData(student); return
	 * savedStudentsDate; }
	 * 
	 * 
	 * 
	 * @PostMapping("/sav") public Student saveStudent(@RequestBody Student student)
	 * { System.out.println("Received student: " + student); Student
	 * savedStudentsDate = studentService.saveStudentData(student); return
	 * savedStudentsDate; }
	 */
	
	@Autowired
    private StudentService studentService;
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/webapp/images" ; 

    // API for testing purpose
    @GetMapping("/")
    public String home() {
        return "Welcome to the image uploading app in Spring Boot!!";
    }

    // Saving the student data in the database
    @PostMapping("/saveData")
    public Student saveStudent(@ModelAttribute Student student,
    		@RequestParam("image") MultipartFile file) throws IOException{
    	String originalFilename =file.getOriginalFilename();
    	Path fileNameAndPath = Paths.get(uploadDirectory, originalFilename);
    	Files.write(fileNameAndPath, file.getBytes());
    	student.setProfileImage(originalFilename);
    	
        System.out.println("Received student: " + student);
        Student savedStudentData = studentService.saveStudentData(student);
        return savedStudentData;
    }
    
    //fetching data by id
    @GetMapping("/student/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable int id)
    {
    	Student student=studentService.getStudentById(id);
    	return ResponseEntity.ok().body(student);
    }
    
    //fetching image of a particular student
    @GetMapping("/student/getProfileImage/{id}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable int id) throws IOException
    {
    	//fetching student obj from repository by id
    	Student student =studentService.getStudentById(id);
    	//get the image from student object
    	Path imagePath=Paths.get(uploadDirectory, student.getProfileImage());
    	//here we are fetching image from that particular path
    	Resource resource=new FileSystemResource(imagePath.toFile());
    	//here getting content type of image
    	String contentType = Files.probeContentType(imagePath);
    	//then we are parse image to display return to postman
    	return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
    	
    	
    }
    
    //fetch all student details
    @GetMapping("/student/AllStudentData")
    public List<Student> getAllStudentData()
    {
    	List<Student> allstudentData = studentService.getAllStudentData();
    	return allstudentData;
    }
    
    @GetMapping("/api/message")
    public String getMessage() {
        return "Welcome to the Student Learning Outcome Tracker!";
    }

}
