package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.AssignmentMapper;
import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.exceptions.NotFoundException;
import com.springframework.universitycourses.model.Assignment;
import com.springframework.universitycourses.model.Course;
import com.springframework.universitycourses.model.Enrollment;
import com.springframework.universitycourses.model.Student;
import com.springframework.universitycourses.model.Teacher;
import com.springframework.universitycourses.repositories.AssignmentRepository;
import com.springframework.universitycourses.repositories.CourseRepository;
import com.springframework.universitycourses.repositories.EnrollmentRepository;
import com.springframework.universitycourses.repositories.StudentRepository;
import com.springframework.universitycourses.repositories.TeacherRepository;
import com.springframework.universitycourses.services.AssignmentService;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	private final TeacherRepository teacherRepository;
	private final AssignmentMapper assignmentMapper;

	public AssignmentSDJpaService(final AssignmentRepository assignmentRepository, final EnrollmentRepository enrollmentRepository,
			final StudentRepository studentRepository, final CourseRepository courseRepository,
			final TeacherRepository teacherRepository,
			final AssignmentMapper assignmentMapper)
	{
		this.assignmentRepository = assignmentRepository;
		this.enrollmentRepository = enrollmentRepository;
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.teacherRepository = teacherRepository;
		this.assignmentMapper = assignmentMapper;
	}

	@Override
	public AssignmentDTO findById(final Long id)
	{
		Optional<Assignment> assignment = getAssignmentRepository().findById(id);

		if (assignment.isEmpty())
		{
			throw new NotFoundException("Assignment Not Found for id: " + id);
		}

		assignment.ifPresent(value -> setEnrollments(value, getEnrollmentRepository().findAll()));

		return assignment
				.map(getAssignmentMapper()::assignmentToAssignmentDTO)
				.orElse(null);
	}

	@Override
	public Assignment findByModelById(final Long id)
	{
		Optional<Assignment> assignment = getAssignmentRepository().findById(id);

		if (assignment.isEmpty())
		{
			throw new NotFoundException("Assignment Not Found");
		}

		assignment.ifPresent(value -> setEnrollments(value, getEnrollmentRepository().findAll()));

		return assignment.orElse(null);
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

	protected static void setEnrollments(final Assignment assignment, final List<Enrollment> enrollments)
	{
		if (Objects.nonNull(enrollments))
		{
			assignment.setEnrollments(new HashSet<>(enrollments.stream()
					.filter(enrollment -> Objects.equals(enrollment.getId().getAssignmentId(), assignment.getId()))
					.collect(Collectors.toSet())));
		}
		else
		{
			assignment.setEnrollments(Collections.emptySet());
		}
	}

	@Override
	public AssignmentDTO createNew(final AssignmentDTO object)
	{
		return this.save(object);
	}

	@Override
	public AssignmentDTO save(final AssignmentDTO object)
	{
		Assignment assignment = getAssignmentMapper().assignmentDTOToAssignment(object);
		Optional<Course> course = getCourseRepository().findById(object.getCourseId());

		if (course.isEmpty())
		{
			throw new NotFoundException("Course Not Found");
		}

		assignment.setCourse(course.get());
		Optional<Teacher> teacher = getTeacherRepository().findById(object.getTeacherId());

		if (teacher.isEmpty())
		{
			throw new NotFoundException("Teacher Not Found");
		}

		assignment.setTeacher(teacher.get());

		if (Objects.nonNull(assignment.getEnrollments()) && !assignment.getEnrollments().isEmpty())
		{
			assignment.getEnrollments().forEach(enrollment -> {
				enrollment.setAssignment(
						getAssignmentRepository().findById(enrollment.getId().getAssignmentId()).orElse(null));
				enrollment.setStudent(getStudentRepository().findById(enrollment.getId().getStudentId()).orElse(null));
				getEnrollmentRepository().save(enrollment);
			});

			setEnrollments(assignment, getEnrollmentRepository().findAll());
		}

		return getAssignmentMapper().assignmentToAssignmentDTO(getAssignmentRepository().saveAndFlush(assignment));
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
		Optional<Assignment> assignment = getAssignmentRepository().findById(id);

		if (assignment.isEmpty())
		{
			throw new NotFoundException("Assignment Not Found");
		}

		assignment.ifPresent(value -> {
			setEnrollments(value, getEnrollmentRepository().findAll());
			value.getEnrollments().forEach(enrollment -> {
				enrollment.setAssignment(null);

				Student student = enrollment.getStudent();
				Optional<Enrollment> enrollmentToRemove = student.getEnrollments().stream()
						.filter(enrollment1 -> enrollment.getId().equals(enrollment1.getId())).findFirst();
				enrollmentToRemove.ifPresent(enrollmentToRemove1 -> student.getEnrollments().remove(enrollmentToRemove1));

				getStudentRepository().save(student);

				enrollment.setStudent(null);
				getEnrollmentRepository().save(enrollment);
				getEnrollmentRepository().deleteById(enrollment.getId());
			});
			value.setTeacher(null);
			value.setEnrollments(null);
			this.save(value);
		});
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

	public TeacherRepository getTeacherRepository()
	{
		return teacherRepository;
	}

	public StudentRepository getStudentRepository()
	{
		return studentRepository;
	}

	public CourseRepository getCourseRepository()
	{
		return courseRepository;
	}

	public AssignmentMapper getAssignmentMapper()
	{
		return assignmentMapper;
	}
}
