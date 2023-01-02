package com.springframework.universitycourses.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
public class EnrollmentDTO
{
	private EnrollmentIdDTO id;
	private StudentDTO student;
	private AssignmentDTO assignment;
	private Date enrollmentDate;
	private Long grade;
	private Boolean inProgress;
}
