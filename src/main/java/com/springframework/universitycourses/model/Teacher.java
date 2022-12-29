package com.springframework.universitycourses.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher extends User
{
	@OneToMany(mappedBy = "teacher")
	private Set<Assignment> assignments = new HashSet<>();

	@Builder
	public Teacher(final Long id, final String firstName, final String lastName, final String email,
			final Set<Assignment> assignments)
	{
		super(id, firstName, lastName, email);
		this.assignments = assignments;
	}
}
