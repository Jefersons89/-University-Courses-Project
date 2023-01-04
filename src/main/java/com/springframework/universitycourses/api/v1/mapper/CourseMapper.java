package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.CourseDTO;
import com.springframework.universitycourses.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Named("CourseMapper")
@Mapper(uses = { AssignmentMapper.class })
public interface CourseMapper
{
	CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

	@Mappings({
			@Mapping(target = "assignments", qualifiedByName = { "AssignmentMapper", "toAssignmentDTOWithoutCourse" }) })
	CourseDTO courseToCourseDTO(Course course);

	Course courseDTOToCourse(CourseDTO courseDTO);

	@Named("toCourseDTOWithoutAssignments")
	@Mappings({
			@Mapping(target = "assignments", ignore = true) })
	CourseDTO toCourseDTOWithoutAssignments(Course course);
}
