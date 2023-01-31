package com.springframework.universitycourses.controllers;

import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.api.v1.model.AssignmentListDTO;
import com.springframework.universitycourses.api.v1.model.StudentListDTO;
import com.springframework.universitycourses.api.v1.model.TeacherDTO;
import com.springframework.universitycourses.services.AssignmentService;
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

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;


@RolesAllowed({ "USER", "ADMIN" })
@RestController
@RequestMapping(AssignmentController.BASE_URL)
public class AssignmentController
{
	public static final String BASE_URL = "/assignments";
	private final AssignmentService assignmentService;

	public AssignmentController(final AssignmentService assignmentService)
	{
		this.assignmentService = assignmentService;
	}

	@InitBinder
	public void setAllowFields(WebDataBinder webDataBinder)
	{
		webDataBinder.setDisallowedFields("id");
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public AssignmentListDTO getListOfAssignments()
	{
		return new AssignmentListDTO(getAssignmentService().findAll());
	}

	@GetMapping({ "/{assignmentId}" })
	@ResponseStatus(HttpStatus.OK)
	public AssignmentDTO getAssignmentById(@PathVariable Long assignmentId)
	{
		return getAssignmentService().findById(assignmentId);
	}

	@GetMapping({ "/{assignmentId}/students" })
	@ResponseStatus(HttpStatus.OK)
	public StudentListDTO getAssignmentStudents(@PathVariable Long assignmentId)
	{
		return new StudentListDTO(getAssignmentService().getAssignmentStudents(assignmentId));
	}

	@GetMapping({ "/{assignmentId}/students/finished" })
	@ResponseStatus(HttpStatus.OK)
	public Object getAssignmentStudentsFinished(@PathVariable Long assignmentId)
	{
		return getAssignmentService().getAssignmentStudentsFinished(assignmentId);
	}

	@GetMapping({ "/{assignmentId}/students/inProgress" })
	@ResponseStatus(HttpStatus.OK)
	public Object getAssignmentStudentsInProgress(@PathVariable Long assignmentId)
	{
		return getAssignmentService().getAssignmentStudentsInProgress(assignmentId);
	}

	@GetMapping({ "/{assignmentId}/teacher" })
	@ResponseStatus(HttpStatus.OK)
	public TeacherDTO getAssignmentTeachers(@PathVariable Long assignmentId)
	{
		return getAssignmentService().getAssignmentTeachers(assignmentId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AssignmentDTO createNewAssignment(@RequestBody @Valid AssignmentDTO assignmentDTO)
	{
		return getAssignmentService().createNew(assignmentDTO);
	}

	@PutMapping({ "/{assignmentId}" })
	@ResponseStatus(HttpStatus.OK)
	public AssignmentDTO updateAssignment(@PathVariable Long assignmentId, @RequestBody @Valid AssignmentDTO assignmentDTO)
	{
		return getAssignmentService().update(assignmentId, assignmentDTO);
	}

	@DeleteMapping({ "/{assignmentId}" })
	@ResponseStatus(HttpStatus.OK)
	public void deleteAssignment(@PathVariable Long assignmentId)
	{
		getAssignmentService().deleteById(assignmentId);
	}

	public AssignmentService getAssignmentService()
	{
		return assignmentService;
	}
}
