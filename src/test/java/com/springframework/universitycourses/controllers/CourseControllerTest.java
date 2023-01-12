package com.springframework.universitycourses.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springframework.universitycourses.api.v1.model.CourseDTO;
import com.springframework.universitycourses.exceptions.NotFoundException;
import com.springframework.universitycourses.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class CourseControllerTest
{
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
	private static final String TITLE = "TestCourse";
	private static final Long ID = 1L;
	private static final String NO_NUMERIC_ID = "abc";
	Set<CourseDTO> returnedCourseDTOSet;
	CourseDTO returnedCourseDTO;

	@Mock
	CourseService courseService;

	@InjectMocks
	CourseController courseController;

	MockMvc mockMvc;

	@BeforeEach
	void setUp()
	{
		returnedCourseDTOSet = new HashSet<>();

		returnedCourseDTO = new CourseDTO();
		returnedCourseDTO.setId(ID);
		returnedCourseDTO.setTitle(TITLE);

		mockMvc = MockMvcBuilders
				.standaloneSetup(courseController)
				.setControllerAdvice(ControllerExceptionHandler.class)
				.build();
	}

	@Test
	void getListOfCourses() throws Exception
	{
		CourseDTO courseDTO1 = new CourseDTO();
		courseDTO1.setId(1L);
		courseDTO1.setTitle("Test1");

		CourseDTO courseDTO2 = new CourseDTO();
		courseDTO2.setId(2L);
		courseDTO2.setTitle("Test2");

		returnedCourseDTOSet.add(courseDTO1);
		returnedCourseDTOSet.add(courseDTO2);

		when(courseService.findAll()).thenReturn(returnedCourseDTOSet);

		mockMvc.perform(get(CourseController.BASE_URL)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.courseDTOSet", hasSize(2)));

		verify(courseService).findAll();
	}

	@Test
	void getCourseById() throws Exception
	{
		when(courseService.findById(anyLong())).thenReturn(returnedCourseDTO);

		mockMvc.perform(get(CourseController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", equalTo(TITLE)));

		verify(courseService).findById(anyLong());
	}

	@Test
	void getCourseByIdNotFound() throws Exception
	{
		when(courseService.findById(anyLong())).thenThrow(NotFoundException.class);

		mockMvc.perform(get(CourseController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(courseService).findById(anyLong());
	}

	@Test
	void getCourseByIdNumberFormatException() throws Exception
	{
		mockMvc.perform(get(CourseController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createNewCourse() throws Exception
	{
		CourseDTO courseDTOToCreate = new CourseDTO();
		courseDTOToCreate.setId(ID);
		courseDTOToCreate.setTitle(TITLE);

		when(courseService.createNew(any())).thenReturn(returnedCourseDTO);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(courseDTOToCreate);

		mockMvc.perform(post(CourseController.BASE_URL)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title", equalTo(TITLE)));

		verify(courseService).createNew(any());
	}

	@Test
	void updateCourse() throws Exception
	{
		CourseDTO courseDTOToUpdate = new CourseDTO();
		courseDTOToUpdate.setId(ID);
		courseDTOToUpdate.setTitle(TITLE);

		when(courseService.update(anyLong(), any())).thenReturn(returnedCourseDTO);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(courseDTOToUpdate);

		mockMvc.perform(put(CourseController.BASE_URL + "/" + ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", equalTo(TITLE)));

		verify(courseService).update(anyLong(), any());
	}

	@Test
	void updateCourseNotFound() throws Exception
	{
		CourseDTO courseDTOToUpdate = new CourseDTO();
		courseDTOToUpdate.setId(ID);
		courseDTOToUpdate.setTitle(TITLE);

		when(courseService.update(anyLong(), any())).thenThrow(NotFoundException.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(courseDTOToUpdate);

		mockMvc.perform(put(CourseController.BASE_URL + "/" + ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isNotFound());

		verify(courseService).update(anyLong(), any());
	}

	@Test
	void updateCourseNumberFormatException() throws Exception
	{
		CourseDTO courseDTOToUpdate = new CourseDTO();
		courseDTOToUpdate.setId(ID);
		courseDTOToUpdate.setTitle(TITLE);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(courseDTOToUpdate);

		mockMvc.perform(put(CourseController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void deleteCourse() throws Exception
	{
		mockMvc.perform(delete(CourseController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(courseService).deleteById(anyLong());
	}

	@Test
	void deleteCourseNotFound() throws Exception
	{
		doThrow(NotFoundException.class).when(courseService).deleteById(anyLong());

		mockMvc.perform(delete(CourseController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(courseService).deleteById(anyLong());
	}

	@Test
	void deleteCourseNumberFormatException() throws Exception
	{
		mockMvc.perform(delete(CourseController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}