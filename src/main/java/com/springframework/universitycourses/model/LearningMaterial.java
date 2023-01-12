package com.springframework.universitycourses.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class LearningMaterial extends BaseEntity
{
	@NotBlank(message = "Title is mandatory")
	@NotNull(message = "Title is mandatory")
	@Size(min = 5, max = 50)
	private String title;

	@NotBlank(message = "Description is mandatory")
	@NotNull(message = "Description is mandatory")
	@Size(min = 5, max = 255)
	private String description;

	public LearningMaterial(final Long id, final String title, final String description)
	{
		super(id);
		this.title = title;
		this.description = description;
	}
}
