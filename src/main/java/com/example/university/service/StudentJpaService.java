package com.example.university.service;

import java.util.ArrayList;
import java.util.List;

import com.example.university.model.*;

import com.example.university.repository.StudentJpaRepository;
import com.example.university.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class StudentJpaService implements StudentRepository {

	@Autowired
	private StudentJpaRepository studentJpaRepository;

	@Override
	public ArrayList<Student> getStudents() {
		List<Student> studentList = studentJpaRepository.findAll();
		ArrayList<Student> students = new ArrayList<>(studentList);
		return students;
	}

	@Override
	public Student getStudentById(int studentId) {
		try {
			Student student = studentJpaRepository.findById(studentId).get();
			return student;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Student addStudent(Student student) {
		studentJpaRepository.save(student);
		return student;
	}

	@Override
	public Student updateStudent(int studentId, Student student) {
		try {
			Student newstudent = studentJpaRepository.findById(studentId).get();
			if (student.getStudentName() != null)
				newstudent.setStudentName(student.getStudentName());
			if (student.getEmail() != null)
				newstudent.setEmail(student.getEmail());
			studentJpaRepository.save(newstudent);
			return newstudent;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteStudent(int studentId) {
		try {
			studentJpaRepository.deleteById(studentId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public List<Course> getStudentCourses(int studentId) {

		return null;
	}

}