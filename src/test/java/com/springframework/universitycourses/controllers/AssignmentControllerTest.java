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
	private static final String NO_NUMERIC_ID = "abc";
	private static final String DESCRIPTION = "TestDescription";
	private static final Long POINTS = 20L;
	private static final Long COURSE_ID = 2L;
	private static final Long TEACHER_ID = 3L;
	private static final Long ID = 1L;
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

		returnedAssignmentDTO = AssignmentDTO.builder().id(ID).title(TITLE).build();

		mockMvc = MockMvcBuilders
				.standaloneSetup(assignmentController)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build();
	}

	@Test
	void getListOfAssignments() throws Exception
	{
		AssignmentDTO assignmentDTO1 = AssignmentDTO.builder().id(1L).title("Test1").build();

		AssignmentDTO assignmentDTO2 = AssignmentDTO.builder().id(2L).title("Test2").build();

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
		AssignmentDTO assignmentDTOToCreate =
				AssignmentDTO.builder()
						.id(ID)
						.title(TITLE)
						.description(DESCRIPTION)
						.points(POINTS)
						.courseId(COURSE_ID)
						.teacherId(TEACHER_ID)
						.build();

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
		AssignmentDTO assignmentDTOToUpdated =
				AssignmentDTO.builder()
						.id(ID)
						.title(TITLE)
						.description(DESCRIPTION)
						.points(POINTS)
						.courseId(COURSE_ID)
						.teacherId(TEACHER_ID)
						.build();

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
		AssignmentDTO assignmentDTOToUpdated = AssignmentDTO.builder()
				.id(ID)
				.title(TITLE)
				.description(DESCRIPTION)
				.points(POINTS)
				.courseId(COURSE_ID)
				.teacherId(TEACHER_ID)
				.build();

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
		AssignmentDTO assignmentDTOToUpdated = AssignmentDTO.builder().id(ID).title(TITLE).build();

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