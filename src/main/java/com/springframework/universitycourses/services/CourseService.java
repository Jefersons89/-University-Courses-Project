package com.springframework.universitycourses.services;

import com.springframework.universitycourses.api.v1.model.CourseDTO;
import com.springframework.universitycourses.model.Course;


public interface CourseService extends CrudService<CourseDTO, Long>
{
	Course findByModelById(Long id);
}
