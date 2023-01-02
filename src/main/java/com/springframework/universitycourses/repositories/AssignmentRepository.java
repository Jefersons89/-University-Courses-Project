package com.springframework.universitycourses.repositories;

import com.springframework.universitycourses.model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AssignmentRepository extends JpaRepository<Assignment, Long>
{
}
