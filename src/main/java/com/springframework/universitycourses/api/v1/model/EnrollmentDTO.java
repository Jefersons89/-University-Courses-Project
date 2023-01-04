package com.springframework.universitycourses.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO
{
	private EnrollmentIdDTO id;
	private StudentDTO student;
	private AssignmentDTO assignment;
	private String enrollmentDate;
	private Long grade;
	private String inProgress;
}
