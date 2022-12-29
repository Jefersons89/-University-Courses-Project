package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.model.Student;
import com.springframework.universitycourses.repositories.StudentRepository;
import com.springframework.universitycourses.services.StudentService;

import java.util.HashSet;
import java.util.Set;


public class StudentSDJpaService implements StudentService
{
	private final StudentRepository studentRepository;

	public StudentSDJpaService(final StudentRepository studentRepository)
	{
		this.studentRepository = studentRepository;
	}

	@Override
	public Student findById(final Long id)
	{
		return getStudentRepository().findById(id).orElse(null);
	}

	@Override
	public Set<Student> findAll()
	{
		Set<Student> students = new HashSet<>();
		getStudentRepository().findAll().forEach(students::add);
		return students;
	}

	@Override
	public Student save(final Student object)
	{
		return getStudentRepository().save(object);
	}

	@Override
	public void delete(final Student object)
	{
		getStudentRepository().delete(object);
	}

	@Override
	public void deleteById(final Long id)
	{
		getStudentRepository().deleteById(id);
	}

	public StudentRepository getStudentRepository()
	{
		return studentRepository;
	}
}
