package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.EnrollmentIdMapper;
import com.springframework.universitycourses.api.v1.mapper.EnrollmentMapper;
import com.springframework.universitycourses.api.v1.model.EnrollmentDTO;
import com.springframework.universitycourses.api.v1.model.EnrollmentIdDTO;
import com.springframework.universitycourses.model.Assignment;
import com.springframework.universitycourses.model.Enrollment;
import com.springframework.universitycourses.model.EnrollmentId;
import com.springframework.universitycourses.model.Student;
import com.springframework.universitycourses.repositories.AssignmentRepository;
import com.springframework.universitycourses.repositories.EnrollmentRepository;
import com.springframework.universitycourses.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
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
class EnrollmentSDJpaServiceTest
{
	private static final Long STUDENT_ID = 1L;
	private static final Long ASSIGNMENT_ID = 2L;
	EnrollmentId returnedEnrollmentId;
	Enrollment returnedEnrollment;
	EnrollmentDTO returnedEnrollmentDTO;
	EnrollmentIdDTO enrollmentIdDTO;
	Student returnedStudent;
	Assignment returnedAssignment;

	@Mock
	EnrollmentRepository enrollmentRepository;

	@Mock
	StudentRepository studentRepository;

	@Mock
	AssignmentRepository assignmentRepository;

	@Mock
	EnrollmentIdMapper enrollmentIdMapper;

	@Mock
	EnrollmentMapper enrollmentMapper;

	@InjectMocks
	EnrollmentSDJpaService enrollmentSDJpaService;

	@BeforeEach
	void setUp()
	{
		enrollmentIdDTO = new EnrollmentIdDTO(STUDENT_ID, ASSIGNMENT_ID);

		returnedEnrollmentDTO = new EnrollmentDTO();
		returnedEnrollmentDTO.setId(enrollmentIdDTO);

		returnedEnrollmentId = EnrollmentId.builder().studentId(STUDENT_ID).assignmentId(ASSIGNMENT_ID).build();
		returnedEnrollment = Enrollment.builder().id(returnedEnrollmentId).build();
		returnedStudent = Student.builder().id(STUDENT_ID).build();
		returnedAssignment = Assignment.builder().id(ASSIGNMENT_ID).build();
	}

	@Test
	void findById()
	{
		when(enrollmentIdMapper.enrollmentIdDTOToEnrollmentId(any())).thenReturn(returnedEnrollmentId);
		when(enrollmentRepository.findById(any())).thenReturn(Optional.ofNullable(returnedEnrollment));
		when(enrollmentMapper.enrollmentToEnrollmentDTO(any())).thenReturn(returnedEnrollmentDTO);
		when(enrollmentMapper.enrollmentDTOToEnrollment(any())).thenReturn(returnedEnrollment);

		Enrollment enrollment = enrollmentMapper.enrollmentDTOToEnrollment(enrollmentSDJpaService.findById(enrollmentIdDTO));

		assertEquals(returnedEnrollment.getId(), enrollment.getId());

		verify(enrollmentIdMapper).enrollmentIdDTOToEnrollmentId(any());
		verify(enrollmentRepository).findById(any());
		verify(enrollmentMapper).enrollmentToEnrollmentDTO(any());
		verify(enrollmentMapper).enrollmentDTOToEnrollment(any());
	}

	@Test
	void findAll()
	{
		List<Enrollment> enrollmentList = new ArrayList<>();
		enrollmentList.add(returnedEnrollment);

		when(enrollmentRepository.findAll()).thenReturn(enrollmentList);
		when(enrollmentMapper.enrollmentToEnrollmentDTO(any())).thenReturn(returnedEnrollmentDTO);

		Set<EnrollmentDTO> enrollmentDTOSet = enrollmentSDJpaService.findAll();

		assertEquals(1, enrollmentDTOSet.size());

		verify(enrollmentRepository).findAll();
		verify(enrollmentMapper).enrollmentToEnrollmentDTO(any());
	}

