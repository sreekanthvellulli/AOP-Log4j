package com.aop.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aop.demo.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	boolean existsById(long id);
	 boolean existsByName(String name);
	    boolean existsByEmail(String email);
}
