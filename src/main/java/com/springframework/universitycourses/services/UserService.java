package com.springframework.universitycourses.services;

import com.springframework.universitycourses.model.User;

import java.util.Optional;


public interface UserService
{
	Optional<User> findByEmail(String email);
}
