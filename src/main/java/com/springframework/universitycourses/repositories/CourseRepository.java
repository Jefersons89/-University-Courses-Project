package com.springframework.universitycourses.repositories;

import com.springframework.universitycourses.model.Course;
import org.springframework.data.repository.CrudRepository;


public interface CourseRepository extends CrudRepository<Course, Long>
{
	Course findByTitle(String title);
}
