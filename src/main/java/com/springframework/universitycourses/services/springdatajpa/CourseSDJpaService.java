package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.model.Course;
import com.springframework.universitycourses.repositories.CourseRepository;
import com.springframework.universitycourses.services.CourseService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class CourseSDJpaService implements CourseService
{
	private final CourseRepository courseRepository;

	public CourseSDJpaService(final CourseRepository courseRepository)
	{
		this.courseRepository = courseRepository;
	}

	@Override
	public Course findById(final Long id)
	{
		return getCourseRepository().findById(id).orElse(null);
	}

	@Override
	public Set<Course> findAll()
	{
		return new HashSet<>(getCourseRepository().findAll());
	}

	@Override
	public Course save(final Course object)
	{
		return getCourseRepository().saveAndFlush(object);
	}

	@Override
	public void delete(final Course object)
	{
		getCourseRepository().delete(object);
	}

	@Override
	public void deleteById(final Long id)
	{
		getCourseRepository().deleteById(id);
	}

	public CourseRepository getCourseRepository()
	{
		return courseRepository;
	}
}
