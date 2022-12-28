package com.springframework.universitycourses.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "assignments")
public class Assignment extends BaseEntity
{
	@NotNull
	@Max(30)
	@Min(15)
	@Column
	private Long points;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private Course course;

	@Builder
	public Assignment(final Long id, final Long code, final String title, final String description, final Long points)
	{
		super(id, code, title, description);
		this.points = points;
	}
}
