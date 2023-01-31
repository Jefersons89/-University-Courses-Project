package com.springframework.universitycourses.controllers;

import com.springframework.universitycourses.api.v1.model.AssignmentListDTO;
import com.springframework.universitycourses.api.v1.model.CourseDTO;
import com.springframework.universitycourses.api.v1.model.CourseListDTO;
import com.springframework.universitycourses.api.v1.model.StudentListDTO;
import com.springframework.universitycourses.services.CourseService;
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
@RequestMapping(CourseController.BASE_URL)
public class CourseController
{
	public static final String BASE_URL = "/courses";
	private final CourseService courseService;

	public CourseController(final CourseService courseService)
	{
		this.courseService = courseService;
	}

	@InitBinder
	public void setAllowFields(WebDataBinder webDataBinder)
	{
		webDataBinder.setDisallowedFields("id");
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public CourseListDTO getListOfCourses()
	{
		return new CourseListDTO(getCourseService().findAll());
	}

	@GetMapping({ "/{courseId}" })
	@ResponseStatus(HttpStatus.OK)
	public CourseDTO getCourseById(@PathVariable Long courseId)
	{
		return getCourseService().findById(courseId);
	}

	@GetMapping({ "/{courseId}/assignments" })
	@ResponseStatus(HttpStatus.OK)
	public AssignmentListDTO getCourseAssignments(@PathVariable Long courseId)
	{
		return getCourseService().getCourseAssignments(courseId);
	}

	@GetMapping({ "/{courseId}/students" })
	@ResponseStatus(HttpStatus.OK)
	public StudentListDTO getCourseStudents(@PathVariable Long courseId)
	{
		return getCourseService().getCourseStudents(courseId);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CourseDTO createNewCourse(@RequestBody @Valid CourseDTO courseDTO)
	{
		return getCourseService().createNew(courseDTO);
	}

	@PutMapping({ "/{courseId}" })
	@ResponseStatus(HttpStatus.OK)
	public CourseDTO updateCourse(@PathVariable Long courseId, @RequestBody @Valid CourseDTO courseDTO)
	{
		return getCourseService().update(courseId, courseDTO);
	}

	@DeleteMapping({ "/{courseId}" })
	@ResponseStatus(HttpStatus.OK)
	public void deleteCourse(@PathVariable Long courseId)
	{
		getCourseService().deleteById(courseId);
	}

	public CourseService getCourseService()
	{
		return courseService;
	}
}
