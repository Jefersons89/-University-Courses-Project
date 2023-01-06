package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.EnrollmentDTO;
import com.springframework.universitycourses.model.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface EnrollmentMapper
{
	EnrollmentMapper INSTANCE = Mappers.getMapper(EnrollmentMapper.class);

	EnrollmentDTO enrollmentToEnrollmentDTO(Enrollment enrollment);

	Enrollment enrollmentDTOToEnrollment(EnrollmentDTO enrollmentDTO);
}
