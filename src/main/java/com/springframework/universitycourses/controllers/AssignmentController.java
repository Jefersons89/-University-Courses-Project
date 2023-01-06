package com.springframework.universitycourses.controllers;

import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.api.v1.model.AssignmentListDTO;
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


@RestController
@RequestMapping("/assignments")
public class AssignmentController
{
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

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AssignmentDTO createNewAssignment(@RequestBody AssignmentDTO assignmentDTO)
	{
		return getAssignmentService().createNew(assignmentDTO);
	}

	@PutMapping({ "/{assignmentId}" })
	@ResponseStatus(HttpStatus.OK)
	public AssignmentDTO updateAssignment(@PathVariable Long assignmentId, @RequestBody AssignmentDTO assignmentDTO)
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
