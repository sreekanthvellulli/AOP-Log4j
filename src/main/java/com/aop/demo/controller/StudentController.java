package com.aop.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aop.demo.entity.Student;
import com.aop.demo.exc.UserNotFoundException;
import com.aop.demo.responce.Response;
import com.aop.demo.service.StudentService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/student")
public class StudentController {
	@Autowired
	private StudentService studentService;

	// http://localhost:8082/api/student
	@PostMapping()
	public ResponseEntity<?> saveStudent(@Valid @RequestBody Student student) {
		if (studentService.existsByName(student.getName())) {
			Response response = new Response("Name already exists");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		if (studentService.existsByEmail(student.getEmail())) {
			Response response = new Response("Email already exists");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
		return new ResponseEntity<Student>(studentService.saveStudent(student), HttpStatus.CREATED);
	}

	// http://localhost:8082/api/student
	@GetMapping
	public List<Student> getAllEmployees() {
		return studentService.getAllStudents();
	}

	// http://localhost:8082/api/student/1
	@GetMapping("{id}")
	public ResponseEntity<Student> getEmployeeById(@PathVariable("id") long id) throws UserNotFoundException {
		return new ResponseEntity<Student>(studentService.getStudentById(id), HttpStatus.OK);
	}

	// http://localhost:8082/api/student/1
	@PutMapping("{id}")
	public ResponseEntity<Student> updateEmployee(@Valid @PathVariable("id") long id, @RequestBody Student student) {
		return new ResponseEntity<Student>(studentService.updateStudent(student, id), HttpStatus.OK);
	}

	// http://localhost:8082/api/student/1
	@DeleteMapping("{id}")
	public ResponseEntity<Response> deleteStudent(@PathVariable("id") long id) throws UserNotFoundException {
		if (studentService.existsById(id)) {
			studentService.deleteStudent(id);
			Response response = new Response("Student id " + id + " deleted.");
			return ResponseEntity.ok(response);
		} else {
			Response response = new Response("Student id " + id + " not found.");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}
}
