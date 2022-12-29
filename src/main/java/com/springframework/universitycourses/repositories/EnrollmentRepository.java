package com.springframework.universitycourses.repositories;

import com.springframework.universitycourses.model.Enrollment;
import com.springframework.universitycourses.model.EnrollmentId;
import org.springframework.data.repository.CrudRepository;


public interface EnrollmentRepository extends CrudRepository<Enrollment, EnrollmentId>
{
}
