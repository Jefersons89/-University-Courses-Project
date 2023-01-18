package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.StudentMapper;
import com.springframework.universitycourses.api.v1.model.StudentDTO;
import com.springframework.universitycourses.exceptions.NotFoundException;
import com.springframework.universitycourses.model.Assignment;
import com.springframework.universitycourses.model.Enrollment;
import com.springframework.universitycourses.model.Role;
import com.springframework.universitycourses.model.Student;
import com.springframework.universitycourses.repositories.AssignmentRepository;
import com.springframework.universitycourses.repositories.EnrollmentRepository;
import com.springframework.universitycourses.repositories.RoleRepository;
import com.springframework.universitycourses.repositories.StudentRepository;
import com.springframework.universitycourses.services.StudentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class StudentSDJpaService implements StudentService
{
	private final StudentRepository studentRepository;
	private final EnrollmentRepository enrollmentRepository;
	private final AssignmentRepository assignmentRepository;
	private final RoleRepository roleRepository;
	private final StudentMapper studentMapper;
	private final BCryptPasswordEncoder passwordEncoder;

	public StudentSDJpaService(final StudentRepository studentRepository, final EnrollmentRepository enrollmentRepository,
			final AssignmentRepository assignmentRepository, final RoleRepository roleRepository, final StudentMapper studentMapper,
			final BCryptPasswordEncoder passwordEncoder)
	{
		this.studentRepository = studentRepository;
		this.enrollmentRepository = enrollmentRepository;
		this.assignmentRepository = assignmentRepository;
		this.roleRepository = roleRepository;
		this.studentMapper = studentMapper;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public StudentDTO findById(final Long id)
	{
		Optional<Student> student = getStudentRepository().findById(id);

		if (student.isEmpty())
		{
			throw new NotFoundException("Student Not Found for id: " + id);
		}

		student.ifPresent(value -> setEnrollments(getEnrollmentRepository().findAll(), value));

		return student
				.map(getStudentMapper()::studentToStudentDTO)
				.orElse(null);
	}

	@Override
	public Set<StudentDTO> findAll()
	{
		Pageable sortedByFirstName =
				PageRequest.of(0, 3, Sort.by("firstName"));

		Page<Student> students = getStudentRepository().findAll(sortedByFirstName);

		students.forEach(student -> setEnrollments(getEnrollmentRepository().findAll(), student));

		return students.stream()
				.map(getStudentMapper()::studentToStudentDTO)
				.collect(Collectors.toSet());
	}

	private static void setEnrollments(final List<Enrollment> enrollments, final Student student)
	{
		if (Objects.nonNull(enrollments))
		{
			student.setEnrollments(new HashSet<>(enrollments.stream()
					.filter(enrollment -> Objects.equals(enrollment.getId().getStudentId(), student.getId()))
					.collect(Collectors.toSet())));
		}
		else
		{
			student.setEnrollments(Collections.emptySet());
		}
	}

	@Override
	public StudentDTO createNew(final StudentDTO object)
	{
		return this.save(object);
	}

	@Override
	public StudentDTO save(final StudentDTO object)
	{
		object.setEnrollments(new HashSet<>());

		Student studentToSave = getStudentMapper().studentDTOToStudent(object);

		if (Objects.nonNull(object.getRoles()) && !object.getRoles().isEmpty())
		{
			Set<Role> roles = new HashSet<>();
			object.getRoles().forEach(role -> {
				Optional<Role> roleOptional = roleRepository.findByName(role);
				roleOptional.ifPresent(roles::add);
			});
			studentToSave.setRoles(roles);
		}

		studentToSave.setPassword(passwordEncoder.encode(studentToSave.getPassword()));

		return getStudentMapper().studentToStudentDTO(
				getStudentRepository().saveAndFlush(studentToSave));
	}

	@Override
	public Student save(final Student object)
	{
		return getStudentRepository().save(object);
	}

	@Override
	public StudentDTO update(final Long id, StudentDTO object)
	{
		Optional<Student> student = getStudentRepository().findById(id);

		if (student.isEmpty())
		{
			throw new NotFoundException("Student Not Found for id: " + id);
		}

		object.setId(id);
		return this.save(object);
	}

	@Override
	public void delete(final StudentDTO object)
	{
		getStudentRepository().delete(getStudentMapper().studentDTOToStudent(object));
	}

	@Override
	public void deleteById(final Long id)
	{
		Optional<Student> student = getStudentRepository().findById(id);

		if (student.isEmpty())
		{
			throw new NotFoundException("Student Not Found for id: " + id);
		}

		student.ifPresent(value -> {
			setEnrollments(getEnrollmentRepository().findAll(), value);
			value.getEnrollments().forEach(enrollment -> {
				enrollment.setStudent(null);

				Assignment assignment = enrollment.getAssignment();
				Optional<Enrollment> enrollmentToRemove = assignment.getEnrollments().stream()
						.filter(enrollment1 -> enrollment.getId().equals(enrollment1.getId())).findFirst();
				enrollmentToRemove.ifPresent(enrollmentToRemove1 -> assignment.getEnrollments().remove(enrollmentToRemove1));

				getAssignmentRepository().save(assignment);

				enrollment.setAssignment(null);
				getEnrollmentRepository().save(enrollment);
				getEnrollmentRepository().deleteById(enrollment.getId());
			});
			value.setEnrollments(null);
			this.save(value);
		});
		getStudentRepository().deleteById(id);
	}

	public StudentRepository getStudentRepository()
	{
		return studentRepository;
	}

	public EnrollmentRepository getEnrollmentRepository()
	{
		return enrollmentRepository;
	}

	public AssignmentRepository getAssignmentRepository()
	{
		return assignmentRepository;
	}

	public StudentMapper getStudentMapper()
	{
		return studentMapper;
	}
}
