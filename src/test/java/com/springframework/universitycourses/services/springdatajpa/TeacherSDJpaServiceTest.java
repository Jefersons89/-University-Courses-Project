package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.TeacherMapper;
import com.springframework.universitycourses.api.v1.model.TeacherDTO;
import com.springframework.universitycourses.model.Teacher;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
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
class TeacherSDJpaServiceTest
{
	private static final String EMAIL = "test@dummy.com";
	private static final String PASSWORD = "123";
	private static final String ENCODED_PASSWORD = "TestEncoded";
	private static final Long ID = 1L;
	Teacher returnedTeacher;
	TeacherDTO returnedTeacherDTO;

	@Mock
	TeacherRepository teacherRepository;

	@Mock
	TeacherMapper teacherMapper;

	@Mock
	BCryptPasswordEncoder passwordEncoder;

	@InjectMocks
	TeacherSDJpaService teacherSDJpaService;

	@BeforeEach
	void setUp()
	{
		returnedTeacher = Teacher.builder().id(ID).email(EMAIL).password(PASSWORD).assignments(new HashSet<>()).build();

		returnedTeacherDTO = new TeacherDTO();
		returnedTeacher.setId(ID);
		returnedTeacher.setEmail(EMAIL);
	}

	@Test
	void findById()
	{
		when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedTeacher));
		when(teacherMapper.teacherToTeacherDTO(any())).thenReturn(returnedTeacherDTO);
		when(teacherMapper.teacherDTOToTeacher(any())).thenReturn(returnedTeacher);

		Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherSDJpaService.findById(ID));

		assertEquals(returnedTeacher.getId(), teacher.getId());

		verify(teacherRepository).findById(anyLong());
		verify(teacherMapper).teacherToTeacherDTO(any());
		verify(teacherMapper).teacherDTOToTeacher(any());
	}

	@Test
	void findAll()
	{
		List<Teacher> teacherList = new ArrayList<>();
		teacherList.add(returnedTeacher);

		final Page<Teacher> teacherListPage = new PageImpl<>(teacherList);

		when(teacherRepository.findAll((Pageable) any())).thenReturn(teacherListPage);
		when(teacherMapper.teacherToTeacherDTO(any())).thenReturn(returnedTeacherDTO);

		Set<TeacherDTO> teacherDTOSet = teacherSDJpaService.findAll();

		assertEquals(1, teacherDTOSet.size());

		verify(teacherRepository).findAll((Pageable) any());
		verify(teacherMapper).teacherToTeacherDTO(any());
	}

	@Test
	void createNew()
	{
		TeacherDTO teacherDTOToSave = new TeacherDTO();
		teacherDTOToSave.setId(ID);
		teacherDTOToSave.setEmail(EMAIL);

		when(teacherRepository.saveAndFlush(any())).thenReturn(returnedTeacher);
		when(teacherMapper.teacherDTOToTeacher(any())).thenReturn(returnedTeacher);
		when(teacherMapper.teacherToTeacherDTO(any())).thenReturn(returnedTeacherDTO);
		when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);

		Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherSDJpaService.createNew(teacherDTOToSave));

		assertNotNull(teacher);
		assertEquals(returnedTeacher.getId(), teacher.getId());

		verify(teacherRepository).saveAndFlush(any());
		verify(teacherMapper).teacherToTeacherDTO(any());
		verify(teacherMapper, times(2)).teacherDTOToTeacher(any());
		verify(passwordEncoder).encode(anyString());
	}

	@Test
	void save()
	{
		TeacherDTO teacherDTOToSave = new TeacherDTO();
		teacherDTOToSave.setId(ID);
		teacherDTOToSave.setEmail(EMAIL);

		when(teacherRepository.saveAndFlush(any())).thenReturn(returnedTeacher);
		when(teacherMapper.teacherDTOToTeacher(any())).thenReturn(returnedTeacher);
		when(teacherMapper.teacherToTeacherDTO(any())).thenReturn(returnedTeacherDTO);
		when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);

		Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherSDJpaService.save(teacherDTOToSave));

		assertNotNull(teacher);
		assertEquals(returnedTeacher.getId(), teacher.getId());

		verify(teacherRepository).saveAndFlush(any());
		verify(teacherMapper).teacherToTeacherDTO(any());
		verify(teacherMapper, times(2)).teacherDTOToTeacher(any());
		verify(passwordEncoder).encode(anyString());
	}

	@Test
	void testSave()
	{
		Teacher teacherToSave = Teacher.builder().id(ID).email(EMAIL).build();
		when(teacherRepository.saveAndFlush(any())).thenReturn(returnedTeacher);

		Teacher teacher = teacherSDJpaService.save(teacherToSave);

		assertNotNull(teacher);
		assertEquals(returnedTeacher.getId(), teacher.getId());

		verify(teacherRepository).saveAndFlush(any());
	}

	@Test
	void update()
	{
		TeacherDTO teacherDTOToSave = new TeacherDTO();
		teacherDTOToSave.setId(ID);
		teacherDTOToSave.setEmail(EMAIL);

		when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedTeacher));
		when(teacherRepository.saveAndFlush(any())).thenReturn(returnedTeacher);
		when(teacherMapper.teacherDTOToTeacher(any())).thenReturn(returnedTeacher);
		when(teacherMapper.teacherToTeacherDTO(any())).thenReturn(returnedTeacherDTO);
		when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD);

		Teacher teacher = teacherMapper.teacherDTOToTeacher(teacherSDJpaService.update(ID, teacherDTOToSave));

		assertNotNull(teacher);
		assertEquals(returnedTeacher.getId(), teacher.getId());

		verify(teacherRepository).saveAndFlush(any());
		verify(teacherMapper).teacherToTeacherDTO(any());
		verify(teacherMapper, times(2)).teacherDTOToTeacher(any());
		verify(passwordEncoder).encode(anyString());
	}

	@Test
	void deleteById()
	{
		when(teacherRepository.findById(anyLong())).thenReturn(Optional.ofNullable(returnedTeacher));

		teacherSDJpaService.deleteById(ID);

		verify(teacherRepository).findById(anyLong());
		verify(teacherRepository).deleteById(anyLong());
	}
}