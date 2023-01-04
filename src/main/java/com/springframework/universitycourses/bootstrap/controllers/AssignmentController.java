package com.springframework.universitycourses.bootstrap.controllers;

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
		//		CourseDTO courseDTO = new CourseDTO();
		//		courseDTO.setId(1L);
		//		courseDTO.setTitle("test");
		//		TeacherDTO teacherDTO = new TeacherDTO();
		//		teacherDTO.setId(2L);
		//		teacherDTO.setFirstName("testTeacher");
		//		EnrollmentDTO enrollmentDTO = new EnrollmentDTO();
		//		EnrollmentIdDTO enrollmentIdDTO = new EnrollmentIdDTO(6L, 8L);
		//		enrollmentDTO.setId(enrollmentIdDTO);
		//		Set<EnrollmentDTO> enrollmentDTOSet = new HashSet<>();
		//		AssignmentDTO assignmentDTO = new AssignmentDTO(1L, "title", "description", 13L, courseDTO, teacherDTO, enrollmentDTOSet);
		//		Set<AssignmentDTO> assignmentDTOSet = new HashSet<>();
		//		assignmentDTOSet.add(assignmentDTO);
		//		return new AssignmentListDTO(assignmentDTOSet);
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
