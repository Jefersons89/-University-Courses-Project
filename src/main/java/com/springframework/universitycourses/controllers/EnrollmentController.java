package com.springframework.universitycourses.controllers;

import com.springframework.universitycourses.api.v1.model.EnrollmentDTO;
import com.springframework.universitycourses.api.v1.model.EnrollmentIdDTO;
import com.springframework.universitycourses.api.v1.model.EnrollmentListDTO;
import com.springframework.universitycourses.services.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping(EnrollmentController.BASE_URL)
public class EnrollmentController
{
	public static final String BASE_URL = "/enrollments";
	private final EnrollmentService enrollmentService;

	public EnrollmentController(final EnrollmentService enrollmentService)
	{
		this.enrollmentService = enrollmentService;
	}

	@InitBinder
	public void setAllowFields(WebDataBinder webDataBinder)
	{
		webDataBinder.setDisallowedFields("id");
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public EnrollmentListDTO getListOfEnrollments()
	{
		return new EnrollmentListDTO(getEnrollmentService().findAll());
	}

	@GetMapping({ "/{studentId}/{assignmentId}" })
	@ResponseStatus(HttpStatus.OK)
	public EnrollmentDTO getEnrollmentById(@PathVariable Long studentId, @PathVariable Long assignmentId)
	{
		EnrollmentIdDTO enrollmentIdDTO = new EnrollmentIdDTO(studentId, assignmentId);
		return getEnrollmentService().findById(enrollmentIdDTO);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EnrollmentDTO createNewEnrollment(@RequestBody @Valid EnrollmentDTO enrollmentDTO)
	{
		return getEnrollmentService().createNew(enrollmentDTO);
	}

	@PutMapping({ "/{studentId}/{assignmentId}" })
	@ResponseStatus(HttpStatus.OK)
	public EnrollmentDTO updateEnrollment(@PathVariable Long studentId, @PathVariable @Valid Long assignmentId,
			@RequestBody EnrollmentDTO enrollmentDTO)
	{
		EnrollmentIdDTO enrollmentIdDTO = new EnrollmentIdDTO(studentId, assignmentId);
		return getEnrollmentService().update(enrollmentIdDTO, enrollmentDTO);
	}

	@DeleteMapping({ "/{studentId}/{assignmentId}" })
	@ResponseStatus(HttpStatus.OK)
	public void deleteEnrollment(@PathVariable Long studentId, @PathVariable Long assignmentId)
	{
		EnrollmentIdDTO enrollmentIdDTO = new EnrollmentIdDTO(studentId, assignmentId);
		getEnrollmentService().deleteById(enrollmentIdDTO);
	}

	public EnrollmentService getEnrollmentService()
	{
		return enrollmentService;
	}
}
