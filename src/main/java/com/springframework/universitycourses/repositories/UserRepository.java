package com.springframework.universitycourses.repositories;

import com.springframework.universitycourses.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long>
{
	User findByEmail(String email);
}
