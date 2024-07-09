package com.aop.demo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aop.demo.entity.Student;
import com.aop.demo.exc.UserNotFoundException;
import com.aop.demo.repository.StudentRepository;
import com.aop.demo.service.StudentService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;

	@Override
	public Student saveStudent(Student student) {
		return studentRepository.save(student);
	}

	@Transactional(readOnly = true)
	@Override
	public List<Student> getAllStudents() {
		return studentRepository.findAll();
	}

	@Override
	public Student getStudentById(long id) throws UserNotFoundException {
		return studentRepository.findById((int) id)
				.orElseThrow(() -> new UserNotFoundException("Student not found with id: " + id));
	}

	@Override
	public Student updateStudent(Student student, long id) {
		Student st = studentRepository.findById((int) id).get();
		st.setName(student.getName());
		st.setEmail(student.getEmail());
		st.setAge(student.getAge());
		studentRepository.save(st);
		return st;
	}

	@Override
	public void deleteStudent(long id) throws UserNotFoundException {
		studentRepository.findById((int) id).get();
		studentRepository.deleteById((int) id);
	}

	@Override
	public boolean existsById(long id) {
		return studentRepository.existsById(id);

	}

	public boolean existsByName(String name) {
		return studentRepository.existsByName(name);
	}

	public boolean existsByEmail(String email) {
		return studentRepository.existsByEmail(email);
	}
}
