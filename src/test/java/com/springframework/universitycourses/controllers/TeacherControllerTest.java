package com.springframework.universitycourses.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springframework.universitycourses.api.v1.model.TeacherDTO;
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

		returnedTeacherDTO = new TeacherDTO();
		returnedTeacherDTO.setId(ID);
		returnedTeacherDTO.setEmail(EMAIL);

		mockMvc = MockMvcBuilders
				.standaloneSetup(teacherController)
				.build();
	}

	@Test
	void getListOfTeachers() throws Exception
	{
		TeacherDTO teacherDTO1 = new TeacherDTO();
		teacherDTO1.setId(ID);
		teacherDTO1.setEmail(EMAIL);

		TeacherDTO teacherDTO2 = new TeacherDTO();
		teacherDTO2.setId(ID);
		teacherDTO2.setEmail(EMAIL);

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
	void createNewTeacher() throws Exception
	{
		TeacherDTO teacherDTOToBeCreated = new TeacherDTO();
		teacherDTOToBeCreated.setId(ID);
		teacherDTOToBeCreated.setEmail(EMAIL);

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
		TeacherDTO teacherDTOToBeUpdated = new TeacherDTO();
		teacherDTOToBeUpdated.setId(ID);
		teacherDTOToBeUpdated.setEmail(EMAIL);

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
	void deleteTeacher() throws Exception
	{
		mockMvc.perform(delete(TeacherController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(teacherService).deleteById(anyLong());
	}
}