package com.springframework.universitycourses.repositories;

import com.springframework.universitycourses.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long>
{
}
