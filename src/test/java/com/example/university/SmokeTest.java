package com.example.university;

import com.example.university.controller.CourseController;
import com.example.university.controller.ProfessorController;
import com.example.university.controller.StudentController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private StudentController studentController;

    @Autowired
    private ProfessorController professorController;

    @Autowired
    private CourseController courseController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(studentController).isNotNull();
        assertThat(professorController).isNotNull();
        assertThat(courseController).isNotNull();
    }
}
