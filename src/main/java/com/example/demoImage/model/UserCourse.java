package com.example.demoImage.model;



import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_course")
public class UserCourse {

    @EmbeddedId
    private UserCourseId id;

    @ManyToOne
    @MapsId("userId") // Maps userId in UserCourseId
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("courseId") // Maps courseId in UserCourseId
    @JoinColumn(name = "course_id")
    private Course course;

    // Constructors
    public UserCourse() {}

    public UserCourse(User user, Course course) {
        this.user = user;
        this.course = course;
        this.id = new UserCourseId(user.getId(), course.getId());
    }

	public UserCourseId getId() {
		return id;
	}

	public void setId(UserCourseId id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

    // Getters and setters
    
}
