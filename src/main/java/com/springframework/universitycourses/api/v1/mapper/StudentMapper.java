package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.StudentDTO;
import com.springframework.universitycourses.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Named("StudentMapper")
@Mapper(uses = { EnrollmentMapper.class })
public interface StudentMapper
{
	StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

	@Mappings({
			@Mapping(target = "enrollments", qualifiedByName = { "EnrollmentMapper", "toEnrollmentDTOWithoutStudent" }) })
	StudentDTO studentToStudentDTO(Student student);

	Student studentDTOToStudent(StudentDTO studentDTO);

	@Named("toStudentDTOWithoutEnrollments")
	@Mappings({
			@Mapping(target = "enrollments", ignore = true) })
	StudentDTO toStudentDTOWithoutEnrollments(Student student);
}
