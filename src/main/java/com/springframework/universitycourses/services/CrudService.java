package com.springframework.universitycourses.services;

import java.util.Set;


public interface CrudService<T, K>
{
	T findById(K id);

	Set<T> findAll();

	T save(T object);

	void delete(T object);

	void deleteById(K id);
}
