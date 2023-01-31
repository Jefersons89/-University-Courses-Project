package com.springframework.universitycourses.api.v1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
public class StudentDTO
{
	private Long id;

	@NotBlank(message = "First Name is mandatory")
	@NotNull(message = "First Name is mandatory")
	private String firstName;

	@NotBlank(message = "Last Name is mandatory")
	@NotNull(message = "Last Name is mandatory")
	private String lastName;

	@Email(message = "Email is mandatory")
	@NotNull(message = "Last Name is mandatory")
	private String email;

	@NotBlank(message = "Password id mandatory")
	@NotNull(message = "Password id mandatory")
	private String password;

	@NotNull(message = "Enrollment Year is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date enrollmentYear;

	private Set<String> roles;
	private Set<EnrollmentDTO> enrollments;

	@Builder
	public StudentDTO(final Long id, final String firstName, final String lastName, final String email, final String password,
			final Date enrollmentYear,
			final Set<String> roles, final Set<EnrollmentDTO> enrollments)
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.enrollmentYear = enrollmentYear;
		this.roles = roles;
		this.enrollments = enrollments;
	}
}
