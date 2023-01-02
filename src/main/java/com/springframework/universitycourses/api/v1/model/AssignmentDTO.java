package com.springframework.universitycourses.api.v1.model;

import com.springframework.universitycourses.model.Course;
import com.springframework.universitycourses.model.Teacher;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
public class AssignmentDTO
{
	private Long id;
	private String title;
	private String description;
	private Long points;
	private Course course;
	private Teacher teacher;
	private Set<EnrollmentDTO> enrollments;
}
