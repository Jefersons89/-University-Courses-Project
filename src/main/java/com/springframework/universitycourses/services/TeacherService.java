package com.springframework.universitycourses.services;

import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.api.v1.model.TeacherDTO;
import com.springframework.universitycourses.model.Teacher;

import java.util.Set;


public interface TeacherService extends CrudService<TeacherDTO, Long>
{
	Teacher findByModelById(final Long id);

	Set<AssignmentDTO> getTeacherAssignments(Long id);
}
