package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.CourseDTO;
import com.springframework.universitycourses.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper(uses = { AssignmentMapper.class })
public interface CourseMapper
{
	CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

	@Mappings({
			@Mapping(target = "assignments", qualifiedByName = { "AssignmentMapper", "assignmentToAssignmentDTO" }) })
	CourseDTO courseToCourseDTO(Course course);

	Course courseDTOToCourse(CourseDTO courseDTO);
}
