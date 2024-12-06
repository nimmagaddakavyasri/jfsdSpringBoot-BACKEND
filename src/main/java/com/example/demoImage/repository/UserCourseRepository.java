package com.example.demoImage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoImage.model.UserCourse;
import com.example.demoImage.model.UserCourseId;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseId>{

}
