package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.AssignmentMapper;
import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.model.Assignment;
import com.springframework.universitycourses.model.Course;
import com.springframework.universitycourses.model.Student;
import com.springframework.universitycourses.model.Teacher;
import com.springframework.universitycourses.repositories.AssignmentRepository;
import com.springframework.universitycourses.repositories.CourseRepository;
import com.springframework.universitycourses.repositories.EnrollmentRepository;
import com.springframework.universitycourses.repositories.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AssignmentSDJpaServiceTest
{
	private static final String TITLE = "TestAssignment";
	private static final Long ID = 1L;
	AssignmentDTO returnedAssignmentDTO;
	Assignment returnedAssignment;
	Course returnedCourse;
	Teacher returnedTeacher;
	Student returnedStudent;

	@Mock
	AssignmentRepository assignmentRepository;

	@Mock
	EnrollmentRepository enrollmentRepository;

	@Mock
	TeacherRepository teacherRepository;

	@Mock
	CourseRepository courseRepository;

	@Mock
	AssignmentMapper assignmentMapper;

	@InjectMocks
	AssignmentSDJpaService assignmentSDJpaService;

	@BeforeEach
	void setUp()
	{
		returnedAssignmentDTO = new AssignmentDTO();
		returnedAssignmentDTO.setId(ID);
		returnedAssignmentDTO.setTitle(TITLE);

		returnedAssignment = Assignment.builder().id(ID).title(TITLE).build();

		returnedTeacher = Teacher.builder().id(ID).firstName("TestFirstName").lastName("TestLastName").build();

		returnedCourse = Course.builder().id(ID).title("TestTitle").build();

		returnedStudent = Student.builder().id(ID).email("test@dummy.com").build();
	}

	@Test
	void findById()
	{
		when(assignmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedAssignment));
		when(enrollmentRepository.findAll()).thenReturn(Collections.emptyList());
		when(assignmentMapper.assignmentToAssignmentDTO(any())).thenReturn(returnedAssignmentDTO);
		when(assignmentMapper.assignmentDTOToAssignment(any())).thenReturn(returnedAssignment);

		Assignment assignmentTest = assignmentMapper.assignmentDTOToAssignment(assignmentSDJpaService.findById(ID));

		assertEquals(ID, assignmentTest.getId());

		verify(assignmentRepository).findById(anyLong());
		verify(enrollmentRepository).findAll();
		verify(assignmentMapper).assignmentToAssignmentDTO(any());
		verify(assignmentMapper).assignmentDTOToAssignment(any());
	}

	@Test
	void findByModelById()
	{
		when(assignmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedAssignment));
		when(enrollmentRepository.findAll()).thenReturn(Collections.emptyList());

		Assignment assignmentTest = assignmentSDJpaService.findByModelById(ID);

		assertEquals(ID, assignmentTest.getId());

		verify(assignmentRepository).findById(anyLong());
		verify(enrollmentRepository).findAll();
	}

	@Test
	void findAll()
	{
		List<Assignment> assignmentTestSet = new ArrayList<>();
		assignmentTestSet.add(returnedAssignment);

		final Page<Assignment> assignmentTestPage = new PageImpl<>(assignmentTestSet);

		when(assignmentRepository.findAll((Pageable) any())).thenReturn(assignmentTestPage);
		when(enrollmentRepository.findAll()).thenReturn(Collections.emptyList());
		when(assignmentMapper.assignmentToAssignmentDTO(any())).thenReturn(returnedAssignmentDTO);

		Set<AssignmentDTO> assignmentTest = assignmentSDJpaService.findAll();

		assertEquals(1, assignmentTest.size());

		verify(assignmentRepository).findAll((Pageable) any());
		verify(enrollmentRepository).findAll();
		verify(assignmentMapper).assignmentToAssignmentDTO(any());
	}

	@Test
	void createNew()
	{
		AssignmentDTO assignmentToSave = new AssignmentDTO();
		assignmentToSave.setId(ID);
		assignmentToSave.setTitle(TITLE);
		assignmentToSave.setCourseId(2L);
		assignmentToSave.setTeacherId(3L);

		when(assignmentMapper.assignmentDTOToAssignment(any())).thenReturn(returnedAssignment);
		when(assignmentRepository.saveAndFlush(any())).thenReturn(returnedAssignment);
		when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedTeacher));
		when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedCourse));

		Assignment assignment = assignmentMapper.assignmentDTOToAssignment(assignmentSDJpaService.createNew(assignmentToSave));

		assertNotNull(assignment);
		assertEquals(returnedAssignment.getId(), assignment.getId());

		verify(teacherRepository).findById(anyLong());
		verify(courseRepository).findById(anyLong());
		verify(assignmentRepository).saveAndFlush(any());
	}

	@Test
	void save()
	{
		AssignmentDTO assignmentToSave = new AssignmentDTO();
		assignmentToSave.setId(ID);
		assignmentToSave.setTitle(TITLE);
		assignmentToSave.setCourseId(2L);
		assignmentToSave.setTeacherId(3L);

		when(assignmentMapper.assignmentDTOToAssignment(any())).thenReturn(returnedAssignment);
		when(assignmentRepository.saveAndFlush(any())).thenReturn(returnedAssignment);
		when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedTeacher));
		when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedCourse));

		Assignment assignment = assignmentMapper.assignmentDTOToAssignment(assignmentSDJpaService.save(assignmentToSave));

		assertNotNull(assignment);
		assertEquals(returnedAssignment.getId(), assignment.getId());

		verify(teacherRepository).findById(anyLong());
		verify(courseRepository).findById(anyLong());
		verify(assignmentRepository).saveAndFlush(any());
	}

	@Test
	void testSave()
	{
		Assignment assignmentToSave = Assignment.builder().id(ID).title(TITLE).build();

		when(assignmentRepository.save(any())).thenReturn(returnedAssignment);

		Assignment assignment = assignmentSDJpaService.save(assignmentToSave);

		assertNotNull(assignment);
		assertEquals(returnedAssignment.getId(), assignment.getId());

		verify(assignmentRepository).save(any());
	}

	@Test
	void update()
	{
		AssignmentDTO assignmentToSave = new AssignmentDTO();
		assignmentToSave.setId(ID);
		assignmentToSave.setTitle(TITLE);
		assignmentToSave.setCourseId(2L);
		assignmentToSave.setTeacherId(3L);

		when(assignmentMapper.assignmentDTOToAssignment(any())).thenReturn(returnedAssignment);
		when(assignmentRepository.findById(any())).thenReturn(Optional.ofNullable(returnedAssignment));
		when(assignmentRepository.saveAndFlush(any())).thenReturn(returnedAssignment);
		when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedTeacher));
		when(courseRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedCourse));

		Assignment assignment = assignmentMapper.assignmentDTOToAssignment(assignmentSDJpaService.update(ID, assignmentToSave));

		assertNotNull(assignment);
		assertEquals(returnedAssignment.getId(), assignment.getId());

		verify(teacherRepository).findById(anyLong());
		verify(courseRepository).findById(anyLong());
		verify(assignmentRepository).saveAndFlush(any());
	}

	@Test
	void deleteById()
	{
		when(assignmentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedAssignment));
		when(enrollmentRepository.findAll()).thenReturn(Collections.emptyList());

		assignmentSDJpaService.deleteById(ID);

		verify(assignmentRepository).findById(anyLong());
		verify(assignmentRepository).deleteById(anyLong());
	}
}