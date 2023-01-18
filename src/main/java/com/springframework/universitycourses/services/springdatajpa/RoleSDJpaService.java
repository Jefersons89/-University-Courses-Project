package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.model.Role;
import com.springframework.universitycourses.repositories.RoleRepository;
import com.springframework.universitycourses.services.RoleService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class RoleSDJpaService implements RoleService
{
	private final RoleRepository roleRepository;

	public RoleSDJpaService(final RoleRepository roleRepository)
	{
		this.roleRepository = roleRepository;
	}

	@Override
	public Role findById(final Long id)
	{
		return roleRepository.findById(id).orElse(null);
	}

	@Override
	public Set<Role> findAll()
	{
		return new HashSet<>(roleRepository.findAll());
	}

	@Override
	public Role createNew(final Role object)
	{
		return roleRepository.save(object);
	}

	@Override
	public Role save(final Role object)
	{
		return roleRepository.save(object);
	}

	@Override
	public Role update(final Long id, final Role object)
	{
		object.setId(id);
		return roleRepository.save(object);
	}

	@Override
	public void delete(final Role object)
	{
		roleRepository.delete(object);
	}

	@Override
	public void deleteById(final Long id)
	{
		roleRepository.deleteById(id);
	}
}
