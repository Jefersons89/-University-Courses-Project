package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.model.Assignment;
import com.springframework.universitycourses.repositories.AssignmentRepository;
import com.springframework.universitycourses.services.AssignmentService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class AssignmentSDJpaService implements AssignmentService
{
	private final AssignmentRepository assignmentRepository;

	public AssignmentSDJpaService(final AssignmentRepository assignmentRepository)
	{
		this.assignmentRepository = assignmentRepository;
	}

	@Override
	public Assignment findById(final Long id)
	{
		return getAssignmentRepository().findById(id).orElse(null);
	}

	@Override
	public Assignment findByTitle(final String title)
	{
		return getAssignmentRepository().findByTitle(title);
	}

	@Override
	public Set<Assignment> findAll()
	{
		Set<Assignment> assignments = new HashSet<>();
		getAssignmentRepository().findAll().forEach(assignments::add);
		return assignments;
	}

	@Override
	public Assignment save(final Assignment object)
	{
		return getAssignmentRepository().save(object);
	}

	@Override
	public void delete(final Assignment object)
	{
		getAssignmentRepository().delete(object);
	}

	@Override
	public void deleteById(final Long id)
	{
		getAssignmentRepository().deleteById(id);
	}

	public AssignmentRepository getAssignmentRepository()
	{
		return assignmentRepository;
	}
}
