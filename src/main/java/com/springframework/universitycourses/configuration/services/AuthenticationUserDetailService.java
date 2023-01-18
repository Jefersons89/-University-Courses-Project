package com.springframework.universitycourses.configuration.services;

import com.springframework.universitycourses.model.Role;
import com.springframework.universitycourses.model.User;
import com.springframework.universitycourses.services.springdatajpa.UserSDJpaService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AuthenticationUserDetailService implements UserDetailsService
{
	UserSDJpaService userSDJpaService;

	public AuthenticationUserDetailService(final UserSDJpaService userSDJpaService)
	{
		this.userSDJpaService = userSDJpaService;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
	{
		Optional<User> optionalUser = userSDJpaService.findByEmail(email);

		if (optionalUser.isEmpty())
		{
			throw new UsernameNotFoundException(email);
		}

		User apiUser = optionalUser.get();

		return new org.springframework.security.core.userdetails.User(
				apiUser.getEmail(),
				apiUser.getPassword(),
				this.mapRolesToAuthorities(apiUser.getRoles()));
	}

	private Collection<GrantedAuthority> mapRolesToAuthorities(Set<Role> roles)
	{
		return roles.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toSet());
	}
}
