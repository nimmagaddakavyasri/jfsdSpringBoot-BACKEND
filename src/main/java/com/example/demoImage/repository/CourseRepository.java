package com.example.demoImage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demoImage.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{

}
