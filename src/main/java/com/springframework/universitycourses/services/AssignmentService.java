package com.springframework.universitycourses.services;

import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.model.Assignment;


public interface AssignmentService extends CrudService<AssignmentDTO, Long>
{
	Assignment findByModelById(Long id);

	Assignment save(Assignment object);
}
