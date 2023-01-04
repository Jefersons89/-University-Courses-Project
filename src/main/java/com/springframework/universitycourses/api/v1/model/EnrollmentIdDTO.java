package com.springframework.universitycourses.api.v1.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class EnrollmentIdDTO
{
	private Long studentId;
	private Long assignmentId;

	@Builder
	public EnrollmentIdDTO(final Long studentId, final Long assignmentId)
	{
		this.studentId = studentId;
		this.assignmentId = assignmentId;
	}
}
