package com.springframework.universitycourses.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springframework.universitycourses.api.v1.model.TeacherDTO;
import com.springframework.universitycourses.exceptions.NotFoundException;
import com.springframework.universitycourses.services.TeacherService;
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
class TeacherControllerTest
{
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
	private static final String EMAIL = "test@dummy.com";
	private static final String NO_NUMERIC_ID = "abc";
	private static final String FIRST_NAME = "TestName";
	private static final String LAST_NAME = "TestLastName";
	private static final String PASSWORD = "123";
	private static final Long ID = 1L;
	Set<TeacherDTO> returnedTeacherDTOSet;
	TeacherDTO returnedTeacherDTO;

	@Mock
	TeacherService teacherService;

	@InjectMocks
	TeacherController teacherController;

	MockMvc mockMvc;

	@BeforeEach
	void setUp()
	{
		returnedTeacherDTOSet = new HashSet<>();

		returnedTeacherDTO = TeacherDTO.builder().id(ID).email(EMAIL).build();

		mockMvc = MockMvcBuilders
				.standaloneSetup(teacherController)
				.setControllerAdvice(ControllerExceptionHandler.class)
				.build();
	}

	@Test
	void getListOfTeachers() throws Exception
	{
		TeacherDTO teacherDTO1 = TeacherDTO.builder().id(1L).email("test1@dummy.com").build();

		TeacherDTO teacherDTO2 = TeacherDTO.builder().id(2L).email("test2@dummy.com").build();

		returnedTeacherDTOSet.add(teacherDTO1);
		returnedTeacherDTOSet.add(teacherDTO2);

		when(teacherService.findAll()).thenReturn(returnedTeacherDTOSet);

		mockMvc.perform(get(TeacherController.BASE_URL)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.teacherDTOSet", hasSize(2)));

		verify(teacherService).findAll();
	}

	@Test
	void getTeacherById() throws Exception
	{
		when(teacherService.findById(anyLong())).thenReturn(returnedTeacherDTO);

		mockMvc.perform(get(TeacherController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", equalTo(EMAIL)));

		verify(teacherService).findById(anyLong());
	}

	@Test
	void getTeacherByIdNotfound() throws Exception
	{
		when(teacherService.findById(anyLong())).thenThrow(NotFoundException.class);

		mockMvc.perform(get(TeacherController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(teacherService).findById(anyLong());
	}

	@Test
	void getTeacherByIdNumberFormatException() throws Exception
	{
		mockMvc.perform(get(TeacherController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createNewTeacher() throws Exception
	{
		TeacherDTO teacherDTOToBeCreated =
				TeacherDTO.builder()
						.id(ID)
						.firstName(FIRST_NAME)
						.lastName(LAST_NAME)
						.email(EMAIL)
						.password(PASSWORD)
						.build();

		when(teacherService.createNew(any())).thenReturn(returnedTeacherDTO);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(teacherDTOToBeCreated);

		mockMvc.perform(post(TeacherController.BASE_URL)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.email", equalTo(EMAIL)));

		verify(teacherService).createNew(any());
	}

	@Test
	void updateTeacher() throws Exception
	{
		TeacherDTO teacherDTOToBeUpdated =
				TeacherDTO.builder()
						.id(ID)
						.firstName(FIRST_NAME)
						.lastName(LAST_NAME)
						.email(EMAIL)
						.password(PASSWORD)
						.build();

		when(teacherService.update(anyLong(), any())).thenReturn(returnedTeacherDTO);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(teacherDTOToBeUpdated);

		mockMvc.perform(put(TeacherController.BASE_URL + "/" + ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", equalTo(EMAIL)));

		verify(teacherService).update(anyLong(), any());
	}

	@Test
	void updateTeacherNotFound() throws Exception
	{
		TeacherDTO teacherDTOToBeUpdated =
				TeacherDTO.builder()
						.id(ID)
						.firstName(FIRST_NAME)
						.lastName(LAST_NAME)
						.email(EMAIL)
						.password(PASSWORD)
						.build();

		when(teacherService.update(anyLong(), any())).thenThrow(NotFoundException.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(teacherDTOToBeUpdated);

		mockMvc.perform(put(TeacherController.BASE_URL + "/" + ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isNotFound());

		verify(teacherService).update(anyLong(), any());
	}

	@Test
	void updateTeacherNumberFormatException() throws Exception
	{
		TeacherDTO teacherDTOToBeUpdated = TeacherDTO.builder().id(ID).email(EMAIL).build();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(teacherDTOToBeUpdated);

		mockMvc.perform(put(TeacherController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void deleteTeacher() throws Exception
	{
		mockMvc.perform(delete(TeacherController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(teacherService).deleteById(anyLong());
	}

	@Test
	void deleteTeacherNotFound() throws Exception
	{
		doThrow(NotFoundException.class).when(teacherService).deleteById(anyLong());

		mockMvc.perform(delete(TeacherController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(teacherService).deleteById(anyLong());
	}

	@Test
	void deleteTeacherNumberFormatException() throws Exception
	{
		mockMvc.perform(delete(TeacherController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}