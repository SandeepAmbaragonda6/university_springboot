package com.example.university.service;

import java.util.ArrayList;
import java.util.List;

import com.example.university.model.*;

import com.example.university.repository.ProfessorJpaRepository;
import com.example.university.repository.ProfessorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProfessorJpaService implements ProfessorRepository {

	@Autowired
	private ProfessorJpaRepository professorJpaRepository;

	@Override
	public ArrayList<Professor> getProfessors() {
		List<Professor> professorList = professorJpaRepository.findAll();
		ArrayList<Professor> professors = new ArrayList<>(professorList);
		return professors;
	}

	@Override
	public Professor getProfessorById(int professorId) {
		try {
			Professor professor = professorJpaRepository.findById(professorId).get();
			return professor;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Professor addProfessor(Professor professor) {
		professorJpaRepository.save(professor);
		return professor;
	}

	@Override
	public Professor updateProfessor(int professorId, Professor professor) {
		try {
			Professor newProfessor = professorJpaRepository.findById(professorId).get();
			if (professor.getProfessorName() != null) {
				newProfessor.setProfessorName(professor.getProfessorName());
			}
			if (professor.getDepartment() != null) {
				newProfessor.setDepartment(professor.getDepartment());
			}
			professorJpaRepository.save(newProfessor);
			return newProfessor;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteProfessor(int professorId) {
		try {
			professorJpaRepository.deleteById(professorId);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public Course getProfessorCourse(int professorId) {
		// TODO Auto-generated method stub
		return null;
	}
}