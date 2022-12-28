package com.springframework.universitycourses.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


public class Course extends BaseEntity
{
	@NotNull
	@Max(150)
	@Min(100)
	private Long credit;

	private Set<Assignment> assignments = new HashSet<>();

	public Course(final Long id, final Long code, final String title, final String description, final Long credit,
			final Set<Assignment> assignments)
	{
		super(id, code, title, description);
		this.credit = credit;
		this.assignments = assignments;
	}

	public Long getCredit()
	{
		return credit;
	}

	public void setCredit(final Long credit)
	{
		this.credit = credit;
	}

	public Set<Assignment> getAssignments()
	{
		return assignments;
	}

	public void setAssignments(final Set<Assignment> assignments)
	{
		this.assignments = assignments;
	}
}
