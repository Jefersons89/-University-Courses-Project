package com.springframework.universitycourses.services;

import com.springframework.universitycourses.api.v1.model.EnrollmentDTO;
import com.springframework.universitycourses.api.v1.model.EnrollmentIdDTO;
import com.springframework.universitycourses.model.Enrollment;


public interface EnrollmentService extends CrudService<EnrollmentDTO, EnrollmentIdDTO>
{
	Enrollment save(Enrollment object);
}
