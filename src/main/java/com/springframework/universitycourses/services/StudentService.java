package com.springframework.universitycourses.services;

import com.springframework.universitycourses.api.v1.model.StudentDTO;
import com.springframework.universitycourses.model.Student;


public interface StudentService extends CrudService<StudentDTO, Long>
{
	Student save(Student object);
}
