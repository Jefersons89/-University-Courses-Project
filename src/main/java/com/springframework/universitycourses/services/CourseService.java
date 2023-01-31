package com.springframework.universitycourses.services;

import com.springframework.universitycourses.api.v1.model.AssignmentListDTO;
import com.springframework.universitycourses.api.v1.model.CourseDTO;
import com.springframework.universitycourses.api.v1.model.StudentListDTO;
import com.springframework.universitycourses.model.Course;


public interface CourseService extends CrudService<CourseDTO, Long>
{
	Course findByModelById(Long id);

	AssignmentListDTO getCourseAssignments(Long courseId);

	StudentListDTO getCourseStudents(Long courseId);
}
