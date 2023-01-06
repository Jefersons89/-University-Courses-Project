package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.TeacherDTO;
import com.springframework.universitycourses.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface TeacherMapper
{
	TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

	TeacherDTO teacherToTeacherDTO(Teacher teacher);

	Teacher teacherDTOToTeacher(TeacherDTO teacherDTO);
}
