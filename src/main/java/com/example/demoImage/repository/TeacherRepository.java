package com.example.demoImage.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoImage.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	List<Teacher> findByAcceptFalse();
	List<Teacher> findByAccept(boolean accept);
	Teacher findByEmailAndPasswordAndRole(String email, String password, String role);
	
	
}
