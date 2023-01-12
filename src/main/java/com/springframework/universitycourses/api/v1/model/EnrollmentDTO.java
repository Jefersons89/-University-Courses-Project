package com.springframework.universitycourses.api.v1.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDTO
{
	private EnrollmentIdDTO id;

	@NotNull(message = "Enrollment Date is mandatory")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date enrollmentDate;

	@Max(5)
	@Min(0)
	@NotNull(message = "Grade is mandatory")
	private Long grade;

	@NotNull(message = "Progress Stage is mandatory")
	private String inProgress;
}
