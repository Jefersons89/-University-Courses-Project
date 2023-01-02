package com.springframework.universitycourses.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
public class CourseDTO
{
	private Long id;
	private String title;
	private String description;
	private Long credit;
	private Set<AssignmentDTO> assignments;
}
