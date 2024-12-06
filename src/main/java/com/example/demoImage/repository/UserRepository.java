package com.example.demoImage.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoImage.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	List<User> findByAccept(int accept);
	Optional<User> findByEmailAndPasswordAndRoleAndAccept(String email, String password, String role, int accept);
	Optional<User> findByEmail(String email);
}