	@Test
	void createNew()
	{
		EnrollmentDTO enrollmentDTOToSave = new EnrollmentDTO();
		enrollmentDTOToSave.setId(new EnrollmentIdDTO(STUDENT_ID, ASSIGNMENT_ID));

		when(enrollmentMapper.enrollmentDTOToEnrollment(any())).thenReturn(returnedEnrollment);
		when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedStudent));
		when(assignmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedAssignment));
		when(enrollmentRepository.saveAndFlush(any())).thenReturn(returnedEnrollment);
		when(enrollmentMapper.enrollmentToEnrollmentDTO(any())).thenReturn(returnedEnrollmentDTO);

		Enrollment enrollment = enrollmentMapper.enrollmentDTOToEnrollment(enrollmentSDJpaService.createNew(enrollmentDTOToSave));

		assertNotNull(enrollment);
		assertEquals(returnedEnrollment.getId(), enrollment.getId());

		verify(studentRepository).findById(anyLong());
		verify(assignmentRepository).findById(anyLong());
		verify(enrollmentRepository).saveAndFlush(any());
		verify(enrollmentMapper, times(2)).enrollmentDTOToEnrollment(any());
		verify(enrollmentMapper).enrollmentToEnrollmentDTO(any());
	}

	@Test
	void save()
	{
		EnrollmentDTO enrollmentDTOToSave = new EnrollmentDTO();
		enrollmentDTOToSave.setId(new EnrollmentIdDTO(STUDENT_ID, ASSIGNMENT_ID));

		when(enrollmentMapper.enrollmentDTOToEnrollment(any())).thenReturn(returnedEnrollment);
		when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedStudent));
		when(assignmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedAssignment));
		when(enrollmentRepository.saveAndFlush(any())).thenReturn(returnedEnrollment);
		when(enrollmentMapper.enrollmentToEnrollmentDTO(any())).thenReturn(returnedEnrollmentDTO);

		Enrollment enrollment = enrollmentMapper.enrollmentDTOToEnrollment(enrollmentSDJpaService.save(enrollmentDTOToSave));

		assertNotNull(enrollment);
		assertEquals(returnedEnrollment.getId(), enrollment.getId());

		verify(studentRepository).findById(anyLong());
		verify(assignmentRepository).findById(anyLong());
		verify(enrollmentRepository).saveAndFlush(any());
		verify(enrollmentMapper, times(2)).enrollmentDTOToEnrollment(any());
		verify(enrollmentMapper).enrollmentToEnrollmentDTO(any());
	}

	@Test
	void testSave()
	{
		Enrollment enrollmentToSave = Enrollment.builder()
				.id(EnrollmentId.builder().assignmentId(ASSIGNMENT_ID).studentId(STUDENT_ID).build()).build();
		when(enrollmentRepository.save(any())).thenReturn(returnedEnrollment);

		Enrollment enrollment = enrollmentSDJpaService.save(enrollmentToSave);

		assertNotNull(enrollment);
		assertEquals(returnedEnrollment.getId(), enrollment.getId());

		verify(enrollmentRepository).save(any());
	}

	@Test
	void update()
	{
		EnrollmentDTO enrollmentDTOToSave = new EnrollmentDTO();
		enrollmentDTOToSave.setId(new EnrollmentIdDTO(STUDENT_ID, ASSIGNMENT_ID));

		when(enrollmentMapper.enrollmentDTOToEnrollment(any())).thenReturn(returnedEnrollment);
		when(studentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedStudent));
		when(assignmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedAssignment));
		when(enrollmentRepository.saveAndFlush(any())).thenReturn(returnedEnrollment);
		when(enrollmentMapper.enrollmentToEnrollmentDTO(any())).thenReturn(returnedEnrollmentDTO);

		Enrollment enrollment = enrollmentMapper.enrollmentDTOToEnrollment(
				enrollmentSDJpaService.update(enrollmentIdDTO, enrollmentDTOToSave));

		assertNotNull(enrollment);
		assertEquals(returnedEnrollment.getId(), enrollment.getId());

		verify(studentRepository).findById(anyLong());
		verify(assignmentRepository).findById(anyLong());
		verify(enrollmentRepository).saveAndFlush(any());
		verify(enrollmentMapper, times(2)).enrollmentDTOToEnrollment(any());
		verify(enrollmentMapper).enrollmentToEnrollmentDTO(any());
	}

	@Test
	void deleteById()
	{
		EnrollmentIdDTO enrollmentIdDTOToDelete = new EnrollmentIdDTO(ASSIGNMENT_ID, STUDENT_ID);
		when(enrollmentIdMapper.enrollmentIdDTOToEnrollmentId(any())).thenReturn(returnedEnrollmentId);

		enrollmentSDJpaService.deleteById(enrollmentIdDTOToDelete);

		verify(enrollmentIdMapper).enrollmentIdDTOToEnrollmentId(any());
		verify(enrollmentRepository).deleteById(any());
	}
}