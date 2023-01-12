package com.springframework.universitycourses.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDTO
{
	private Long id;

	@NotBlank(message = "First Name is mandatory")
	@NotNull(message = "First Name is mandatory")
	private String firstName;

	@NotBlank(message = "Last Name is mandatory")
	@NotNull(message = "Last Name is mandatory")
	private String lastName;

	@NotNull(message = "Email is mandatory")
	@Email(message = "Email is mandatory")
	private String email;

	private Set<AssignmentDTO> assignments;
}
