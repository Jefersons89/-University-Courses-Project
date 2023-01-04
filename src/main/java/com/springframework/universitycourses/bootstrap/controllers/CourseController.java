package com.springframework.universitycourses.bootstrap.controllers;

import com.springframework.universitycourses.api.v1.model.CourseDTO;
import com.springframework.universitycourses.api.v1.model.CourseListDTO;
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


@RestController
@RequestMapping("/courses")
public class CourseController
{
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

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CourseDTO createNewCourse(@RequestBody CourseDTO courseDTO)
	{
		return getCourseService().createNew(courseDTO);
	}

	@PutMapping({ "/{courseId}" })
	@ResponseStatus(HttpStatus.OK)
	public CourseDTO updateCourse(@PathVariable Long courseId, @RequestBody CourseDTO courseDTO)
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
