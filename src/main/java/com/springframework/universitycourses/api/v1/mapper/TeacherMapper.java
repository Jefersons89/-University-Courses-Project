package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.TeacherDTO;
import com.springframework.universitycourses.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface TeacherMapper
{
	TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

	@Mappings({
			@Mapping(target = "password", ignore = true),
			@Mapping(target = "roles", ignore = true) })
	TeacherDTO teacherToTeacherDTO(Teacher teacher);

	@Mappings({
			@Mapping(target = "roles", ignore = true) })
	Teacher teacherDTOToTeacher(TeacherDTO teacherDTO);
}
