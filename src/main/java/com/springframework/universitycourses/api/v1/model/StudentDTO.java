package com.springframework.universitycourses.api.v1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

	@NotNull(message = "Enrollment Year is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date enrollmentYear;

	private Set<EnrollmentDTO> enrollments;
}
