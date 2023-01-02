package com.springframework.universitycourses.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class EnrollmentIdDTO
{
	private Long studentId;
	private Long assignmentId;
}
