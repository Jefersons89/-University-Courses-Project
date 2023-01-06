package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.StudentDTO;
import com.springframework.universitycourses.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface StudentMapper
{
	StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

	StudentDTO studentToStudentDTO(Student student);

	Student studentDTOToStudent(StudentDTO studentDTO);
}
