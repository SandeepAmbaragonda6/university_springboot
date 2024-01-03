package com.example.university;

import com.example.university.model.Course;
import com.example.university.model.Professor;
import com.example.university.model.Student;
import com.example.university.repository.CourseJpaRepository;
import com.example.university.repository.ProfessorJpaRepository;
import com.example.university.repository.StudentJpaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.notNullValue;
import javax.transaction.Transactional;
import java.util.HashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = { "/schema.sql", "/data.sql" })
public class UniversityControllerTests {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ProfessorJpaRepository professorJpaRepository;

        @Autowired
        private CourseJpaRepository courseJpaRepository;

        @Autowired
        private StudentJpaRepository studentJpaRepository;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        private HashMap<Integer, Object[]> professorsHashMap = new HashMap<>(); // Professor
        {
                professorsHashMap.put(1, new Object[] { "John Smith", "Computer Science" });
                professorsHashMap.put(2, new Object[] { "Mary Johnson", "Physics" });
                professorsHashMap.put(3, new Object[] { "David Lee", "Mathematics" });
                professorsHashMap.put(4, new Object[] { "Mark Willam", "Mathematics" }); // POST
                professorsHashMap.put(5, new Object[] { "Mark Williams", "Mathematics" }); // PUT
        }

        private HashMap<Integer, Object[]> coursesHashMap = new HashMap<>(); // Course
        {
                coursesHashMap.put(1, new Object[] { "Introduction to Programming", 3, 1, new Integer[] { 1, 2 } });
                coursesHashMap.put(2, new Object[] { "Quantum Mechanics", 4, 2, new Integer[] { 2, 3 } });
                coursesHashMap.put(3, new Object[] { "Calculus", 4, 3, new Integer[] { 1, 3, 4 } });
                coursesHashMap.put(4, new Object[] { "Statistics", 5, 3, new Integer[] { 2, 3 } }); // POST
                coursesHashMap.put(5, new Object[] { "Statistics", 4, 4, new Integer[] { 1, 3, 4 } }); // PUT
        }

        private HashMap<Integer, Object[]> studentsHashMap = new HashMap<>(); // Student
        {
                studentsHashMap.put(1,
                                new Object[] { "Alice Johnson", "alice@example.com", new Integer[] { 1, 3, 4 } });
                studentsHashMap.put(2, new Object[] { "Bob Davis", "bob@example.com", new Integer[] { 1, 2 } });
                studentsHashMap.put(3, new Object[] { "Eva Wilson", "eva@example.com", new Integer[] { 2, 3, 4 } });
                studentsHashMap.put(4, new Object[] { "Harley Hoies", "harley@example.com", new Integer[] { 2, 4 } }); // POST
                studentsHashMap.put(5, new Object[] { "Harley Homes", "harley@example.com", new Integer[] { 3, 4 } }); // PUT
        }

        @Test
        @Order(1)
        public void testGetProfessors() throws Exception {
                mockMvc.perform(get("/professors")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].professorId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].professorName",
                                                Matchers.equalTo(professorsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].department", Matchers.equalTo(professorsHashMap.get(1)[1])))

