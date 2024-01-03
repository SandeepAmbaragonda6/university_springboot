package com.example.university.service;

import java.util.ArrayList;
import java.util.List;

import com.example.university.model.*;

import com.example.university.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CourseJpaService implements CourseRepository {

	@Autowired
	private CourseJpaRepository courseJpaRepository;

	@Autowired
	private ProfessorJpaRepository professorJpaRepository;

	@Override
	public List<Course> getCourses() {
		List<Course> courseList = courseJpaRepository.findAll();
		ArrayList<Course> courses = new ArrayList<>(courseList);
		return courses;
	}

	@Override
	public Course getCourseById(int courseId) {
		try {
			Course course = courseJpaRepository.findById(courseId).get();
			return course;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong courseId");
		}
	}

	@Override
	public Course addCourse(Course course) {
		Professor professor = course.getProfessor();
		int professorId = professor.getProfessorId();
		try {

			professor = professorJpaRepository.findById(professorId).get();
			course.setProfessor(professor);
			courseJpaRepository.save(course);
			return course;
		} catch (Exception e) {

			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong professorId");
		}

	}

	@Override
	public Course updateCourse(int courseId, Course course) {
		try {
			Course newCourse = courseJpaRepository.findById(courseId).get();
			if (course.getCourseName() != null) {
				newCourse.setCourseName(course.getCourseName());
			}
			if (course.getCredits() != 0) {
				newCourse.setCredits(course.getCredits());
			}
			if (course.getProfessor() != null) {
				Professor professor = course.getProfessor();
				int professorId = professor.getProfessorId();
				Professor newProfessor = professorJpaRepository.findById(professorId).get();
				newCourse.setProfessor(newProfessor);
			}
			courseJpaRepository.save(newCourse);
			return newCourse;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong professorId");
		}
	}

	@Override
	public void deleteCourse(int courseId) {
		try {
			courseJpaRepository.deleteById(courseId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Professor getCourseProfessor(int courseId) {
		try {
			Course course = courseJpaRepository.findById(courseId).get();
			return course.getProfessor();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public List<Student> getCourseStudents(int courseId) {
		try {
			Course course = courseJpaRepository.findById(courseId).get();
			return course.getStudents();
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wrong professorId");
		}
	}

}