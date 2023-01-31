package com.springframework.universitycourses.services.springdatajpa;

import com.springframework.universitycourses.api.v1.mapper.AssignmentMapper;
import com.springframework.universitycourses.api.v1.mapper.TeacherMapper;
import com.springframework.universitycourses.api.v1.model.AssignmentDTO;
import com.springframework.universitycourses.api.v1.model.TeacherDTO;
import com.springframework.universitycourses.exceptions.NotFoundException;
import com.springframework.universitycourses.model.Role;
import com.springframework.universitycourses.model.Teacher;
import com.springframework.universitycourses.repositories.AssignmentRepository;
import com.springframework.universitycourses.repositories.RoleRepository;
import com.springframework.universitycourses.repositories.TeacherRepository;
import com.springframework.universitycourses.services.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class TeacherSDJpaService implements TeacherService
{
	private final TeacherRepository teacherRepository;
	private final AssignmentRepository assignmentRepository;
	private final RoleRepository roleRepository;
	private final TeacherMapper teacherMapper;
	private final BCryptPasswordEncoder passwordEncoder;
	private final AssignmentMapper assignmentMapper;

	public TeacherSDJpaService(final TeacherRepository teacherRepository, final AssignmentRepository assignmentRepository,
			final RoleRepository roleRepository, final TeacherMapper teacherMapper, final BCryptPasswordEncoder passwordEncoder,
			final AssignmentMapper assignmentMapper)
	{
		this.teacherRepository = teacherRepository;
		this.assignmentRepository = assignmentRepository;
		this.roleRepository = roleRepository;
		this.teacherMapper = teacherMapper;
		this.passwordEncoder = passwordEncoder;
		this.assignmentMapper = assignmentMapper;
	}

	@Override
	public TeacherDTO findById(final Long id)
	{
		Optional<Teacher> teacher = getTeacherRepository().findById(id);

		if (teacher.isEmpty())
		{
			throw new NotFoundException("Teacher Not Found for id: " + id);
		}

		return teacher
				.map(getTeacherMapper()::teacherToTeacherDTO)
				.orElse(null);
	}

	@Override
	public Teacher findByModelById(final Long id)
	{
		Optional<Teacher> teacher = getTeacherRepository().findById(id);

		if (teacher.isEmpty())
		{
			throw new NotFoundException("Teacher Not Found for id: " + id);
		}

		return teacher.orElse(null);
	}

	@Override
	public Set<TeacherDTO> findAll()
	{
		Pageable sortedByFirstName =
				PageRequest.of(0, 3, Sort.by("firstName"));

		Page<Teacher> teachers = getTeacherRepository().findAll(sortedByFirstName);

		return teachers
				.stream()
				.map(getTeacherMapper()::teacherToTeacherDTO)
				.collect(Collectors.toSet());
	}

	@Override
	public Set<AssignmentDTO> getTeacherAssignments(final Long id)
	{
		Teacher teacher = this.findByModelById(id);

		if (teacher.getAssignments().isEmpty())
		{
			throw new NotFoundException("No assignments found for teacher with id: " + id);
		}

		return teacher.getAssignments().stream()
				.map(assignmentMapper::assignmentToAssignmentDTO)
				.collect(Collectors.toSet());
	}

	@Override
	public TeacherDTO createNew(final TeacherDTO object)
	{
		object.setAssignments(new HashSet<>());
		return this.save(object);
	}

	@Override
	public TeacherDTO save(final TeacherDTO teacherDto)
	{
		Teacher teacher = getTeacherMapper().teacherDTOToTeacher(teacherDto);

		if (Objects.nonNull(teacherDto.getRoles()) && !teacherDto.getRoles().isEmpty())
		{
			Set<Role> roles = new HashSet<>();
			teacherDto.getRoles().forEach(role -> {
				Optional<Role> roleOptional = roleRepository.findByName(role);
				roleOptional.ifPresent(roles::add);
			});
			teacher.setRoles(roles);
		}

		teacher.setPassword(passwordEncoder.encode(teacher.getPassword()));

		return getTeacherMapper().teacherToTeacherDTO(
				getTeacherRepository().saveAndFlush(teacher));
	}

	public Teacher save(final Teacher object)
	{
		return getTeacherRepository().saveAndFlush(object);
	}

	@Override
	public TeacherDTO update(final Long id, TeacherDTO object)
	{
		Optional<Teacher> teacher = getTeacherRepository().findById(id);

		if (teacher.isEmpty())
		{
			throw new NotFoundException("Teacher Not Found for id: " + id);
		}

		object.setId(id);
		object.setAssignments(null);
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
		Optional<Teacher> teacher = getTeacherRepository().findById(id);

		if (teacher.isEmpty())
		{
			throw new NotFoundException("Teacher Not Found for id: " + id);
		}

		teacher.ifPresent(value -> {
			value.getAssignments().forEach(assignment -> {
				assignment.setTeacher(null);

				getAssignmentRepository().save(assignment);
			});
			value.setAssignments(null);
			this.save(value);
		});
		getTeacherRepository().deleteById(id);
	}

	public TeacherRepository getTeacherRepository()
	{
		return teacherRepository;
	}

	public AssignmentRepository getAssignmentRepository()
	{
		return assignmentRepository;
	}

	public TeacherMapper getTeacherMapper()
	{
		return teacherMapper;
	}
}
