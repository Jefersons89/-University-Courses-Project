package com.springframework.universitycourses.repositories;

import com.springframework.universitycourses.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TeacherRepository extends JpaRepository<Teacher, Long>
{
}
