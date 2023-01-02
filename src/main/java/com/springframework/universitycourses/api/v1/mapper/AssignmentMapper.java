package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.model.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface AssignmentMapper
{
	AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

	AssignmentDTO assignmentToAssignmentDTO(Assignment assignment);
}
