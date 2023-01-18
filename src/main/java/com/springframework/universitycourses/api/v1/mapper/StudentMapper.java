package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.StudentDTO;
import com.springframework.universitycourses.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper
public interface StudentMapper
{
	StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

	@Mappings({
			@Mapping(target = "password", ignore = true),
			@Mapping(target = "roles", ignore = true) })
	StudentDTO studentToStudentDTO(Student student);

	@Mappings({
			@Mapping(target = "roles", ignore = true) })
	Student studentDTOToStudent(StudentDTO studentDTO);
}
