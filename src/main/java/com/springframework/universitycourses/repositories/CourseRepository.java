package com.springframework.universitycourses.repositories;

import com.springframework.universitycourses.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Long>
{
}