                                .andExpect(jsonPath("$[1].professorId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].professorName",
                                                Matchers.equalTo(professorsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].department", Matchers.equalTo(professorsHashMap.get(2)[1])))

                                .andExpect(jsonPath("$[2].professorId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].professorName",
                                                Matchers.equalTo(professorsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].department", Matchers.equalTo(professorsHashMap.get(3)[1])));
        }

        @Test
        @Order(2)
        public void testGetProfessorNotFound() throws Exception {
                mockMvc.perform(get("/professors/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(3)
        public void testGetProfessorById() throws Exception {
                mockMvc.perform(get("/professors/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.professorId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.professorName", Matchers.equalTo(professorsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.department", Matchers.equalTo(professorsHashMap.get(1)[1])));

                mockMvc.perform(get("/professors/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.professorId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.professorName", Matchers.equalTo(professorsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.department", Matchers.equalTo(professorsHashMap.get(2)[1])));

                mockMvc.perform(get("/professors/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.professorId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.professorName", Matchers.equalTo(professorsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.department", Matchers.equalTo(professorsHashMap.get(3)[1])));
        }

        @Test
        @Order(4)
        public void testPostProfessor() throws Exception {
                String content = "{\n    \"professorName\": \"" + professorsHashMap.get(4)[0]
                                + "\",\n    \"department\": \"" + professorsHashMap.get(4)[1] + "\"\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/professors")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.professorId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.professorName", Matchers.equalTo(professorsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.department", Matchers.equalTo(professorsHashMap.get(4)[1])));
        }

        @Test
        @Order(5)
        public void testAfterPostProfessor() throws Exception {
                mockMvc.perform(get("/professors/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.professorId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.professorName", Matchers.equalTo(professorsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.department", Matchers.equalTo(professorsHashMap.get(4)[1])));
        }

        @Test
        @Order(6)
        public void testDbAfterPostProfessor() throws Exception {
                Professor professor = professorJpaRepository.findById(4).get();

                assertEquals(professor.getProfessorId(), 4);
                assertEquals(professor.getProfessorName(), professorsHashMap.get(4)[0]);
                assertEquals(professor.getDepartment(), professorsHashMap.get(4)[1]);
        }

        @Test
        @Order(7)
        public void testPutProfessorNotFound() throws Exception {
                String content = "{\n    \"professorName\": \"" + professorsHashMap.get(5)[0]
                                + "\",\n    \"department\": \"" + professorsHashMap.get(5)[1] + "\"\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/professors/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(8)
        public void testPutProfessor() throws Exception {
                String content = "{\n    \"professorName\": \"" + professorsHashMap.get(5)[0]
                                + "\",\n    \"department\": \"" + professorsHashMap.get(5)[1] + "\"\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/professors/4")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.professorId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.professorName", Matchers.equalTo(professorsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.department", Matchers.equalTo(professorsHashMap.get(5)[1])));
        }

        @Test
        @Order(9)
        public void testAfterPutProfessor() throws Exception {
                mockMvc.perform(get("/professors/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", notNullValue()))
                                .andExpect(jsonPath("$.professorId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.professorName", Matchers.equalTo(professorsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.department", Matchers.equalTo(professorsHashMap.get(5)[1])));
        }

        @Test
        @Order(10)
        public void testDbAfterPutProfessor() throws Exception {
                Professor professor = professorJpaRepository.findById(4).get();

                assertEquals(professor.getProfessorId(), 4);
                assertEquals(professor.getProfessorName(), professorsHashMap.get(5)[0]);
                assertEquals(professor.getDepartment(), professorsHashMap.get(5)[1]);
        }

        @Test
        @Order(11)
        public void testGetCourses() throws Exception {
                mockMvc.perform(get("/courses")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].courseId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].courseName", Matchers.equalTo(coursesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].credits", Matchers.equalTo(coursesHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(1)[2])))
                                .andExpect(jsonPath("$[0].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(1)[3])[0])))
                                .andExpect(jsonPath("$[0].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(1)[3])[1])))

                                .andExpect(jsonPath("$[1].courseId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].courseName", Matchers.equalTo(coursesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].credits", Matchers.equalTo(coursesHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(2)[2])))
                                .andExpect(jsonPath("$[1].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(2)[3])[0])))
                                .andExpect(jsonPath("$[1].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(2)[3])[1])))

                                .andExpect(jsonPath("$[2].courseId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].courseName", Matchers.equalTo(coursesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].credits", Matchers.equalTo(coursesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(3)[2])))
                                .andExpect(jsonPath("$[2].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(3)[3])[0])))
                                .andExpect(jsonPath("$[2].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(3)[3])[1])));
        }

        @Test
        @Order(12)
        public void testGetCourseNotFound() throws Exception {
                mockMvc.perform(get("/courses/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(13)
        public void testGetCourseById() throws Exception {
                mockMvc.perform(get("/courses/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.courseId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.courseName", Matchers.equalTo(coursesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.credits", Matchers.equalTo(coursesHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(1)[2])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(1)[3])[0])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(1)[3])[1])));

                mockMvc.perform(get("/courses/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.courseId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.courseName", Matchers.equalTo(coursesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.credits", Matchers.equalTo(coursesHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(2)[2])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(2)[3])[0])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(2)[3])[1])));

                mockMvc.perform(get("/courses/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.courseId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.courseName", Matchers.equalTo(coursesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.credits", Matchers.equalTo(coursesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(3)[2])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(3)[3])[0])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(3)[3])[1])));
        }

        @Test
        @Order(14)
        public void testPostCourse() throws Exception {
                String content = "{\n    \"courseName\": \"" + coursesHashMap.get(4)[0] + "\",\n    \"credits\": "
                                + coursesHashMap.get(4)[1] + ",\n    \"professor\": {\n        \"professorId\": "
                                + coursesHashMap.get(4)[2]
                                + "\n    },\n    \"students\": [\n        {\n            \"studentId\": "
                                + ((Integer[]) coursesHashMap.get(4)[3])[0]
                                + "\n        },\n        {\n            \"studentId\": "
                                + ((Integer[]) coursesHashMap.get(4)[3])[1] + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/courses")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.courseId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.courseName", Matchers.equalTo(coursesHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.credits", Matchers.equalTo(coursesHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(4)[2])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(4)[3])[0])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(4)[3])[1])));
        }

        @Test
        @Order(15)
        public void testAfterPostCourse() throws Exception {
                mockMvc.perform(get("/courses/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.courseId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.courseName", Matchers.equalTo(coursesHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.credits", Matchers.equalTo(coursesHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(4)[2])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(4)[3])[0])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(4)[3])[1])));
        }

        @Test
        @Order(16)
        @Transactional
        public void testDbAfterPostCourse() throws Exception {
                Course course = courseJpaRepository.findById(4).get();

                assertEquals(course.getCourseId(), 4);
                assertEquals(course.getCourseName(), coursesHashMap.get(4)[0]);
                assertEquals(course.getCredits(), coursesHashMap.get(4)[1]);
                assertEquals(course.getProfessor().getProfessorId(), coursesHashMap.get(4)[2]);
                assertEquals(course.getStudents().get(0).getStudentId(), ((Integer[]) coursesHashMap.get(4)[3])[0]);
                assertEquals(course.getStudents().get(1).getStudentId(), ((Integer[]) coursesHashMap.get(4)[3])[1]);

                Student student = studentJpaRepository.findById(((Integer[]) coursesHashMap.get(4)[3])[0]).get();

                int i;
                for (i = 0; i < student.getCourses().size(); i++) {
                        if (student.getCourses().get(i).getCourseId() == 4) {
                                break;
                        }
                }
                if (i == student.getCourses().size()) {
                        throw new AssertionError("Assertion Error: Student " + student.getStudentId()
                                        + " has no course with courseId 4");
                }

                student = studentJpaRepository.findById(((Integer[]) coursesHashMap.get(4)[3])[1]).get();

                for (i = 0; i < student.getCourses().size(); i++) {
                        if (student.getCourses().get(i).getCourseId() == 4) {
                                break;
                        }
                }
                if (i == student.getCourses().size()) {
                        throw new AssertionError("Assertion Error: Student " + student.getStudentId()
                                        + " has no course with courseId 4");
                }
        }

        @Test
        @Order(17)
        public void testPutCourseNotFound() throws Exception {
                String content = "{\n    \"courseName\": \"" + coursesHashMap.get(5)[0] + "\",\n    \"credits\": "
                                + coursesHashMap.get(5)[1] + ",\n    \"professor\": {\n        \"professorId\": "
                                + coursesHashMap.get(5)[2]
                                + "\n    },\n    \"students\": [\n        {\n            \"studentId\": "
                                + ((Integer[]) coursesHashMap.get(5)[3])[0]
                                + "\n        },\n        {\n            \"studentId\": "
                                + ((Integer[]) coursesHashMap.get(5)[3])[1] + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/courses/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(18)
        public void testPutCourse() throws Exception {
                String content = "{\n    \"courseName\": \"" + coursesHashMap.get(5)[0] + "\",\n    \"credits\": "
                                + coursesHashMap.get(5)[1] + ",\n    \"professor\": {\n        \"professorId\": "
                                + coursesHashMap.get(5)[2]
                                + "\n    },\n    \"students\": [\n        {\n            \"studentId\": "
                                + ((Integer[]) coursesHashMap.get(5)[3])[0]
                                + "\n        },\n        {\n            \"studentId\": "
                                + ((Integer[]) coursesHashMap.get(5)[3])[1] + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/courses/4")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.courseId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.courseName", Matchers.equalTo(coursesHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.credits", Matchers.equalTo(coursesHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(5)[2])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(5)[3])[0])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(5)[3])[1])));
        }

        @Test
        @Order(19)
        public void testAfterPutCourse() throws Exception {
                mockMvc.perform(get("/courses/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.courseId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.courseName", Matchers.equalTo(coursesHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.credits", Matchers.equalTo(coursesHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(5)[2])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(5)[3])[0])))
                                .andExpect(jsonPath("$.students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(5)[3])[1])));
        }

        @Test
        @Order(20)
        @Transactional
        public void testDbAfterPutCourse() throws Exception {
                Course course = courseJpaRepository.findById(4).get();

                assertEquals(course.getCourseId(), 4);
                assertEquals(course.getCourseName(), coursesHashMap.get(5)[0]);
                assertEquals(course.getCredits(), coursesHashMap.get(5)[1]);
                assertEquals(course.getProfessor().getProfessorId(), coursesHashMap.get(5)[2]);
                assertEquals(course.getStudents().get(0).getStudentId(), ((Integer[]) coursesHashMap.get(5)[3])[0]);
                assertEquals(course.getStudents().get(1).getStudentId(), ((Integer[]) coursesHashMap.get(5)[3])[1]);

                Student student = studentJpaRepository.findById(((Integer[]) coursesHashMap.get(5)[3])[0]).get();

                int i;
                for (i = 0; i < student.getCourses().size(); i++) {
                        if (student.getCourses().get(i).getCourseId() == 4) {
                                break;
                        }
                }
                if (i == student.getCourses().size()) {
                        throw new AssertionError("Assertion Error: Student " + student.getStudentId()
                                        + " has no course with courseId 4");
                }

                student = studentJpaRepository.findById(((Integer[]) coursesHashMap.get(5)[3])[1]).get();

                for (i = 0; i < student.getCourses().size(); i++) {
                        if (student.getCourses().get(i).getCourseId() == 4) {
                                break;
                        }
                }
                if (i == student.getCourses().size()) {
                        throw new AssertionError("Assertion Error: Student " + student.getStudentId()
                                        + " has no course with courseId 4");
                }
        }

        @Test
        @Order(21)
        public void testGetStudents() throws Exception {
                mockMvc.perform(get("/students")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].studentId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].studentName", Matchers.equalTo(studentsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].email", Matchers.equalTo(studentsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[0].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(1)[2])[1])))
                                .andExpect(jsonPath("$[0].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(1)[2])[2])))

                                .andExpect(jsonPath("$[1].studentId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].studentName", Matchers.equalTo(studentsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].email", Matchers.equalTo(studentsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[1].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(2)[2])[1])))

                                .andExpect(jsonPath("$[2].studentId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].studentName", Matchers.equalTo(studentsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].email", Matchers.equalTo(studentsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[2].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(3)[2])[1])))
                                .andExpect(jsonPath("$[2].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(3)[2])[2])));
        }

        @Test
        @Order(22)
        public void testGetStudentNotFound() throws Exception {
                mockMvc.perform(get("/students/48")).andExpect(status().isNotFound());
        }

        @Test
        @Order(23)
        public void testGetStudentById() throws Exception {
                mockMvc.perform(get("/students/1")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.studentId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$.studentName", Matchers.equalTo(studentsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$.email", Matchers.equalTo(studentsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(1)[2])[1])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(1)[2])[2])));

                mockMvc.perform(get("/students/2")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.studentId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$.studentName", Matchers.equalTo(studentsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$.email", Matchers.equalTo(studentsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(2)[2])[1])));

                mockMvc.perform(get("/students/3")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.studentId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$.studentName", Matchers.equalTo(studentsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$.email", Matchers.equalTo(studentsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(3)[2])[1])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(3)[2])[2])));
        }

        @Test
        @Order(24)
        public void testPostStudentBadRequest() throws Exception {
                String content = "{\n    \"studentName\": \"" + studentsHashMap.get(4)[0] + "\",\n    \"email\": \""
                                + studentsHashMap.get(4)[1]
                                + "\",\n    \"courses\": [\n        {\n            \"courseId\": "
                                + ((Integer[]) studentsHashMap.get(4)[2])[0]
                                + "\n        },\n        {\n            \"courseId\": "
                                + 148 + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/students")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
        }

        @Test
        @Order(25)
        public void testPostStudent() throws Exception {
                String content = "{\n    \"studentName\": \"" + studentsHashMap.get(4)[0] + "\",\n    \"email\": \""
                                + studentsHashMap.get(4)[1]
                                + "\",\n    \"courses\": [\n        {\n            \"courseId\": "
                                + ((Integer[]) studentsHashMap.get(4)[2])[0]
                                + "\n        },\n        {\n            \"courseId\": "
                                + ((Integer[]) studentsHashMap.get(4)[2])[1] + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/students")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.studentId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.studentName", Matchers.equalTo(studentsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.email", Matchers.equalTo(studentsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(26)
        public void testAfterPostStudent() throws Exception {
                mockMvc.perform(get("/students/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.studentId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.studentName", Matchers.equalTo(studentsHashMap.get(4)[0])))
                                .andExpect(jsonPath("$.email", Matchers.equalTo(studentsHashMap.get(4)[1])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(4)[2])[0])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(4)[2])[1])));
        }

        @Test
        @Order(27)
        @Transactional
        public void testDbAfterPostStudent() throws Exception {
                Student student = studentJpaRepository.findById(4).get();

                assertEquals(student.getStudentId(), 4);
                assertEquals(student.getStudentName(), studentsHashMap.get(4)[0]);
                assertEquals(student.getEmail(), studentsHashMap.get(4)[1]);
                assertEquals(student.getCourses().get(0).getCourseId(), ((Integer[]) studentsHashMap.get(4)[2])[0]);
                assertEquals(student.getCourses().get(1).getCourseId(), ((Integer[]) studentsHashMap.get(4)[2])[1]);

                Course course = courseJpaRepository.findById(((Integer[]) studentsHashMap.get(4)[2])[0]).get();

                int i;
                for (i = 0; i < course.getStudents().size(); i++) {
                        if (course.getStudents().get(i).getStudentId() == 4) {
                                break;
                        }
                }
                if (i == course.getStudents().size()) {
                        throw new AssertionError("Assertion Error: Course " + course.getCourseId()
                                        + " has no student with professorId 4");
                }

                course = courseJpaRepository.findById(((Integer[]) studentsHashMap.get(4)[2])[1]).get();
                for (i = 0; i < course.getStudents().size(); i++) {
                        if (course.getStudents().get(i).getStudentId() == 4) {
                                break;
                        }
                }
                if (i == course.getStudents().size()) {
                        throw new AssertionError("Assertion Error: Course " + course.getCourseId()
                                        + " has no student with professorId 4");
                }
        }

        @Test
        @Order(28)
        public void testPutStudentNotFound() throws Exception {
                String content = "{\n    \"studentName\": \"" + studentsHashMap.get(5)[0] + "\",\n    \"email\": \""
                                + studentsHashMap.get(5)[1]
                                + "\",\n    \"courses\": [\n        {\n            \"courseId\": "
                                + ((Integer[]) studentsHashMap.get(5)[2])[0]
                                + "\n        },\n        {\n            \"courseId\": "
                                + ((Integer[]) studentsHashMap.get(5)[2])[1] + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/students/48")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(29)
        public void testPutStudentBadRequest() throws Exception {
                String content = "{\n    \"studentName\": \"" + studentsHashMap.get(5)[0] + "\",\n    \"email\": \""
                                + studentsHashMap.get(5)[1]
                                + "\",\n    \"courses\": [\n        {\n            \"courseId\": "
                                + ((Integer[]) studentsHashMap.get(5)[2])[0]
                                + "\n        },\n        {\n            \"courseId\": "
                                + 148 + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/students/4")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isBadRequest());
        }

        @Test
        @Order(30)
        public void testPutStudent() throws Exception {
                String content = "{\n    \"studentName\": \"" + studentsHashMap.get(5)[0] + "\",\n    \"email\": \""
                                + studentsHashMap.get(5)[1]
                                + "\",\n    \"courses\": [\n        {\n            \"courseId\": "
                                + ((Integer[]) studentsHashMap.get(5)[2])[0]
                                + "\n        },\n        {\n            \"courseId\": "
                                + ((Integer[]) studentsHashMap.get(5)[2])[1] + "\n        }\n    ]\n}";

                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/students/4")
                                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                                .content(content);

                mockMvc.perform(mockRequest).andExpect(status().isOk())
                                .andExpect(jsonPath("$.studentId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.studentName", Matchers.equalTo(studentsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.email", Matchers.equalTo(studentsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(5)[2])[0])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(5)[2])[1])));
        }

        @Test
        @Order(31)
        public void testAfterPutStudent() throws Exception {
                mockMvc.perform(get("/students/4")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.studentId", Matchers.equalTo(4)))
                                .andExpect(jsonPath("$.studentName", Matchers.equalTo(studentsHashMap.get(5)[0])))
                                .andExpect(jsonPath("$.email", Matchers.equalTo(studentsHashMap.get(5)[1])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(5)[2])[0])))
                                .andExpect(jsonPath("$.courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(5)[2])[1])));
        }

        @Test
        @Order(32)
        @Transactional
        public void testDbAfterPutStudent() throws Exception {
                Student student = studentJpaRepository.findById(4).get();

                assertEquals(student.getStudentId(), 4);
                assertEquals(student.getStudentName(), studentsHashMap.get(5)[0]);
                assertEquals(student.getEmail(), studentsHashMap.get(5)[1]);
                assertEquals(student.getCourses().get(0).getCourseId(), ((Integer[]) studentsHashMap.get(5)[2])[0]);
                assertEquals(student.getCourses().get(1).getCourseId(), ((Integer[]) studentsHashMap.get(5)[2])[1]);

                Course course = courseJpaRepository.findById(((Integer[]) studentsHashMap.get(5)[2])[0]).get();

                int i;
                for (i = 0; i < course.getStudents().size(); i++) {
                        if (course.getStudents().get(i).getStudentId() == 4) {
                                break;
                        }
                }
                if (i == course.getStudents().size()) {
                        throw new AssertionError("Assertion Error: Course " + course.getCourseId()
                                        + " has no student with professorId 4");
                }
                course = courseJpaRepository.findById(((Integer[]) studentsHashMap.get(5)[2])[1]).get();

                for (i = 0; i < course.getStudents().size(); i++) {
                        if (course.getStudents().get(i).getStudentId() == 4) {
                                break;
                        }
                }
                if (i == course.getStudents().size()) {
                        throw new AssertionError("Assertion Error: Course " + course.getCourseId()
                                        + " has no student with professorId 4");
                }
        }

        @Test
        @Order(33)
        public void testGetStudentCourses() throws Exception {
                mockMvc.perform(get("/students/1/courses")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(1)[2])[1])))
                                .andExpect(jsonPath("$[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(1)[2])[2])));

                mockMvc.perform(get("/students/2/courses")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(2)[2])[1])));

                mockMvc.perform(get("/students/3/courses")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(3)[2])[1])))
                                .andExpect(jsonPath("$[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(3)[2])[2])));

                mockMvc.perform(get("/students/4/courses")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(5)[2])[0])))
                                .andExpect(jsonPath("$[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(5)[2])[1])));
        }

        @Test
        @Order(34)
        public void testGetCourseStudents() throws Exception {
                mockMvc.perform(get("/courses/1/students")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(1)[3])[0])))
                                .andExpect(jsonPath("$[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(1)[3])[1])));

                mockMvc.perform(get("/courses/2/students")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(2)[3])[0])))
                                .andExpect(jsonPath("$[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(2)[3])[1])));

                mockMvc.perform(get("/courses/3/students")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(3)[3])[0])))
                                .andExpect(jsonPath("$[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(3)[3])[1])))
                                .andExpect(jsonPath("$[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(3)[3])[2])));

                mockMvc.perform(get("/courses/4/students")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(5)[3])[0])))
                                .andExpect(jsonPath("$[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(5)[3])[1])))
                                .andExpect(jsonPath("$[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(5)[3])[2])));
        }

        @Test
        @Order(35)
        public void testGetCourseProfessor() throws Exception {
                mockMvc.perform(get("/courses/1/professor")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.professorId",
                                                Matchers.equalTo(coursesHashMap.get(1)[2])));

                mockMvc.perform(get("/courses/2/professor")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.professorId",
                                                Matchers.equalTo(coursesHashMap.get(2)[2])));

                mockMvc.perform(get("/courses/3/professor")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.professorId",
                                                Matchers.equalTo(coursesHashMap.get(3)[2])));

                mockMvc.perform(get("/courses/4/professor")).andExpect(status().isOk())
                                .andExpect(jsonPath("$.professorId",
                                                Matchers.equalTo(coursesHashMap.get(5)[2])));
        }

        @Test
        @Order(36)
        public void testGetProfessorCourses() throws Exception {
                mockMvc.perform(get("/professors/1/courses")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].courseId", hasItem(1)));

                mockMvc.perform(get("/professors/2/courses")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].courseId", hasItem(2)));

                mockMvc.perform(get("/professors/3/courses")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].courseId", hasItem(3)));

                mockMvc.perform(get("/professors/4/courses")).andExpect(status().isOk())
                                .andExpect(jsonPath("$[*].courseId", hasItem(4)));
        }

        @Test
        @Order(37)
        public void testDeleteProfessorNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/professors/148")
                                .contentType(MediaType.APPLICATION_JSON);
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(38)
        public void testDeleteProfessor() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/professors/4")
                                .contentType(MediaType.APPLICATION_JSON);
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());

                Course course = courseJpaRepository.findById(4).get();
                assertEquals(course.getProfessor(), null);
        }

        @Test
        @Order(39)
        public void testAfterDeleteProfessor() throws Exception {
                mockMvc.perform(get("/professors")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].professorId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].professorName",
                                                Matchers.equalTo(professorsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].department", Matchers.equalTo(professorsHashMap.get(1)[1])))

                                .andExpect(jsonPath("$[1].professorId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].professorName",
                                                Matchers.equalTo(professorsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].department", Matchers.equalTo(professorsHashMap.get(2)[1])))

                                .andExpect(jsonPath("$[2].professorId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].professorName",
                                                Matchers.equalTo(professorsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].department", Matchers.equalTo(professorsHashMap.get(3)[1])));
        }

        @Test
        @Order(40)
        public void testDeleteStudentNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/students/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());

        }

        @Test
        @Order(41)
        @Transactional
        @Rollback(false)
        public void testDeleteStudent() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/students/4");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());

                Course course = courseJpaRepository.findById(((Integer[]) studentsHashMap.get(5)[2])[0]).get();

                for (Student student : course.getStudents()) {
                        if (student.getStudentId() == 4) {
                                throw new AssertionError("Assertion Error: Student 4 and Course " + course.getCourseId()
                                                + " are still linked");
                        }
                }

                course = courseJpaRepository.findById(((Integer[]) studentsHashMap.get(5)[2])[1]).get();

                for (Student student : course.getStudents()) {
                        if (student.getStudentId() == 4) {
                                throw new AssertionError("Assertion Error: Student 4 and Course " + course.getCourseId()
                                                + " are still linked");
                        }
                }
        }

        @Test
        @Order(42)
        public void testAfterDeleteStudent() throws Exception {
                mockMvc.perform(get("/students")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].studentId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].studentName", Matchers.equalTo(studentsHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].email", Matchers.equalTo(studentsHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(1)[2])[0])))
                                .andExpect(jsonPath("$[0].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(1)[2])[1])))

                                .andExpect(jsonPath("$[1].studentId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].studentName", Matchers.equalTo(studentsHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].email", Matchers.equalTo(studentsHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(2)[2])[0])))
                                .andExpect(jsonPath("$[1].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(2)[2])[1])))

                                .andExpect(jsonPath("$[2].studentId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].studentName", Matchers.equalTo(studentsHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].email", Matchers.equalTo(studentsHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(3)[2])[0])))
                                .andExpect(jsonPath("$[2].courses[*].courseId",
                                                hasItem(((Integer[]) studentsHashMap.get(3)[2])[1])));
        }

        @Test
        @Order(43)
        public void testDeleteCourseNotFound() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/courses/148");
                mockMvc.perform(mockRequest).andExpect(status().isNotFound());
        }

        @Test
        @Order(44)
        @Transactional
        @Rollback(false)
        public void testDeleteCourse() throws Exception {
                MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.delete("/courses/4");
                mockMvc.perform(mockRequest).andExpect(status().isNoContent());

                Student student = studentJpaRepository.findById(((Integer[]) coursesHashMap.get(5)[3])[0]).get();

                for (Course course : student.getCourses()) {
                        if (course.getCourseId() == 4) {
                                throw new AssertionError("Assertion Error: Course 4 and Student "
                                                + student.getStudentId() + " are still linked");
                        }
                }

                student = studentJpaRepository.findById(((Integer[]) coursesHashMap.get(5)[3])[1]).get();

                for (Course course : student.getCourses()) {
                        if (course.getCourseId() == 4) {
                                throw new AssertionError("Assertion Error: Course 4 and Student "
                                                + student.getStudentId() + " are still linked");
                        }
                }
        }

        @Test
        @Order(45)
        public void testAfterDeleteCourse() throws Exception {
                mockMvc.perform(get("/courses")).andExpect(status().isOk())
                                .andExpect(jsonPath("$", Matchers.hasSize(3)))

                                .andExpect(jsonPath("$[0].courseId", Matchers.equalTo(1)))
                                .andExpect(jsonPath("$[0].courseName", Matchers.equalTo(coursesHashMap.get(1)[0])))
                                .andExpect(jsonPath("$[0].credits", Matchers.equalTo(coursesHashMap.get(1)[1])))
                                .andExpect(jsonPath("$[0].professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(1)[2])))
                                .andExpect(jsonPath("$[0].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(1)[3])[0])))
                                .andExpect(jsonPath("$[0].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(1)[3])[1])))

                                .andExpect(jsonPath("$[1].courseId", Matchers.equalTo(2)))
                                .andExpect(jsonPath("$[1].courseName", Matchers.equalTo(coursesHashMap.get(2)[0])))
                                .andExpect(jsonPath("$[1].credits", Matchers.equalTo(coursesHashMap.get(2)[1])))
                                .andExpect(jsonPath("$[1].professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(2)[2])))
                                .andExpect(jsonPath("$[1].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(2)[3])[0])))
                                .andExpect(jsonPath("$[1].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(2)[3])[1])))

                                .andExpect(jsonPath("$[2].courseId", Matchers.equalTo(3)))
                                .andExpect(jsonPath("$[2].courseName", Matchers.equalTo(coursesHashMap.get(3)[0])))
                                .andExpect(jsonPath("$[2].credits", Matchers.equalTo(coursesHashMap.get(3)[1])))
                                .andExpect(jsonPath("$[2].professor.professorId",
                                                Matchers.equalTo(coursesHashMap.get(3)[2])))
                                .andExpect(jsonPath("$[2].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(3)[3])[0])))
                                .andExpect(jsonPath("$[2].students[*].studentId",
                                                hasItem(((Integer[]) coursesHashMap.get(3)[3])[1])));
        }

        @AfterAll
        public void cleanup() {
                jdbcTemplate.execute("drop table course_student");
                jdbcTemplate.execute("drop table student");
                jdbcTemplate.execute("drop table course");
                jdbcTemplate.execute("drop table professor");
        }
}