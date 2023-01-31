package com.springframework.universitycourses.controllers;

import com.springframework.universitycourses.api.v1.model.AssignmentListDTO;
import com.springframework.universitycourses.api.v1.model.TeacherDTO;
import com.springframework.universitycourses.api.v1.model.TeacherListDTO;
import com.springframework.universitycourses.services.TeacherService;
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
@RequestMapping(TeacherController.BASE_URL)
public class TeacherController
{
	public static final String BASE_URL = "/teachers";
	private final TeacherService teacherService;

	public TeacherController(final TeacherService teacherService)
	{
		this.teacherService = teacherService;
	}

	@InitBinder
	public void setAllowFields(WebDataBinder webDataBinder)
	{
		webDataBinder.setDisallowedFields("id");
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public TeacherListDTO getListOfTeachers()
	{
		return new TeacherListDTO(getTeacherService().findAll());
	}

	@GetMapping({ "/{teacherId}" })
	@ResponseStatus(HttpStatus.OK)
	public TeacherDTO getTeacherById(@PathVariable Long teacherId)
	{
		return getTeacherService().findById(teacherId);
	}

	@GetMapping({ "/{teacherId}/assignments" })
	@ResponseStatus(HttpStatus.OK)
	public AssignmentListDTO getTeacherAssignments(@PathVariable Long teacherId)
	{
		return new AssignmentListDTO(getTeacherService().getTeacherAssignments(teacherId));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public TeacherDTO createNewTeacher(@RequestBody @Valid TeacherDTO teacherDTO)
	{
		return getTeacherService().createNew(teacherDTO);
	}

	@PutMapping({ "/{teacherId}" })
	@ResponseStatus(HttpStatus.OK)
	public TeacherDTO updateTeacher(@PathVariable Long teacherId, @RequestBody @Valid TeacherDTO teacherDTO)
	{
		return getTeacherService().update(teacherId, teacherDTO);
	}

	@DeleteMapping({ "/{teacherId}" })
	@ResponseStatus(HttpStatus.OK)
	public void deleteTeacher(@PathVariable Long teacherId)
	{
		getTeacherService().deleteById(teacherId);
	}

	public TeacherService getTeacherService()
	{
		return teacherService;
	}
}
