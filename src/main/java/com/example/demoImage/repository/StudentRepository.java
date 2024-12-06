package com.example.demoImage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoImage.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
	

}
