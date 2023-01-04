package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.StudentMapper;
import com.springframework.universitycourses.api.v1.model.StudentDTO;
import com.springframework.universitycourses.model.Enrollment;
import com.springframework.universitycourses.model.Student;
import com.springframework.universitycourses.repositories.EnrollmentRepository;
import com.springframework.universitycourses.repositories.StudentRepository;
import com.springframework.universitycourses.services.StudentService;
import org.springframework.stereotype.Service;

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
	private final StudentMapper studentMapper;

	public StudentSDJpaService(final StudentRepository studentRepository, final EnrollmentRepository enrollmentRepository,
			final StudentMapper studentMapper)
	{
		this.studentRepository = studentRepository;
		this.enrollmentRepository = enrollmentRepository;
		this.studentMapper = studentMapper;
	}

	@Override
	public StudentDTO findById(final Long id)
	{
		Optional<Student> student = getStudentRepository().findById(id);
		student.ifPresent(value -> setEnrollments(getEnrollmentRepository().findAll(), value));

		return student
				.map(getStudentMapper()::studentToStudentDTO)
				.orElse(null);
	}

	@Override
	public Set<StudentDTO> findAll()
	{
		List<Student> students = getStudentRepository().findAll();
		students.forEach(student -> setEnrollments(getEnrollmentRepository().findAll(), student));

		return students.stream()
				.map(getStudentMapper()::studentToStudentDTO)
				.collect(Collectors.toSet());
	}

	private static void setEnrollments(final List<Enrollment> enrollments, final Student student)
	{
		student.setEnrollments(new HashSet<>(enrollments.stream()
				.filter(enrollment -> Objects.equals(enrollment.getId().getStudentId(), student.getId()))
				.collect(Collectors.toSet())));
	}

	@Override
	public StudentDTO createNew(final StudentDTO object)
	{
		return this.save(object);
	}

	@Override
	public StudentDTO save(final StudentDTO object)
	{
		return getStudentMapper().studentToStudentDTO(
				getStudentRepository().saveAndFlush(getStudentMapper().studentDTOToStudent(object)));
	}

	@Override
	public Student save(final Student object)
	{
		return getStudentRepository().save(object);
	}

	@Override
	public StudentDTO update(final Long id, StudentDTO object)
	{
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

	public StudentMapper getStudentMapper()
	{
		return studentMapper;
	}
}
