package com.springframework.universitycourses.api.v1.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
public class TeacherDTO
{
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private Set<AssignmentDTO> assignments;
}
