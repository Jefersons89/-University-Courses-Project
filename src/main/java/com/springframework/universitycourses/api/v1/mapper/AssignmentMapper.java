package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.model.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Mapper
@Named("AssignmentMapper")
public interface AssignmentMapper
{
	AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

	@Named("assignmentToAssignmentDTO")
	@Mappings({
			@Mapping(target = "courseId", expression = "java(assignment.getCourse().getId())"),
			@Mapping(target = "teacherId", expression = "java(assignment.getTeacher().getId())") })
	AssignmentDTO assignmentToAssignmentDTO(Assignment assignment);

	Assignment assignmentDTOToAssignment(AssignmentDTO assignmentDTO);
}
