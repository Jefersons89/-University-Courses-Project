package com.springframework.universitycourses.services;

import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.api.v1.model.StudentDTO;
import com.springframework.universitycourses.api.v1.model.TeacherDTO;
import com.springframework.universitycourses.model.Assignment;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface AssignmentService extends CrudService<AssignmentDTO, Long>
{
	Assignment findByModelById(Long id);

	Assignment save(Assignment object);

	Set<StudentDTO> getAssignmentStudents(Long id);

	List<Map<String, Object>> getAssignmentStudentsFinished(Long id);

	List<Map<String, Object>> getAssignmentStudentsInProgress(Long id);

	TeacherDTO getAssignmentTeachers(Long id);
}
