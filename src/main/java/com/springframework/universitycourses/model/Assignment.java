package com.springframework.universitycourses.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Assignment extends BaseEntity
{
	@NotNull
	@Max(30)
	@Min(15)
	private Long points;

	@Builder
	public Assignment(final Long id, final Long code, final String title, final String description, final Long points)
	{
		super(id, code, title, description);
		this.points = points;
	}
}
