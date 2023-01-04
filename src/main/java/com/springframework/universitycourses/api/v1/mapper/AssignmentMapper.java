package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.model.Assignment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Named("AssignmentMapper")
@Mapper(uses = { CourseMapper.class, TeacherMapper.class, EnrollmentMapper.class })
public interface AssignmentMapper
{
	AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

	@Mappings({
			@Mapping(target = "course", qualifiedByName = { "CourseMapper", "toCourseDTOWithoutAssignments" }),
			@Mapping(target = "teacher", qualifiedByName = { "TeacherMapper", "toTeacherDTOWithoutAssignments" }),
			@Mapping(target = "enrollments", qualifiedByName = { "EnrollmentMapper", "toEnrollmentDTOWithoutAssignment" }) })
	AssignmentDTO assignmentToAssignmentDTO(Assignment assignment);

	Assignment assignmentDTOToAssignment(AssignmentDTO assignmentDTO);

	@Named("toAssignmentDTOWithoutCourse")
	@Mappings({
			@Mapping(target = "course", ignore = true) })
	AssignmentDTO toAssignmentDTOWithoutCourse(Assignment assignment);

	@Named("toAssignmentDTOWithoutTeacher")
	@Mappings({
			@Mapping(target = "teacher", ignore = true) })
	AssignmentDTO toAssignmentDTOWithoutTeacher(Assignment assignment);

	@Named("toAssignmentDTOWithoutEnrollments")
	@Mappings({
			@Mapping(target = "enrollments", ignore = true) })
	AssignmentDTO toAssignmentDTOWithoutEnrollments(Assignment assignment);
}
