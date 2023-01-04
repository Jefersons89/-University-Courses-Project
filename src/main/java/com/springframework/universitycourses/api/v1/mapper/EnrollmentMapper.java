package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.EnrollmentDTO;
import com.springframework.universitycourses.model.Enrollment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Named("EnrollmentMapper")
@Mapper(uses = { StudentMapper.class, AssignmentMapper.class })
public interface EnrollmentMapper
{
	EnrollmentMapper INSTANCE = Mappers.getMapper(EnrollmentMapper.class);

	@Mappings({
			@Mapping(target = "student", qualifiedByName = { "StudentMapper", "toStudentDTOWithoutEnrollments" }),
			@Mapping(target = "assignment", qualifiedByName = { "AssignmentMapper", "toAssignmentDTOWithoutEnrollments" }) })
	EnrollmentDTO enrollmentToEnrollmentDTO(Enrollment enrollment);

	Enrollment enrollmentDTOToEnrollment(EnrollmentDTO enrollmentDTO);

	@Named("toEnrollmentDTOWithoutStudent")
	@Mappings({
			@Mapping(target = "student", ignore = true),
			@Mapping(target = "assignment", ignore = true) })
	EnrollmentDTO toEnrollmentDTOWithoutStudent(Enrollment enrollment);

	@Named("toEnrollmentDTOWithoutAssignment")
	@Mappings({
			@Mapping(target = "assignment", ignore = true) })
	EnrollmentDTO toEnrollmentDTOWithoutAssignment(Enrollment enrollment);
}
