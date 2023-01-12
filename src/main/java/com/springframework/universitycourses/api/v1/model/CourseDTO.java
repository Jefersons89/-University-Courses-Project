package com.springframework.universitycourses.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO
{
	private Long id;

	@NotBlank(message = "Title is mandatory")
	@NotNull(message = "Title is mandatory")
	@Size(min = 5, max = 50)
	private String title;

	@NotBlank(message = "Description is mandatory")
	@NotNull(message = "Description is mandatory")
	@Size(min = 5, max = 255)
	private String description;

	@Max(150)
	@Min(100)
	@NotNull(message = "Credit is mandatory")
	private Long credit;

	private Set<AssignmentDTO> assignments;
}
