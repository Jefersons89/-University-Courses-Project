package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.model.Teacher;
import com.springframework.universitycourses.repositories.TeacherRepository;
import com.springframework.universitycourses.services.TeacherService;

import java.util.HashSet;
import java.util.Set;


public class TeacherSDJpaService implements TeacherService
{
	private final TeacherRepository teacherRepository;

	public TeacherSDJpaService(final TeacherRepository teacherRepository)
	{
		this.teacherRepository = teacherRepository;
	}

	@Override
	public Teacher findById(final Long id)
	{
		return getTeacherRepository().findById(id).orElse(null);
	}

	@Override
	public Set<Teacher> findAll()
	{
		Set<Teacher> teachers = new HashSet<>();
		getTeacherRepository().findAll().forEach(teachers::add);
		return teachers;
	}

	@Override
	public Teacher save(final Teacher object)
	{
		return getTeacherRepository().save(object);
	}

	@Override
	public void delete(final Teacher object)
	{
		getTeacherRepository().delete(object);
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
}
