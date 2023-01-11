package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.CourseMapper;
import com.springframework.universitycourses.api.v1.model.CourseDTO;
import com.springframework.universitycourses.model.Course;
import com.springframework.universitycourses.repositories.AssignmentRepository;
import com.springframework.universitycourses.repositories.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CourseSDJpaServiceTest
{
	private static final String TITLE = "TestAssignment";
	private static final Long ID = 1L;
	CourseDTO returnedCourseDTO;
	Course returnedCourse;

	@Mock
	CourseRepository courseRepository;

	@Mock
	AssignmentRepository assignmentRepository;

	@Mock
	CourseMapper courseMapper;

	@InjectMocks
	CourseSDJpaService courseSDJpaService;

	@BeforeEach
	void setUp()
	{
		returnedCourseDTO = new CourseDTO();
		returnedCourseDTO.setId(ID);
		returnedCourseDTO.setTitle(TITLE);

		returnedCourse = Course.builder().id(ID).title(TITLE).assignments(Collections.emptySet()).build();
	}

	@Test
	void findById()
	{
		when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedCourse));
		when(courseMapper.courseDTOToCourse(any())).thenReturn(returnedCourse);

		Course course = courseMapper.courseDTOToCourse(courseSDJpaService.findById(ID));

		assertEquals(ID, course.getId());

		verify(courseRepository).findById(anyLong());
		verify(courseMapper).courseDTOToCourse(any());
	}

	@Test
	void findByModelById()
	{
		when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedCourse));
		when(assignmentRepository.findAll()).thenReturn(Collections.emptyList());

		Course course = courseSDJpaService.findByModelById(ID);

		assertEquals(ID, course.getId());

		verify(courseRepository).findById(anyLong());
		verify(assignmentRepository).findAll();
	}

	@Test
	void findAll()
	{
		List<Course> courseList = new ArrayList<>();
		courseList.add(returnedCourse);

		when(courseRepository.findAll()).thenReturn(courseList);
		when(courseMapper.courseToCourseDTO(any())).thenReturn(returnedCourseDTO);

		Set<CourseDTO> courseDTOSet = courseSDJpaService.findAll();

		assertEquals(1, courseDTOSet.size());

		verify(courseRepository).findAll();
		verify(courseMapper).courseToCourseDTO(any());
	}

	@Test
	void createNew()
	{
		CourseDTO courseDTOToSave = new CourseDTO();
		courseDTOToSave.setId(ID);
		courseDTOToSave.setTitle(TITLE);

		when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedCourse));
		when(courseRepository.saveAndFlush(any())).thenReturn(returnedCourse);
		when(courseMapper.courseDTOToCourse(any())).thenReturn(returnedCourse);
		when(courseMapper.courseToCourseDTO(any())).thenReturn(returnedCourseDTO);

		Course course = courseMapper.courseDTOToCourse(courseSDJpaService.createNew(courseDTOToSave));

		assertNotNull(course);
		assertEquals(returnedCourse.getId(), course.getId());

		verify(courseRepository).findById(anyLong());
		verify(courseRepository).saveAndFlush(any());
		verify(courseMapper, times(2)).courseDTOToCourse(any());
		verify(courseMapper).courseToCourseDTO(any());
	}

	@Test
	void save()
	{
		CourseDTO courseDTOToSave = new CourseDTO();
		courseDTOToSave.setId(ID);
		courseDTOToSave.setTitle(TITLE);

		when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedCourse));
		when(courseRepository.saveAndFlush(any())).thenReturn(returnedCourse);
		when(courseMapper.courseDTOToCourse(any())).thenReturn(returnedCourse);
		when(courseMapper.courseToCourseDTO(any())).thenReturn(returnedCourseDTO);

		Course course = courseMapper.courseDTOToCourse(courseSDJpaService.save(courseDTOToSave));

		assertNotNull(course);
		assertEquals(returnedCourse.getId(), course.getId());

		verify(courseRepository).findById(anyLong());
		verify(courseRepository).saveAndFlush(any());
		verify(courseMapper, times(2)).courseDTOToCourse(any());
		verify(courseMapper).courseToCourseDTO(any());
	}

	@Test
	void update()
	{
		CourseDTO courseDTOToSave = new CourseDTO();
		courseDTOToSave.setId(ID);
		courseDTOToSave.setTitle(TITLE);

		when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedCourse));
		when(courseRepository.saveAndFlush(any())).thenReturn(returnedCourse);
		when(courseMapper.courseDTOToCourse(any())).thenReturn(returnedCourse);
		when(courseMapper.courseToCourseDTO(any())).thenReturn(returnedCourseDTO);

		Course course = courseMapper.courseDTOToCourse(courseSDJpaService.update(ID, courseDTOToSave));

		assertNotNull(course);
		assertEquals(returnedCourse.getId(), course.getId());

		verify(courseRepository).findById(anyLong());
		verify(courseRepository).saveAndFlush(any());
		verify(courseMapper, times(2)).courseDTOToCourse(any());
		verify(courseMapper).courseToCourseDTO(any());
	}

	@Test
	void deleteById()
	{
		when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedCourse));

		courseSDJpaService.deleteById(ID);

		verify(courseRepository).findById(anyLong());
		verify(courseRepository).deleteById(anyLong());
	}
}