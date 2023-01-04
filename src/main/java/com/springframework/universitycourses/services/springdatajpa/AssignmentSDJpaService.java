package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.AssignmentMapper;
import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.model.Assignment;
import com.springframework.universitycourses.model.Enrollment;
import com.springframework.universitycourses.repositories.AssignmentRepository;
import com.springframework.universitycourses.repositories.EnrollmentRepository;
import com.springframework.universitycourses.services.AssignmentService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AssignmentSDJpaService implements AssignmentService
{
	private final AssignmentRepository assignmentRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final AssignmentMapper assignmentMapper;

	public AssignmentSDJpaService(final AssignmentRepository assignmentRepository, final EnrollmentRepository enrollmentRepository,
			final AssignmentMapper assignmentMapper)
	{
		this.assignmentRepository = assignmentRepository;
		this.enrollmentRepository = enrollmentRepository;
		this.assignmentMapper = assignmentMapper;
	}

	@Override
	public AssignmentDTO findById(final Long id)
	{
		Optional<Assignment> assignment = getAssignmentRepository().findById(id);
		assignment.ifPresent(value -> setEnrollments(value, getEnrollmentRepository().findAll()));

		return assignment
				.map(getAssignmentMapper()::assignmentToAssignmentDTO)
				.orElse(null);
	}

	@Override
	public Set<AssignmentDTO> findAll()
	{
		List<Assignment> assignments = getAssignmentRepository().findAll();
		assignments.forEach(assignment -> setEnrollments(assignment, getEnrollmentRepository().findAll()));

		return assignments.stream()
				.map(getAssignmentMapper()::assignmentToAssignmentDTO)
				.collect(Collectors.toSet());
	}

	private static void setEnrollments(final Assignment assignment, final List<Enrollment> enrollments)
	{
		assignment.setEnrollments(new HashSet<>(enrollments.stream()
				.filter(enrollment -> Objects.equals(enrollment.getId().getAssignmentId(), assignment.getId()))
				.collect(Collectors.toSet())));
	}

	@Override
	public AssignmentDTO createNew(final AssignmentDTO object)
	{
		return this.save(object);
	}

	@Override
	public AssignmentDTO save(final AssignmentDTO object)
	{
		return getAssignmentMapper().assignmentToAssignmentDTO(getAssignmentRepository()
				.saveAndFlush(getAssignmentMapper()
						.assignmentDTOToAssignment(object)));
	}

	@Override
	public Assignment save(Assignment object)
	{
		return getAssignmentRepository().save(object);
	}

	@Override
	public AssignmentDTO update(final Long id, AssignmentDTO object)
	{
		object.setId(id);
		return this.save(object);
	}

	@Override
	public void delete(final AssignmentDTO object)
	{
		getAssignmentRepository().delete(getAssignmentMapper().assignmentDTOToAssignment(object));
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

	public EnrollmentRepository getEnrollmentRepository()
	{
		return enrollmentRepository;
	}

	public AssignmentMapper getAssignmentMapper()
	{
		return assignmentMapper;
	}
}
