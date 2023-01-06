package com.springframework.universitycourses.api.v1.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentDTO
{
	private Long id;
	private String title;
	private String description;
	private Long points;
	private Long courseId;
	private Long teacherId;
	private Set<EnrollmentDTO> enrollments;
}
