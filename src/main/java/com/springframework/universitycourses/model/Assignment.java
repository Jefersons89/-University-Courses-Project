package com.springframework.universitycourses.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


public class Assignment extends BaseEntity
{
	@NotNull
	@Max(30)
	@Min(15)
	private Long points;

	public Assignment(final Long id, final Long code, final String title, final String description, final Long points)
	{
		super(id, code, title, description);
		this.points = points;
	}

	public Long getPoints()
	{
		return points;
	}

	public void setPoints(final Long points)
	{
		this.points = points;
	}
}
