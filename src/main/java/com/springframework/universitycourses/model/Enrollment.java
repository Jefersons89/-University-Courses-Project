package com.springframework.universitycourses.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "enrollments")
public class Enrollment
{
	@EmbeddedId
	private EnrollmentId id = new EnrollmentId();

	@ManyToOne
	@MapsId("studentId")
	private Student student;

	@ManyToOne
	@MapsId("assignmentId")
	private Assignment assignment;

	@NotNull
	@Column(name = "enrollment_date")
	private Date enrollmentDate;

	@NotNull
	@Column(name = "grade")
	private Long grade;

	@Column(name = "in_progress")
	private Boolean inProgress;

	@Builder
	public Enrollment(final EnrollmentId id, final Student student, final Assignment assignment, final Date enrollmentDate,
			final Long grade, final Boolean inProgress)
	{
		this.id = id;
		this.student = student;
		this.assignment = assignment;
		this.enrollmentDate = enrollmentDate;
		this.grade = grade;
		this.inProgress = inProgress;
	}
}
