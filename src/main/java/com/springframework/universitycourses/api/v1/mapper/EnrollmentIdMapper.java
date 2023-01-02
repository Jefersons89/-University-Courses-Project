package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.EnrollmentIdDTO;
import com.springframework.universitycourses.model.EnrollmentId;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface EnrollmentIdMapper
{
	EnrollmentIdMapper INSTANCE = Mappers.getMapper(EnrollmentIdMapper.class);

	EnrollmentIdDTO enrollmentIdToEnrollmentIdDTO(EnrollmentId enrollmentId);
}
