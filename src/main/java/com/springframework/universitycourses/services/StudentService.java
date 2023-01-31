package com.springframework.universitycourses.services;

import com.springframework.universitycourses.api.v1.model.StudentDTO;
import com.springframework.universitycourses.model.Student;

import java.util.List;
import java.util.Map;


public interface StudentService extends CrudService<StudentDTO, Long>
{
	Student save(Student object);

	Student findByModelById(final Long id);

	List<Map<String, Object>>  getStudentAssignments(Long id);
}
