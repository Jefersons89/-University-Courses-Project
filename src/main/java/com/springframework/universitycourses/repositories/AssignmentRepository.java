package com.springframework.universitycourses.repositories;

import com.springframework.universitycourses.model.Assignment;
import org.springframework.data.repository.CrudRepository;


public interface AssignmentRepository extends CrudRepository<Assignment, Long>
{
	Assignment findByTitle(String title);
}
