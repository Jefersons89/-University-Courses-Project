package com.springframework.universitycourses.repositories;

import com.springframework.universitycourses.model.Student;
import org.springframework.data.repository.CrudRepository;


public interface StudentRepository extends CrudRepository<Student, Long>
{
}
