package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.TeacherMapper;
import com.springframework.universitycourses.api.v1.model.TeacherDTO;
import com.springframework.universitycourses.repositories.TeacherRepository;
import com.springframework.universitycourses.services.TeacherService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TeacherSDJpaService implements TeacherService
{
	private final TeacherRepository teacherRepository;
	private final TeacherMapper teacherMapper;

	public TeacherSDJpaService(final TeacherRepository teacherRepository, final TeacherMapper teacherMapper)
	{
		this.teacherRepository = teacherRepository;
		this.teacherMapper = teacherMapper;
	}

	@Override
	public TeacherDTO findById(final Long id)
	{
		return getTeacherRepository().findById(id)
				.map(getTeacherMapper()::teacherToTeacherDTO)
				.orElse(null);
	}

	@Override
	public Set<TeacherDTO> findAll()
	{
		return getTeacherRepository().findAll()
				.stream()
				.map(getTeacherMapper()::teacherToTeacherDTO)
				.collect(Collectors.toSet());
	}

	@Override
	public TeacherDTO createNew(final TeacherDTO object)
	{
		return this.save(object);
	}

	@Override
	public TeacherDTO save(final TeacherDTO object)
	{
		return getTeacherMapper().teacherToTeacherDTO(
				getTeacherRepository().saveAndFlush(getTeacherMapper().teacherDTOToTeacher(object)));
	}

	@Override
	public TeacherDTO update(final Long id, TeacherDTO object)
	{
		object.setId(id);
		return this.save(object);
	}

	@Override
	public void delete(final TeacherDTO object)
	{
		getTeacherRepository().delete(getTeacherMapper().teacherDTOToTeacher(object));
	}

	@Override
	public void deleteById(final Long id)
	{
		getTeacherRepository().deleteById(id);
	}

	public TeacherRepository getTeacherRepository()
	{
		return teacherRepository;
	}

	public TeacherMapper getTeacherMapper()
	{
		return teacherMapper;
	}
}
