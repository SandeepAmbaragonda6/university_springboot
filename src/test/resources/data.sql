INSERT INTO professor (name, department)
SELECT 'John Smith', 'Computer Science'
WHERE NOT EXISTS (SELECT 1 FROM professor WHERE id = 1);

INSERT INTO professor (name, department)
SELECT 'Mary Johnson', 'Physics'
WHERE NOT EXISTS (SELECT 2 FROM professor WHERE id = 2);

INSERT INTO professor (name, department)
SELECT 'David Lee', 'Mathematics'
WHERE NOT EXISTS (SELECT 3 FROM professor WHERE id = 3);

INSERT INTO student (name, email)
SELECT 'Alice Johnson', 'alice@example.com'
WHERE NOT EXISTS (SELECT 1 FROM student WHERE id = 1);

INSERT INTO student (name, email)
SELECT 'Bob Davis', 'bob@example.com'
WHERE NOT EXISTS (SELECT 2 FROM student WHERE id = 2);

INSERT INTO student (name, email)
SELECT 'Eva Wilson', 'eva@example.com'
WHERE NOT EXISTS (SELECT 3 FROM student WHERE id = 3);

INSERT INTO course (name, credits, professorId)
SELECT 'Introduction to Programming', 3, 1
WHERE NOT EXISTS (SELECT 1 FROM course WHERE id = 1);

INSERT INTO course (name, credits, professorId)
SELECT 'Quantum Mechanics', 4, 2
WHERE NOT EXISTS (SELECT 2 FROM course WHERE id = 2);

INSERT INTO course (name, credits, professorId)
SELECT 'Calculus', 4, 3
WHERE NOT EXISTS (SELECT 3 FROM course WHERE id = 3);

INSERT INTO course_student (courseId, studentId)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM course_student WHERE courseId = 1 AND studentId = 1);

INSERT INTO course_student (courseId, studentId)
SELECT 1, 2
WHERE NOT EXISTS (SELECT 2 FROM course_student WHERE courseId = 1 AND studentId = 2);

INSERT INTO course_student (courseId, studentId)
SELECT 2, 2
WHERE NOT EXISTS (SELECT 3 FROM course_student WHERE courseId = 2 AND studentId = 2);

INSERT INTO course_student (courseId, studentId)
SELECT 2, 3
WHERE NOT EXISTS (SELECT 4 FROM course_student WHERE courseId = 2 AND studentId = 3);

INSERT INTO course_student (courseId, studentId)
SELECT 3, 1
WHERE NOT EXISTS (SELECT 5 FROM course_student WHERE courseId = 3 AND studentId = 1);

INSERT INTO course_student (courseId, studentId)
SELECT 3, 3
WHERE NOT EXISTS (SELECT 6 FROM course_student WHERE courseId = 3 AND studentId = 3);