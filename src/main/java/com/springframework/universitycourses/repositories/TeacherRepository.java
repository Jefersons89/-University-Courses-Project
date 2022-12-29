package com.springframework.universitycourses.repositories;

import com.springframework.universitycourses.model.Teacher;
import org.springframework.data.repository.CrudRepository;


public interface TeacherRepository extends CrudRepository<Teacher, Long>
{
}
