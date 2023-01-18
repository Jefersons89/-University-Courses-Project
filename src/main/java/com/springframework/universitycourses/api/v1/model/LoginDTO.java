package com.springframework.universitycourses.api.v1.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
public class LoginDTO
{
	@NotNull(message = "Email is mandatory")
	@NotBlank(message = "Email is mandatory")
	private String email;

	@NotNull(message = "Password is mandatory")
	@NotBlank(message = "Password is mandatory")
	private String password;
}
