package com.springframework.universitycourses.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable
{
	@NotNull
	private Long id;

	@NotNull(message = "Name is mandatory")
	private Long code;

	@NotBlank(message = "Name is mandatory")
	private String title;

	@NotBlank(message = "Name is mandatory")
	private String description;
}
