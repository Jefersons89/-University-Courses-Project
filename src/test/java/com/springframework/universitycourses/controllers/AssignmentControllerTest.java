package com.springframework.universitycourses.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.exceptions.NotFoundException;
import com.springframework.universitycourses.services.AssignmentService;
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
class AssignmentControllerTest
{
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
	private static final String TITLE = "TestAssignment";
	private static final Long ID = 1L;
	private static final String NO_NUMERIC_ID = "abc";
	Set<AssignmentDTO> returnedAssignmentDTOSet;
	AssignmentDTO returnedAssignmentDTO;

	@Mock
	AssignmentService assignmentService;

	@InjectMocks
	AssignmentController assignmentController;

	MockMvc mockMvc;

	@BeforeEach
	void setUp()
	{
		returnedAssignmentDTOSet = new HashSet<>();

		returnedAssignmentDTO = new AssignmentDTO();
		returnedAssignmentDTO.setId(ID);
		returnedAssignmentDTO.setTitle(TITLE);

		mockMvc = MockMvcBuilders
				.standaloneSetup(assignmentController)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}

	@Test
	void getListOfAssignments() throws Exception
	{
		AssignmentDTO assignmentDTO1 = new AssignmentDTO();
		assignmentDTO1.setId(1L);
		assignmentDTO1.setTitle("Test1");

		AssignmentDTO assignmentDTO2 = new AssignmentDTO();
		assignmentDTO2.setId(2L);
		assignmentDTO2.setTitle("Test2");

		returnedAssignmentDTOSet.add(assignmentDTO1);
		returnedAssignmentDTOSet.add(assignmentDTO2);

		when(assignmentService.findAll()).thenReturn(returnedAssignmentDTOSet);

		mockMvc.perform(get(AssignmentController.BASE_URL)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.assignmentDTOSet", hasSize(2)));

		verify(assignmentService).findAll();
	}

	@Test
	void getAssignmentById() throws Exception
	{
		when(assignmentService.findById(anyLong())).thenReturn(returnedAssignmentDTO);

		mockMvc.perform(get(AssignmentController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", equalTo(TITLE)));

		verify(assignmentService).findById(anyLong());
	}


	@Test
	void getAssignmentByIdNotFound() throws Exception
	{
		when(assignmentService.findById(anyLong())).thenThrow(NotFoundException.class);

		mockMvc.perform(get(AssignmentController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(assignmentService).findById(anyLong());
	}

	@Test
	void getAssignmentByIdNumberFormatException() throws Exception
	{
		mockMvc.perform(get(AssignmentController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createNewAssignment() throws Exception
	{
		AssignmentDTO assignmentDTOToCreate = new AssignmentDTO();
		assignmentDTOToCreate.setId(ID);
		assignmentDTOToCreate.setTitle(TITLE);

		when(assignmentService.createNew(any())).thenReturn(returnedAssignmentDTO);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(assignmentDTOToCreate);

		mockMvc.perform(post(AssignmentController.BASE_URL)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title", equalTo(TITLE)));

		verify(assignmentService).createNew(any());
	}

	@Test
	void updateAssignment() throws Exception
	{
		AssignmentDTO assignmentDTOToUpdated = new AssignmentDTO();
		assignmentDTOToUpdated.setId(ID);
		assignmentDTOToUpdated.setTitle(TITLE);

		when(assignmentService.update(anyLong(), any())).thenReturn(returnedAssignmentDTO);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(assignmentDTOToUpdated);

		mockMvc.perform(put(AssignmentController.BASE_URL + "/" + ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", equalTo(TITLE)));

		verify(assignmentService).update(anyLong(), any());
	}

	@Test
	void updateAssignmentNotFound() throws Exception
	{
		AssignmentDTO assignmentDTOToUpdated = new AssignmentDTO();
		assignmentDTOToUpdated.setId(ID);
		assignmentDTOToUpdated.setTitle(TITLE);

		when(assignmentService.update(anyLong(), any())).thenThrow(NotFoundException.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(assignmentDTOToUpdated);

		mockMvc.perform(put(AssignmentController.BASE_URL + "/" + ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isNotFound());

		verify(assignmentService).update(anyLong(), any());
	}

	@Test
	void updateAssignmentNumberFormatException() throws Exception
	{
		AssignmentDTO assignmentDTOToUpdated = new AssignmentDTO();
		assignmentDTOToUpdated.setId(ID);
		assignmentDTOToUpdated.setTitle(TITLE);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(assignmentDTOToUpdated);

		mockMvc.perform(put(AssignmentController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}

	@Test
	void deleteAssignment() throws Exception
	{
		mockMvc.perform(delete(AssignmentController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(assignmentService).deleteById(anyLong());
	}

	@Test
	void deleteAssignmentNotFound() throws Exception
	{
		doThrow(NotFoundException.class).when(assignmentService).deleteById(anyLong());

		mockMvc.perform(delete(AssignmentController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(assignmentService).deleteById(anyLong());
	}

	@Test
	void deleteAssignmentNumberFormatException() throws Exception
	{
		mockMvc.perform(delete(AssignmentController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}