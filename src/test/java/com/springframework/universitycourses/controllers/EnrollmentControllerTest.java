package com.springframework.universitycourses.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springframework.universitycourses.api.v1.model.EnrollmentDTO;
import com.springframework.universitycourses.api.v1.model.EnrollmentIdDTO;
import com.springframework.universitycourses.services.EnrollmentService;
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

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class EnrollmentControllerTest
{
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
	private static final Long STUDENT_ID = 1L;
	private static final Long ASSIGNMENT_ID = 2L;

	Set<EnrollmentDTO> returnedEnrollmentSet;
	EnrollmentDTO returnedEnrollmentDTO;
	EnrollmentIdDTO enrollmentIdDTO;

	@Mock
	EnrollmentService enrollmentService;

	@InjectMocks
	EnrollmentController enrollmentController;

	MockMvc mockMvc;

	@BeforeEach
	void setUp()
	{
		returnedEnrollmentSet = new HashSet<>();

		enrollmentIdDTO = new EnrollmentIdDTO(STUDENT_ID, ASSIGNMENT_ID);

		returnedEnrollmentDTO = new EnrollmentDTO();
		returnedEnrollmentDTO.setId(enrollmentIdDTO);

		mockMvc = MockMvcBuilders
				.standaloneSetup(enrollmentController)
				.build();
	}

	@Test
	void getListOfEnrollments() throws Exception
	{
		EnrollmentIdDTO enrollmentIdDTO1 = new EnrollmentIdDTO(1L, 2L);
		EnrollmentDTO enrollmentDTO1 = new EnrollmentDTO();
		enrollmentDTO1.setId(enrollmentIdDTO1);

		EnrollmentIdDTO enrollmentIdDTO2 = new EnrollmentIdDTO(3L, 4L);
		EnrollmentDTO enrollmentDTO2 = new EnrollmentDTO();
		enrollmentDTO2.setId(enrollmentIdDTO2);

		returnedEnrollmentSet.add(enrollmentDTO1);
		returnedEnrollmentSet.add(enrollmentDTO2);

		when(enrollmentService.findAll()).thenReturn(returnedEnrollmentSet);

		mockMvc.perform(get(EnrollmentController.BASE_URL)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.enrollmentDTOSet", hasSize(2)));

		verify(enrollmentService).findAll();
	}

	@Test
	void getEnrollmentById() throws Exception
	{
		when(enrollmentService.findById(any())).thenReturn(returnedEnrollmentDTO);

		mockMvc.perform(get(EnrollmentController.BASE_URL + "/" + STUDENT_ID + "/" + ASSIGNMENT_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", hasKey("studentId")))
				.andExpect(jsonPath("$.id", hasKey("assignmentId")));

		verify(enrollmentService).findById(any());
	}

	@Test
	void createNewEnrollment() throws Exception
	{
		EnrollmentIdDTO enrollmentIdDTOToCreate = new EnrollmentIdDTO(STUDENT_ID, ASSIGNMENT_ID);
		EnrollmentDTO enrollmentDTOToCreate = new EnrollmentDTO();
		enrollmentDTOToCreate.setId(enrollmentIdDTOToCreate);

		when(enrollmentService.createNew(any())).thenReturn(returnedEnrollmentDTO);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(enrollmentDTOToCreate);

		mockMvc.perform(post(EnrollmentController.BASE_URL)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", hasKey("studentId")))
				.andExpect(jsonPath("$.id", hasKey("assignmentId")));

		verify(enrollmentService).createNew(any());
	}

	@Test
	void updateEnrollment() throws Exception
	{
		EnrollmentIdDTO enrollmentIdDTOToUpdate = new EnrollmentIdDTO(STUDENT_ID, ASSIGNMENT_ID);
		EnrollmentDTO enrollmentDTOToUpdate = new EnrollmentDTO();
		enrollmentDTOToUpdate.setId(enrollmentIdDTOToUpdate);

		when(enrollmentService.update(any(), any())).thenReturn(returnedEnrollmentDTO);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(enrollmentDTOToUpdate);

		mockMvc.perform(put(EnrollmentController.BASE_URL + "/" + STUDENT_ID + "/" + ASSIGNMENT_ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", hasKey("studentId")))
				.andExpect(jsonPath("$.id", hasKey("assignmentId")));

		verify(enrollmentService).update(any(), any());
	}

	@Test
	void deleteEnrollment() throws Exception
	{
		mockMvc.perform(delete(EnrollmentController.BASE_URL + "/" + STUDENT_ID + "/" + ASSIGNMENT_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(enrollmentService).deleteById(any());
	}
}