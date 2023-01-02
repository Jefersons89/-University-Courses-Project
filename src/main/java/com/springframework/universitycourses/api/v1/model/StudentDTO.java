package com.springframework.universitycourses.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
public class StudentDTO
{
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Date enrollmentYear;
	private Set<EnrollmentDTO> enrollments;
}
