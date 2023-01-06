package com.springframework.universitycourses.controllers;

import com.springframework.universitycourses.api.v1.model.StudentDTO;
import com.springframework.universitycourses.api.v1.model.StudentListDTO;
import com.springframework.universitycourses.services.StudentService;
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
@RequestMapping("/students")
public class StudentController
{
	private final StudentService studentService;

	public StudentController(final StudentService studentService)
	{
		this.studentService = studentService;
	}

	@InitBinder
	public void setAllowFields(WebDataBinder webDataBinder)
	{
		webDataBinder.setDisallowedFields("id");
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public StudentListDTO getListOfStudents()
	{
		return new StudentListDTO(getStudentService().findAll());
	}

	@GetMapping({ "/{studentId}" })
	@ResponseStatus(HttpStatus.OK)
	public StudentDTO getStudentById(@PathVariable Long studentId)
	{
		return getStudentService().findById(studentId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StudentDTO createNewStudent(@RequestBody StudentDTO studentDTO)
	{
		return getStudentService().createNew(studentDTO);
	}

	@PutMapping({ "/{studentId}" })
	@ResponseStatus(HttpStatus.OK)
	public StudentDTO updateStudent(@PathVariable Long studentId, @RequestBody StudentDTO studentDTO)
	{
		return getStudentService().update(studentId, studentDTO);
	}

	@DeleteMapping({ "/{studentId}" })
	@ResponseStatus(HttpStatus.OK)
	public void deleteStudent(@PathVariable Long studentId)
	{
		getStudentService().deleteById(studentId);
	}

	public StudentService getStudentService()
	{
		return studentService;
	}
}
