package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.EnrollmentIdMapper;
import com.springframework.universitycourses.api.v1.mapper.EnrollmentMapper;
import com.springframework.universitycourses.api.v1.model.EnrollmentDTO;
import com.springframework.universitycourses.api.v1.model.EnrollmentIdDTO;
import com.springframework.universitycourses.model.Enrollment;
import com.springframework.universitycourses.repositories.EnrollmentRepository;
import com.springframework.universitycourses.services.EnrollmentService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;


@Service
public class EnrollmentSDJpaService implements EnrollmentService
{
	private final EnrollmentRepository enrollmentRepository;
	private final EnrollmentMapper enrollmentMapper;
	private final EnrollmentIdMapper enrollmentIdMapper;

	public EnrollmentSDJpaService(final EnrollmentRepository enrollmentRepository, final EnrollmentMapper enrollmentMapper,
			final EnrollmentIdMapper enrollmentIdMapper)
	{
		this.enrollmentRepository = enrollmentRepository;
		this.enrollmentMapper = enrollmentMapper;
		this.enrollmentIdMapper = enrollmentIdMapper;
	}

	@Override
	public EnrollmentDTO findById(final EnrollmentIdDTO id)
	{
		return getEnrollmentRepository().findById(getEnrollmentIdMapper().enrollmentIdDTOToEnrollmentId(id))
				.map(getEnrollmentMapper()::enrollmentToEnrollmentDTO)
				.orElse(null);
	}

	@Override
	public Set<EnrollmentDTO> findAll()
	{
		return getEnrollmentRepository().findAll()
				.stream()
				.map(getEnrollmentMapper()::enrollmentToEnrollmentDTO)
				.collect(Collectors.toSet());
	}

	@Override
	public EnrollmentDTO createNew(final EnrollmentDTO object)
	{
		return this.save(object);
	}

	@Override
	public EnrollmentDTO save(final EnrollmentDTO object)
	{
		return getEnrollmentMapper().enrollmentToEnrollmentDTO(
				getEnrollmentRepository().saveAndFlush(getEnrollmentMapper().enrollmentDTOToEnrollment(object)));
	}

	@Override
	public Enrollment save(Enrollment object)
	{
		return getEnrollmentRepository().save(object);
	}

	@Override
	public EnrollmentDTO update(final EnrollmentIdDTO id, EnrollmentDTO object)
	{
		object.setId(id);
		return this.save(object);
	}

	@Override
	public void delete(final EnrollmentDTO object)
	{
		getEnrollmentRepository().delete(getEnrollmentMapper().enrollmentDTOToEnrollment(object));
	}

	@Override
	public void deleteById(final EnrollmentIdDTO id)
	{
		getEnrollmentRepository().deleteById(getEnrollmentIdMapper().enrollmentIdDTOToEnrollmentId(id));
	}

	public EnrollmentRepository getEnrollmentRepository()
	{
		return enrollmentRepository;
	}

	public EnrollmentMapper getEnrollmentMapper()
	{
		return enrollmentMapper;
	}

	public EnrollmentIdMapper getEnrollmentIdMapper()
	{
		return enrollmentIdMapper;
	}
}
