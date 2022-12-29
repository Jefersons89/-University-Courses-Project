package com.springframework.universitycourses.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public class LearningMaterial extends BaseEntity
{
	@NotBlank(message = "Name is mandatory")
	private String title;

	@NotBlank(message = "Description is mandatory")
	private String description;

	public LearningMaterial(final Long id, final String title, final String description)
	{
		super(id);
		this.title = title;
		this.description = description;
	}
}
