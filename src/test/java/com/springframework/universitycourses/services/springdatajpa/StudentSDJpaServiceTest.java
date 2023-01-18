package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.StudentMapper;
import com.springframework.universitycourses.api.v1.model.StudentDTO;
import com.springframework.universitycourses.model.Student;
import com.springframework.universitycourses.repositories.EnrollmentRepository;
import com.springframework.universitycourses.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class StudentSDJpaServiceTest
{
	private static final String EMAIL = "test@dummy.com";
	private static final String PASSWORD = "123";
	private static final String ENCODED_PASSWORD = "TestEncoded";
	private static final Long ID = 1L;
	Student returnedStudent;
	StudentDTO returnedStudentDTO;

	@Mock
	StudentRepository studentRepository;

	@Mock
	EnrollmentRepository enrollmentRepository;

	@Mock
	StudentMapper studentMapper;

	@Mock
	BCryptPasswordEncoder passwordEncoder;

	@InjectMocks
	StudentSDJpaService studentSDJpaService;

	@BeforeEach
	void setUp()
	{
		returnedStudent = Student.builder().id(ID).email(EMAIL).password(PASSWORD).build();

		returnedStudentDTO = new StudentDTO();
		returnedStudentDTO.setId(ID);
		returnedStudentDTO.setEmail(EMAIL);
	}

	@Test
	void findById()
	{
		when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedStudent));
		when(enrollmentRepository.findAll()).thenReturn(Collections.emptyList());
		when(studentMapper.studentToStudentDTO(any())).thenReturn(returnedStudentDTO);
		when(studentMapper.studentDTOToStudent(any())).thenReturn(returnedStudent);

		Student student = studentMapper.studentDTOToStudent(studentSDJpaService.findById(ID));

		assertEquals(returnedStudent.getId(), student.getId());

		verify(studentRepository).findById(anyLong());
		verify(enrollmentRepository).findAll();
		verify(studentMapper).studentToStudentDTO(any());
		verify(studentMapper).studentDTOToStudent(any());
	}

	@Test
	void findAll()
	{
		List<Student> studentList = new ArrayList<>();
		studentList.add(returnedStudent);

		final Page<Student> studentListPage = new PageImpl<>(studentList);

		when(studentRepository.findAll((Pageable) any())).thenReturn(studentListPage);
		when(enrollmentRepository.findAll()).thenReturn(Collections.emptyList());
		when(studentMapper.studentToStudentDTO(any())).thenReturn(returnedStudentDTO);

		Set<StudentDTO> studentDTOSet = studentSDJpaService.findAll();

		assertEquals(1, studentDTOSet.size());

		verify(studentRepository).findAll((Pageable) any());
		verify(enrollmentRepository).findAll();
		verify(studentMapper).studentToStudentDTO(any());
	}

	@Test
	void createNew()
	{
		StudentDTO studentDTOToSave = new StudentDTO();
		studentDTOToSave.setId(ID);
		studentDTOToSave.setEmail(EMAIL);

		when(studentRepository.saveAndFlush(any())).thenReturn(returnedStudent);
		when(studentMapper.studentDTOToStudent(any())).thenReturn(returnedStudent);
		when(studentMapper.studentToStudentDTO(any())).thenReturn(returnedStudentDTO);
		when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);

		Student student = studentMapper.studentDTOToStudent(studentSDJpaService.createNew(studentDTOToSave));

		assertNotNull(student);
		assertEquals(returnedStudent.getId(), student.getId());

		verify(studentRepository).saveAndFlush(any());
		verify(studentMapper, times(2)).studentDTOToStudent(any());
		verify(studentMapper).studentToStudentDTO(any());
		verify(passwordEncoder).encode(anyString());
	}

	@Test
	void save()
	{
		StudentDTO studentDTOToSave = new StudentDTO();
		studentDTOToSave.setId(ID);
		studentDTOToSave.setEmail(EMAIL);

		when(studentRepository.saveAndFlush(any())).thenReturn(returnedStudent);
		when(studentMapper.studentDTOToStudent(any())).thenReturn(returnedStudent);
		when(studentMapper.studentToStudentDTO(any())).thenReturn(returnedStudentDTO);
		when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);

		Student student = studentMapper.studentDTOToStudent(studentSDJpaService.save(studentDTOToSave));

		assertNotNull(student);
		assertEquals(returnedStudent.getId(), student.getId());

		verify(studentRepository).saveAndFlush(any());
		verify(studentMapper, times(2)).studentDTOToStudent(any());
		verify(studentMapper).studentToStudentDTO(any());
		verify(passwordEncoder).encode(anyString());
	}

	@Test
	void testSave()
	{
		Student studentToSave = Student.builder().id(ID).email(EMAIL).build();

		when(studentRepository.save(any())).thenReturn(returnedStudent);

		Student student = studentSDJpaService.save(studentToSave);

		assertEquals(returnedStudent.getId(), student.getId());

		verify(studentRepository).save(any());
	}

	@Test
	void update()
	{
		StudentDTO studentDTOToSave = new StudentDTO();
		studentDTOToSave.setId(ID);
		studentDTOToSave.setEmail(EMAIL);

		when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedStudent));
		when(studentRepository.saveAndFlush(any())).thenReturn(returnedStudent);
		when(studentMapper.studentDTOToStudent(any())).thenReturn(returnedStudent);
		when(studentMapper.studentToStudentDTO(any())).thenReturn(returnedStudentDTO);
		when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);

		Student student = studentMapper.studentDTOToStudent(studentSDJpaService.update(ID, studentDTOToSave));

		assertNotNull(student);
		assertEquals(returnedStudent.getId(), student.getId());

		verify(studentRepository).saveAndFlush(any());
		verify(studentRepository).findById(anyLong());
		verify(studentMapper, times(2)).studentDTOToStudent(any());
		verify(studentMapper).studentToStudentDTO(any());
		verify(passwordEncoder).encode(anyString());
	}

	@Test
	void deleteById()
	{
		when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedStudent));
		when(enrollmentRepository.findAll()).thenReturn(Collections.emptyList());

		studentSDJpaService.deleteById(ID);

		verify(studentRepository).findById(anyLong());
		verify(enrollmentRepository).findAll();
		verify(studentRepository).deleteById(anyLong());
	}
}