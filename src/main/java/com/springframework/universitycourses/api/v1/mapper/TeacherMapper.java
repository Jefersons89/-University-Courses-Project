package com.springframework.universitycourses.api.v1.mapper;

import com.springframework.universitycourses.api.v1.model.TeacherDTO;
import com.springframework.universitycourses.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;


@Named("TeacherMapper")
@Mapper(uses = { AssignmentMapper.class })
public interface TeacherMapper
{
	TeacherMapper INSTANCE = Mappers.getMapper(TeacherMapper.class);

	@Mappings({
			@Mapping(target = "assignments", qualifiedByName = { "AssignmentMapper", "toAssignmentDTOWithoutTeacher" }) })
	TeacherDTO teacherToTeacherDTO(Teacher teacher);

	Teacher teacherDTOToTeacher(TeacherDTO teacherDTO);

	@Named("toTeacherDTOWithoutAssignments")
	@Mappings({
			@Mapping(target = "assignments", expression = "java(null)") })
	TeacherDTO toTeacherDTOWithoutAssignments(Teacher teacher);
}
