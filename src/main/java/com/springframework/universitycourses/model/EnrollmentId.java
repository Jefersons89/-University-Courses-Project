package com.springframework.universitycourses.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class EnrollmentId implements Serializable
{
	private static final long serialVersionUID = 1L;

	@NotNull(message = "Id is mandatory")
	private Long studentId;

	@NotNull(message = "Id is mandatory")
	private Long assignmentId;

	@Builder
	public EnrollmentId(final Long studentId, final Long assignmentId)
	{
		super();
		this.studentId = studentId;
		this.assignmentId = assignmentId;
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
			return true;
		if (!(o instanceof EnrollmentId))
			return false;

		EnrollmentId that = (EnrollmentId) o;

		if (!getStudentId().equals(that.getStudentId()))
			return false;
		return getAssignmentId().equals(that.getAssignmentId());
	}

	@Override
	public int hashCode()
	{
		int result = getStudentId().hashCode();
		result = 31 * result + getAssignmentId().hashCode();
		return result;
	}
}
