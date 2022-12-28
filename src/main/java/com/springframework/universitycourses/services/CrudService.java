package com.springframework.universitycourses.services;

import java.util.Set;


public interface CrudService<T, K, S>
{
	T findById(K id);

	T findByTitle(S title);

	Set<T> findAll();

	T save(T object);

	void delete(T object);

	void deleteById(K id);
}
