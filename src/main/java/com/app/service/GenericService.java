package com.app.service;


import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.ResponseEntity;

/**
 * Base interface for REST resources.
 */
public interface GenericService<T, ID extends Serializable> {
	public default Iterable<T> findAll() {
		return getRepository().findAll();
	}
	
	public default T get(ID id) {
		return getRepository().findOne(id);
	}
	
	public default T save(T entity) {
		return getRepository().save(entity);
	}
	
	public default ResponseEntity<?> delete(ID id) {
		if (getRepository().exists(id)) {
			getRepository().delete(id);
		} else {
			return notFound();
		}
		return ResponseEntity.noContent().build();
	}
	
	public default ResponseEntity<?> update(T entity) {
		if (getRepository().exists(getId(entity))) {
			getRepository().save(entity);
		} else {
			return notFound();
		}
		return ResponseEntity.ok().build();
	}
	
	public static ResponseEntity<?> notFound() {
		return ResponseEntity.notFound().build();
    }
	
	public ID getId(T entity);
	
	public CrudRepository<T, ID> getRepository();
}
