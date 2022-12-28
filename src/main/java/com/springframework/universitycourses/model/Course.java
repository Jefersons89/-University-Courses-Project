package com.springframework.universitycourses.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course extends BaseEntity
{
	@NotNull
	@Max(150)
	@Min(100)
	@Column
	private Long credit;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
	private Set<Assignment> assignments = new HashSet<>();

	@Builder
	public Course(final Long id, final Long code, final String title, final String description, final Long credit,
			final Set<Assignment> assignments)
	{
		super(id, code, title, description);
		this.credit = credit;
		this.assignments = assignments;
	}
}
