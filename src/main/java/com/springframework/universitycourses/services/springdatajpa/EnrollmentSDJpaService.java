package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.EnrollmentIdMapper;
import com.springframework.universitycourses.api.v1.mapper.EnrollmentMapper;
import com.springframework.universitycourses.api.v1.model.EnrollmentDTO;
import com.springframework.universitycourses.api.v1.model.EnrollmentIdDTO;
import com.springframework.universitycourses.exceptions.NotFoundException;
import com.springframework.universitycourses.model.Assignment;
import com.springframework.universitycourses.model.Enrollment;
import com.springframework.universitycourses.model.EnrollmentId;
import com.springframework.universitycourses.model.Student;
import com.springframework.universitycourses.repositories.AssignmentRepository;
import com.springframework.universitycourses.repositories.EnrollmentRepository;
import com.springframework.universitycourses.repositories.StudentRepository;
import com.springframework.universitycourses.services.EnrollmentService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class EnrollmentSDJpaService implements EnrollmentService
{
	private final EnrollmentRepository enrollmentRepository;
	private final StudentRepository studentRepository;
	private final AssignmentRepository assignmentRepository;
	private final EnrollmentMapper enrollmentMapper;
	private final EnrollmentIdMapper enrollmentIdMapper;

	public EnrollmentSDJpaService(final EnrollmentRepository enrollmentRepository, final StudentRepository studentRepository,
			final AssignmentRepository assignmentRepository, final EnrollmentMapper enrollmentMapper,
			final EnrollmentIdMapper enrollmentIdMapper)
	{
		this.enrollmentRepository = enrollmentRepository;
		this.studentRepository = studentRepository;
		this.assignmentRepository = assignmentRepository;
		this.enrollmentMapper = enrollmentMapper;
		this.enrollmentIdMapper = enrollmentIdMapper;
	}

	@Override
	public EnrollmentDTO findById(final EnrollmentIdDTO id)
	{
		Optional<Enrollment> enrollment = getEnrollmentRepository().findById(
				getEnrollmentIdMapper().enrollmentIdDTOToEnrollmentId(id));

		if (enrollment.isEmpty())
		{
			throw new NotFoundException("Enrollment Not Found");

		}

		return enrollment
				.map(getEnrollmentMapper()::enrollmentToEnrollmentDTO)
				.orElse(null);
	}

	@Override
	public Set<EnrollmentDTO> findAll()
	{
		return getEnrollmentRepository().findAll()
				.stream()
				.map(getEnrollmentMapper()::enrollmentToEnrollmentDTO)
				.collect(Collectors.toSet());
	}

	@Override
	public EnrollmentDTO createNew(final EnrollmentDTO object)
	{
		return this.save(object);
	}

	@Override
	public EnrollmentDTO save(final EnrollmentDTO object)
	{
		Enrollment enrollment = getEnrollmentMapper().enrollmentDTOToEnrollment(object);
		Optional<Student> student = getStudentRepository().findById(object.getId().getStudentId());

		if (student.isEmpty())
		{
			throw new NotFoundException("Student Not Found");
		}

		enrollment.setStudent(student.get());
		Optional<Assignment> assignment = getAssignmentRepository().findById(object.getId().getAssignmentId());

		if (assignment.isEmpty())
		{
			throw new NotFoundException("Assignment Not Found");
		}

		enrollment.setAssignment(assignment.get());

		return getEnrollmentMapper().enrollmentToEnrollmentDTO(
				getEnrollmentRepository().saveAndFlush(enrollment));
	}

	@Override
	public Enrollment save(Enrollment object)
	{
		return getEnrollmentRepository().save(object);
	}

	@Override
	public EnrollmentDTO update(final EnrollmentIdDTO id, EnrollmentDTO object)
	{
		object.setId(id);
		return this.save(object);
	}

	@Override
	public void delete(final EnrollmentDTO object)
	{
		getEnrollmentRepository().delete(getEnrollmentMapper().enrollmentDTOToEnrollment(object));
	}

	@Override
	public void deleteById(final EnrollmentIdDTO id)
	{
		EnrollmentId enrollmentId = getEnrollmentIdMapper().enrollmentIdDTOToEnrollmentId(id);
		Optional<Enrollment> enrollment = getEnrollmentRepository().findById(enrollmentId);

		if (enrollment.isEmpty())
		{
			throw new NotFoundException("Enrollment Not Found");
		}

		getEnrollmentRepository().deleteById(enrollmentId);
	}

	public EnrollmentRepository getEnrollmentRepository()
	{
		return enrollmentRepository;
	}

	public StudentRepository getStudentRepository()
	{
		return studentRepository;
	}

	public AssignmentRepository getAssignmentRepository()
	{
		return assignmentRepository;
	}

	public EnrollmentMapper getEnrollmentMapper()
	{
		return enrollmentMapper;
	}

	public EnrollmentIdMapper getEnrollmentIdMapper()
	{
		return enrollmentIdMapper;
	}
}
