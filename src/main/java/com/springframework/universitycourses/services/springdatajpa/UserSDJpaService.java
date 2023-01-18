package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.model.User;
import com.springframework.universitycourses.repositories.UserRepository;
import com.springframework.universitycourses.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserSDJpaService implements UserService
{
	private final UserRepository userRepository;

	public UserSDJpaService(final UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}

	@Override
	public Optional<User> findByEmail(final String email)
	{
		return Optional.ofNullable(userRepository.findByEmail(email));
	}
}
