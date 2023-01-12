package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.CourseMapper;
import com.springframework.universitycourses.api.v1.model.CourseDTO;
import com.springframework.universitycourses.exceptions.NotFoundException;
import com.springframework.universitycourses.model.Assignment;
import com.springframework.universitycourses.model.Course;
import com.springframework.universitycourses.repositories.AssignmentRepository;
import com.springframework.universitycourses.repositories.CourseRepository;
import com.springframework.universitycourses.services.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class CourseSDJpaService implements CourseService
{
	private final CourseRepository courseRepository;
	private final AssignmentRepository assignmentRepository;
	private final AssignmentSDJpaService assignmentSDJpaService;
	private final CourseMapper courseMapper;

	public CourseSDJpaService(final CourseRepository courseRepository, final AssignmentRepository assignmentRepository,
			final AssignmentSDJpaService assignmentSDJpaService,
			final CourseMapper courseMapper)
	{
		this.courseRepository = courseRepository;
		this.assignmentRepository = assignmentRepository;
		this.assignmentSDJpaService = assignmentSDJpaService;
		this.courseMapper = courseMapper;
	}

	@Override
	public CourseDTO findById(final Long id)
	{
		Optional<Course> course = getCourseRepository().findById(id);

		if (course.isEmpty())
		{
			throw new NotFoundException("Course Not Found for id: " + id);
		}

		return getCourseRepository().findById(id)
				.map(getCourseMapper()::courseToCourseDTO)
				.orElse(null);
	}

	public Course findByModelById(final Long id)
	{
		Optional<Course> course = getCourseRepository().findById(id);

		if (course.isEmpty())
		{
			throw new NotFoundException("Course Not Found for id: " + id);
		}

		course.ifPresent(value -> setAssignments(value, getAssignmentRepository().findAll()));

		return course.orElse(null);
	}

	protected static void setAssignments(final Course course, final List<Assignment> assignments)
	{
		course.setAssignments(new HashSet<>(assignments.stream()
				.filter(assignment -> Objects.equals(assignment.getCourse().getId(), course.getId()))
				.collect(Collectors.toSet())));
	}

	@Override
	public Set<CourseDTO> findAll()
	{
		Pageable sortedByTitle =
				PageRequest.of(0, 3, Sort.by("title"));

		Page<Course> courses = getCourseRepository().findAll(sortedByTitle);

		return courses
				.stream()
				.map(getCourseMapper()::courseToCourseDTO)
				.collect(Collectors.toSet());
	}

	@Override
	public CourseDTO createNew(final CourseDTO object)
	{
		object.setAssignments(new HashSet<>());
		return this.save(object);
	}

	@Override
	public CourseDTO save(final CourseDTO object)
	{
		Course course = getCourseMapper().courseDTOToCourse(object);

		course.setAssignments(this.findByModelById(course.getId()).getAssignments());
		course.getAssignments().forEach(assignment -> {
			Assignment assignmentSaved = getAssignmentSDJpaService().findByModelById(assignment.getId());
			assignment.setCourse(assignmentSaved.getCourse());
			assignment.setTeacher(assignmentSaved.getTeacher());
		});

		return getCourseMapper().courseToCourseDTO(getCourseRepository().saveAndFlush(course));
	}

	@Override
	public CourseDTO update(final Long id, CourseDTO object)
	{
		Optional<Course> course = getCourseRepository().findById(id);

		if (course.isEmpty())
		{
			throw new NotFoundException("Course Not Found for id: " + id);
		}

		object.setId(id);
		return this.save(object);
	}

	@Override
	public void delete(final CourseDTO object)
	{
		getCourseRepository().delete(getCourseMapper().courseDTOToCourse(object));
	}

	@Override
	public void deleteById(final Long id)
	{
		Optional<Course> courseOptional = getCourseRepository().findById(id);

		if (courseOptional.isEmpty())
		{
			throw new NotFoundException("Course Not Found for id: " + id);
		}

		courseOptional.ifPresent(course -> {
			course.getAssignments().forEach(assignment -> getAssignmentSDJpaService().deleteById(assignment.getId()));
			getCourseRepository().save(course);
		});

		getCourseRepository().deleteById(id);
	}

	public CourseRepository getCourseRepository()
	{
		return courseRepository;
	}

	public AssignmentSDJpaService getAssignmentSDJpaService()
	{
		return assignmentSDJpaService;
	}

	public AssignmentRepository getAssignmentRepository()
	{
		return assignmentRepository;
	}

	public CourseMapper getCourseMapper()
	{
		return courseMapper;
	}
}
