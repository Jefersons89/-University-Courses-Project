package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.model.Enrollment;
import com.springframework.universitycourses.model.EnrollmentId;
import com.springframework.universitycourses.repositories.EnrollmentRepository;
import com.springframework.universitycourses.services.EnrollmentService;

import java.util.HashSet;
import java.util.Set;


public class EnrollmentSDJpaService implements EnrollmentService
{
	private final EnrollmentRepository enrollmentRepository;

	public EnrollmentSDJpaService(final EnrollmentRepository enrollmentRepository)
	{
		this.enrollmentRepository = enrollmentRepository;
	}

	@Override
	public Enrollment findById(final EnrollmentId id)
	{
		return getEnrollmentRepository().findById(id).orElse(null);
	}

	@Override
	public Set<Enrollment> findAll()
	{
		Set<Enrollment> enrollments = new HashSet<>();
		getEnrollmentRepository().findAll().forEach(enrollments::add);
		return enrollments;
	}

	@Override
	public Enrollment save(final Enrollment object)
	{
		return getEnrollmentRepository().save(object);
	}

	@Override
	public void delete(final Enrollment object)
	{
		getEnrollmentRepository().delete(object);
	}

	@Override
	public void deleteById(final EnrollmentId id)
	{
		getEnrollmentRepository().deleteById(id);
	}

	public EnrollmentRepository getEnrollmentRepository()
	{
		return enrollmentRepository;
	}
}
