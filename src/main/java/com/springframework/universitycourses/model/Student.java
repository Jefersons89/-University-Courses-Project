package com.springframework.universitycourses.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "students")
public class Student extends User
{
	@NotNull
	@Column(name = "enrollment_year")
	private Date enrollmentYear;

	@OneToMany(mappedBy = "student")
	private transient Set<Enrollment> enrollments = new HashSet<>();

	@Builder
	public Student(final Long id, final String firstName, final String lastName, final Date enrollmentYear,
			final Set<Enrollment> enrollments)
	{
		super(id, firstName, lastName);
		this.enrollmentYear = enrollmentYear;
		this.enrollments = enrollments;
	}
}
