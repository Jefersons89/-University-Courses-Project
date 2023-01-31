package com.springframework.universitycourses.api.v1.model;

import lombok.Builder;
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

	@NotBlank(message = "Password id mandatory")
	@NotNull(message = "Password id mandatory")
	private String password;

	private Set<String> roles;
	private Set<AssignmentDTO> assignments;

	@Builder
	public TeacherDTO(final Long id, final String firstName, final String lastName, final String email, final String password,
			final Set<String> roles,
			final Set<AssignmentDTO> assignments)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.assignments = assignments;
	}
}
