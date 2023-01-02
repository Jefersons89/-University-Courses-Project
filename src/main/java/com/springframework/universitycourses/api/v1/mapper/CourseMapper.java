package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.CourseDTO;
import com.springframework.universitycourses.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CourseMapper
{
	CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

	CourseDTO courseToCourseDTO(Course course);
}
