package com.springframework.universitycourses.services;

import com.springframework.universitycourses.model.Enrollment;
import com.springframework.universitycourses.model.EnrollmentId;

import java.util.Set;


public interface EnrollmentService
{
	EnrollmentId findById(EnrollmentId id);

	Set<Enrollment> findAll();

	Enrollment save(Enrollment object);

	void delete(Enrollment object);

	void deleteById(EnrollmentId id);
}
