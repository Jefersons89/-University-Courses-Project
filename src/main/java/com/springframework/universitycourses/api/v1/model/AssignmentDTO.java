package com.springframework.universitycourses.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;


@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO
{
	private Long id;

	@NotNull(message = "Description is mandatory")
	@NotBlank(message = "Title is mandatory")
	@Size(min = 5, max = 50)
	private String title;

	@NotNull(message = "Description is mandatory")
	@NotBlank(message = "Description is mandatory")
	@Size(min = 5, max = 255)
	private String description;

	@NotNull
	@Max(30)
	@Min(15)
	private Long points;

	@NotNull(message = "Description is mandatory")
	private Long courseId;

	@NotNull(message = "Description is mandatory")
	private Long teacherId;
	private Set<EnrollmentDTO> enrollments;
}
