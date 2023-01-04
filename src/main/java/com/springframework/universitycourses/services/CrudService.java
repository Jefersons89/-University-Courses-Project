package com.springframework.universitycourses.services;

import java.util.Set;


public interface CrudService<T, K>
{
	T findById(K id);

	Set<T> findAll();

	T createNew(T object);

	T save(T object);

	T update(K id, T object);

	void delete(T object);

	void deleteById(K id);
}
