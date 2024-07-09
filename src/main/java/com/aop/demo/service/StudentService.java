package com.aop.demo.service;

import java.util.List;

import com.aop.demo.entity.Student;
import com.aop.demo.exc.UserNotFoundException;

public interface StudentService {
	Student saveStudent(Student student);
	List<Student> getAllStudents();
	Student getStudentById(long id) throws UserNotFoundException;
	Student updateStudent(Student student, long id);
	void deleteStudent(long id) throws UserNotFoundException;
	boolean existsById(long id);
	 boolean existsByName(String name);
	 boolean existsByEmail(String email);
}
