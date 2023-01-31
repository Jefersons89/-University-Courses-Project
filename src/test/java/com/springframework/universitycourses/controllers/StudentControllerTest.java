package com.springframework.universitycourses.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.springframework.universitycourses.api.v1.model.StudentDTO;
import com.springframework.universitycourses.exceptions.NotFoundException;
import com.springframework.universitycourses.services.StudentService;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
class StudentControllerTest
{
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
	private static final String EMAIL = "test@dummy.com";
	private static final String NO_NUMERIC_ID = "abc";
	private static final String FIRST_NAME = "TestName";
	private static final String LAST_NAME = "TestLastName";
	private static final String PASSWORD = "123";
	private static final Date ENROLLMENT_YEAR = new GregorianCalendar(2023, Calendar.SEPTEMBER, 8).getTime();
	private static final Long ID = 1L;
	Set<StudentDTO> returnedStudentDTOSet;
	StudentDTO returnedStudentDTO;

	@Mock
	StudentService studentService;

	@InjectMocks
	StudentController studentController;

	MockMvc mockMvc;

	@BeforeEach
	void setUp()
	{
		returnedStudentDTOSet = new HashSet<>();

		returnedStudentDTO = StudentDTO.builder().id(ID).email(EMAIL).build();

		mockMvc = MockMvcBuilders
				.standaloneSetup(studentController)
				.setControllerAdvice(ControllerExceptionHandler.class)
				.build();
	}

	@Test
	void getListOfStudents() throws Exception
	{
		StudentDTO studentDTO1 = StudentDTO.builder().id(1L).email("test1@dummy.com").build();

		StudentDTO studentDTO2 = StudentDTO.builder().id(2L).email("test2@dummy.com").build();

		returnedStudentDTOSet.add(studentDTO1);
		returnedStudentDTOSet.add(studentDTO2);

		when(studentService.findAll()).thenReturn(returnedStudentDTOSet);

		mockMvc.perform(get(StudentController.BASE_URL)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.studentDTOSet", hasSize(2)));

		verify(studentService).findAll();
	}

	@Test
	void getStudentById() throws Exception
	{
		when(studentService.findById(anyLong())).thenReturn(returnedStudentDTO);

		mockMvc.perform(get(StudentController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", equalTo(EMAIL)));

		verify(studentService).findById(anyLong());
	}

	@Test
	void getStudentByIdNotFound() throws Exception
	{
		when(studentService.findById(anyLong())).thenThrow(NotFoundException.class);

		mockMvc.perform(get(StudentController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(studentService).findById(anyLong());
	}

	@Test
	void getStudentByIdNumberFormatException() throws Exception
	{
		mockMvc.perform(get(StudentController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	void createNewStudent() throws Exception
	{
		StudentDTO studentDTOToCreate =
				StudentDTO.builder()
						.id(ID)
						.firstName(FIRST_NAME)
						.lastName(LAST_NAME)
						.email(EMAIL)
						.password(PASSWORD)
						.enrollmentYear(ENROLLMENT_YEAR)
						.build();

		when(studentService.createNew(any())).thenReturn(returnedStudentDTO);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(studentDTOToCreate);

		mockMvc.perform(post(StudentController.BASE_URL)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.email", equalTo(EMAIL)));

		verify(studentService).createNew(any());
	}

	@Test
	void updateStudent() throws Exception
	{
		StudentDTO studentDTOToUpdate =
				StudentDTO.builder()
						.id(ID)
						.firstName(FIRST_NAME)
						.lastName(LAST_NAME)
						.email(EMAIL)
						.password(PASSWORD)
						.enrollmentYear(ENROLLMENT_YEAR)
						.build();

		when(studentService.update(anyLong(), any())).thenReturn(returnedStudentDTO);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(studentDTOToUpdate);

		mockMvc.perform(put(StudentController.BASE_URL + "/" + ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.email", equalTo(EMAIL)));

		verify(studentService).update(anyLong(), any());
	}

	@Test
	void updateStudentNotFound() throws Exception
	{
		StudentDTO studentDTOToUpdate =
				StudentDTO.builder()
						.id(ID)
						.firstName(FIRST_NAME)
						.lastName(LAST_NAME)
						.email(EMAIL)
						.password(PASSWORD)
						.enrollmentYear(ENROLLMENT_YEAR)
						.build();

		when(studentService.update(anyLong(), any())).thenThrow(NotFoundException.class);

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(studentDTOToUpdate);

		mockMvc.perform(put(StudentController.BASE_URL + "/" + ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isNotFound());

		verify(studentService).update(anyLong(), any());
	}

	@Test
	void updateStudentNumberFormatException() throws Exception
	{
		StudentDTO studentDTOToUpdate = StudentDTO.builder().id(ID).email(EMAIL).build();

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(studentDTOToUpdate);

		mockMvc.perform(put(StudentController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(APPLICATION_JSON_UTF8)
						.content(requestJson))
				.andExpect(status().isBadRequest());
	}


	@Test
	void deleteStudent() throws Exception
	{
		mockMvc.perform(delete(StudentController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

		verify(studentService).deleteById(anyLong());
	}

	@Test
	void deleteStudentNotFound() throws Exception
	{
		doThrow(NotFoundException.class).when(studentService).deleteById(anyLong());

		mockMvc.perform(delete(StudentController.BASE_URL + "/" + ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		verify(studentService).deleteById(anyLong());
	}

	@Test
	void deleteStudentNumberFormatException() throws Exception
	{
		mockMvc.perform(delete(StudentController.BASE_URL + "/" + NO_NUMERIC_ID)
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}
}