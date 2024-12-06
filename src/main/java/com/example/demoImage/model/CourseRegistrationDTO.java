package com.example.demoImage.model;

public class CourseRegistrationDTO {
	private String userEmail;  // Email of the user
    private Long courseId;     // ID of the course

    // Getters and Setters
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }
}
