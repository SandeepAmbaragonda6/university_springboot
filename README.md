In this project, we will build a Spring Boot application named 'University'. The 'University' platform is designed to connect professors, courses, and students. Users can easily navigate through the platform to discover which courses are offered by specific professors and which students are enrolled in a particular course.

The main entities to be considered for this application are `Professor`, `Course`, and `Student`. The `Course` entity has a Many-to-One relationship with the `Professor` indicating that each course is taught by a specific professor. Additionally, the `Course` entity shares a Many-to-Many relationship with the `Student`, showing that a course can have multiple enrolled students, and a student can be enrolled in multiple courses.

<details>
<summary>**Implementation Files**</summary>

Use these files to complete the implementation:

- `ProfessorController.java`
- `ProfessorRepository.java`
- `ProfessorJpaService.java`
- `ProfessorJpaRepository.java`
- `Professor.java`
- `CourseController.java`
- `CourseRepository.java`
- `CourseJpaService.java`
- `CourseJpaRepository.java`
- `Course.java`
- `StudentController.java`
- `StudentRepository.java`
- `StudentJpaService.java`
- `StudentJpaRepository.java`
- `Student.java`

</details>

Create a database that contains four tables `professor`, `student`, `course`, and `course_student` using the given database schema.

You can refer to this [session](https://learning.ccbp.in/course?c_id=e345dfa4-f5ce-406e-b19a-4ed720c54136&s_id=6a60610e-79c2-4e15-b675-45ddbd9bbe82&t_id=f880166e-2f51-4403-81a0-d2430694dae8), for creating a database.

_Create the SQL files and compose accurate queries to run the application. Inaccurate SQL files will result in test case failures._

<details>
<summary>**Database Schema**</summary>

#### Professor Table

|  Columns   |                 Type                  |
| :--------: | :-----------------------------------: |
|     id     | INTEGER (Primary Key, Auto Increment) |
|    name    |                 TEXT                  |
| department |                 TEXT                  |

#### Course Table

|   Columns   |                 Type                  |
| :---------: | :-----------------------------------: |
|     id      | INTEGER (Primary Key, Auto Increment) |
|    name     |                 TEXT                  |
|   credits   |                INTEGER                |
| professorId |         INTEGER (Foreign Key)         |

#### Student Table

| Columns |                 Type                  |
| :-----: | :-----------------------------------: |
|   id    | INTEGER (Primary Key, Auto Increment) |
|  name   |                 TEXT                  |
|  email  |                 TEXT                  |

#### Junction Table

|  Columns  |                Type                |
| :-------: | :--------------------------------: |
| studentId | INTEGER (Primary Key, Foreign Key) |
| courseId  | INTEGER (Primary Key, Foreign Key) |

You can use the given sample data to populate the tables.

<details>
<summary>**Sample Data**</summary>

#### Professor Data

|  id   |     name     |    department    |
| :---: | :----------: | :--------------: |
|   1   |  John Smith  | Computer Science |
|   2   | Mary Johnson |     Physics      |
|   3   |  David Lee   |   Mathematics    |

#### Course Data

|  id   |            name             | credits | professorId |
| :---: | :-------------------------: | :-----: | :---------: |
|   1   | Introduction to Programming |    3    |      1      |
|   2   |      Quantum Mechanics      |    4    |      2      |
|   3   |          Calculus           |    4    |      3      |

#### Student Data

|  id   |     name      |       email       |
| :---: | :-----------: | :---------------: |
|   1   | Alice Johnson | alice@example.com |
|   2   |   Bob Davis   |  bob@example.com  |
|   3   |  Eva Wilson   |  eva@example.com  |

#### Junction Table

| courseId | studentId |
| :------: | :-------: |
|    1     |     1     |
|    1     |     2     |
|    2     |     2     |
|    2     |     3     |
|    3     |     1     |
|    3     |     3     |

</details>

</details>

<MultiLineNote>

Use only `professor`, `student`, `course`, and `course_student` as the table names in your code.

</MultiLineNote>

### Completion Instructions

- `Professor.java`: The `Professor` class should contain the following attributes.

    |   Attribute   |  Type  |
    | :-----------: | :----: |
    |  professorId  |  int   |
    | professorName | String |
    |  department   | String |

- `ProfessorRepository.java`: Create an `interface` containing the required methods.
- `ProfessorJpaService.java`: Update the service class with logic for managing professor data.
- `ProfessorController.java`: Create the controller class to handle HTTP requests.
- `ProfessorJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.

- `Course.java`: The `Course` class should contain the following attributes.

    | Attribute  |      Type      |
    | :--------: | :------------: |
    |  courseId  |      int       |
    | courseName |     String     |
    |  credits   |     String     |
    | professor  |   Professor    |
    |  students  | List\<Student> |

- `CourseRepository.java`: Create an `interface` containing the required methods.
- `CourseJpaService.java`: Update the service class with logic for managing course data.
- `CourseController.java`: Create the controller class to handle HTTP requests.
- `CourseJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.

- `Student.java`: The `Student` class should contain the following attributes.

    |  Attribute  |     Type      |
    | :---------: | :-----------: |
    |  studentId  |      int      |
    | studentName |    String     |
    |    email    |    String     |
    |   courses   | List\<Course> |

- `StudentRepository.java`: Create an `interface` containing the required methods.
- `StudentJpaService.java`: Update the service class with logic for managing student data.
- `StudentController.java`: Create the controller class to handle HTTP requests.
- `StudentJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.

Implement the following APIs.

<details>
<summary>**API 1: GET /professors**</summary>

#### Path: `/professors`

#### Method: `GET`

#### Description:

Returns a list of all professors in the `professor` table.

#### Response

```json
[
    {
        "professorId": 1,
        "professorName": "John Smith",
        "department": "Computer Science"
    },
    ...
]
```

</details>

<details>
<summary>**API 2: POST /professors**</summary>

#### Path: `/professors`

#### Method: `POST`

#### Description:

Creates a new professor in the `professor` table. The `professorId` is auto-incremented.

#### Request

```json
{
    "professorName": "Mark Willam",
    "department": "Mathematics"
}
```

#### Response

```json
{
    "professorId": 4,
    "professorName": "Mark Willam",
    "department": "Mathematics"
}
```

</details>

<details>
<summary>**API 3: GET /professors/{professorId}**</summary>

#### Path: `/professors/{professorId}`

#### Method: `GET`

#### Description:

Returns a professor based on the `professorId`. If the given `professorId` is not found in the `professor` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.


#### Success Response

```json
{
    "professorId": 1,
    "professorName": "John Smith",
    "department": "Computer Science"
}
```

</details>

<details>
<summary>**API 4: PUT /professors/{professorId}**</summary>

#### Path: `/professors/{professorId}`

#### Method: `PUT`

#### Description:

Updates the details of a professor based on the `professorId` and returns the updated professor details. If the given `professorId` is not found in the `professor` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Request

```json
{
    "professorName": "Mark Williams"
}
```

#### Success Response

```json
{
    "professorId": 4,
    "professorName": "Mark Williams",
    "department": "Mathematics"
}
```

</details>

<details>
<summary>**API 5: DELETE /professors/{professorId}**</summary>

#### Path: `/professors/{professorId}`

#### Method: `DELETE`

#### Description:

Deletes a professor from the `professor` table based on the `professorId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). Also, remove the association between the professor and the courses by replacing the `professorId` in the `course` table with `null`.

If the given `professorId` is not found in the `professor` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Sample Course object when its corresponding professor is deleted

```json
{
    "courseId": 1,
    "courseName": "Introduction to Programming",
    "credits": 3,
    "professor": null
}
```

</details>

<details>
<summary>**API 6: GET /professors/{professorId}/courses**</summary>

#### Path: `/professors/{professorId}/courses`

#### Method: `GET`

#### Description:

Returns the courses of a professor based on the `professorId`. If the given `professorId` is not found in the `professor` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
[
    {
        "courseId": 1,
        "courseName": "Introduction to Programming",
        "credits": 3,
        "professor": {
            "professorId": 1,
            "professorName": "John Smith",
            "department": "Computer Science"
        },
        "students": [
            {
                "studentId": 1,
                "studentName": "Alice Johnson",
                "email": "alice@example.com"
            },
            {
                "studentId": 2,
                "studentName": "Bob Davis",
                "email": "bob@example.com"
            }
        ]
    }
]
```

</details>

<details>
<summary>**API 7: GET /courses**</summary>

#### Path: `/courses`

#### Method: `GET`

#### Description:

Returns a list of all courses in the `course` table.

#### Response

```json
[
    {
        "courseId": 1,
        "courseName": "Introduction to Programming",
        "credits": 3,
        "professor": {
            "professorId": 1,
            "professorName": "John Smith",
            "department": "Computer Science"
        },
        "students": [
            {
                "studentId": 1,
                "studentName": "Alice Johnson",
                "email": "alice@example.com"
            },
            {
                "studentId": 2,
                "studentName": "Bob Davis",
                "email": "bob@example.com"
            }
        ]
    },
    ...
]
```

</details>

<details>
<summary>**API 8: POST /courses**</summary>

#### Path: `/courses`

#### Method: `POST`

#### Description:

Creates a new course in the `course` table. Also, create an association between the course and students in the `course_student` table based on the `studentId`s provided in the `students` field and an association between the course and the professor based on the `professorId` of the `professor` field. The `courseId` is auto-incremented.

#### Request

```json
{
    "courseName": "Statistics",
    "credits": 5,
    "professor": {
        "professorId": 3
    },
    "students": [
        {
            "studentId": 2
        },
        {
            "studentId": 3
        }
    ]
}
```

#### Response

```json
{
    "courseId": 4,
    "courseName": "Statistics",
    "credits": 5,
    "professor": {
        "professorId": 3,
        "professorName": "David Lee",
        "department": "Mathematics"
    },
    "students": [
        {
            "studentId": 2,
            "studentName": "Bob Davis",
            "email": "bob@example.com"
        },
        {
            "studentId": 3,
            "studentName": "Eva Wilson",
            "email": "eva@example.com"
        }
    ]
}
```

</details>

<details>
<summary>**API 9: GET /courses/{courseId}**</summary>

#### Path: `/courses/{courseId}`

#### Method: `GET`

#### Description:

Returns a course based on the `courseId`. If the given `courseId` is not found in the `course` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
{
    "courseId": 1,
    "courseName": "Introduction to Programming",
    "credits": 3,
    "professor": {
        "professorId": 1,
        "professorName": "John Smith",
        "department": "Computer Science"
    },
    "students": [
        {
            "studentId": 1,
            "studentName": "Alice Johnson",
            "email": "alice@example.com"
        },
        {
            "studentId": 2,
            "studentName": "Bob Davis",
            "email": "bob@example.com"
        }
    ]
}
```

</details>

<details>
<summary>**API 10: PUT /courses/{courseId}**</summary>

#### Path: `/courses/{courseId}`

#### Method: `PUT`

#### Description:

Updates the details of a course based on the `courseId` and returns the updated course details. Also update the associations between the course and students, if the `students` field is provided and the association between the course and the professor based on the `professorId`, if the `professor` field is provided. If the given `courseId` is not found in the `course` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Request

```json
{
    "credits": 4,
    "professor": {
        "professorId": 4
    },
    "students": [
        {
            "studentId": 1
        },
        {
            "studentId": 3
        }
    ]
}
```

#### Success Response

```json
{
    "courseId": 4,
    "courseName": "Statistics",
    "credits": 4,
    "professor": {
        "professorId": 4,
        "professorName": "Mark Williams",
        "department": "Mathematics"
    },
    "students": [
        {
            "studentId": 1,
            "studentName": "Alice Johnson",
            "email": "alice@example.com"
        },
        {
            "studentId": 3,
            "studentName": "Eva Wilson",
            "email": "eva@example.com"
        }
    ]
}
```

</details>

<details>
<summary>**API 11: DELETE /courses/{courseId}**</summary>

#### Path: `/courses/{courseId}`

#### Method: `DELETE`

#### Description:

Deletes a course from the `course` table and its associations from the `course_student` table based on the `courseId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). If the given `courseId` is not found in the `course` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

</details>

<details>
<summary>**API 12: GET /courses/{courseId}/professor**</summary>

#### Path: `/courses/{courseId}/professor`

#### Method: `GET`

#### Description:

Returns a professor of the course based on the `courseId`. If the given `courseId` is not found in the `course` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
{
    "professorId": 1,
    "professorName": "John Smith",
    "department": "Computer Science"
}
```

</details>

<details>
<summary>**API 13: GET /courses/{courseId}/students**</summary>

#### Path: `/courses/{courseId}/students`

#### Method: `GET`

#### Description:

Returns all students associated with the course based on the `courseId`. If the given `courseId` is not found in the `course` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
[
    {
        "studentId": 1,
        "studentName": "Alice Johnson",
        "email": "alice@example.com",
        "courses": [
            {
                "courseId": 1,
                "courseName": "Introduction to Programming",
                "credits": 3,
                "professor": {
                    "professorId": 1,
                    "professorName": "John Smith",
                    "department": "Computer Science"
                }
            },
            {
                "courseId": 3,
                "courseName": "Calculus",
                "credits": 4,
                "professor": {
                    "professorId": 3,
                    "professorName": "David Lee",
                    "department": "Mathematics"
                }
            },
            {
                "courseId": 4,
                "courseName": "Statistics",
                "credits": 4,
                "professor": {
                    "professorId": 4,
                    "professorName": "Mark Williams",
                    "department": "Mathematics"
                }
            }
        ]
    },
    {
        "studentId": 2,
        "studentName": "Bob Davis",
        "email": "bob@example.com",
        "courses": [
            {
                "courseId": 1,
                "courseName": "Introduction to Programming",
                "credits": 3,
                "professor": {
                    "professorId": 1,
                    "professorName": "John Smith",
                    "department": "Computer Science"
                }
            },
            {
                "courseId": 2,
                "courseName": "Quantum Mechanics",
                "credits": 4,
                "professor": {
                    "professorId": 2,
                    "professorName": "Mary Johnson",
                    "department": "Physics"
                }
            }
        ]
    }
]
```

</details>

<details>
<summary>**API 14: GET /students**</summary>

#### Path: `/students`

#### Method: `GET`

#### Description:

Returns a list of all students in the `student` table.

#### Response

```json
[
    {
        "studentId": 1,
        "studentName": "Alice Johnson",
        "email": "alice@example.com",
        "courses": [
            {
                "courseId": 1,
                "courseName": "Introduction to Programming",
                "credits": 3,
                "professor": {
                    "professorId": 1,
                    "professorName": "John Smith",
                    "department": "Computer Science"
                }
            },
            {
                "courseId": 3,
                "courseName": "Calculus",
                "credits": 4,
                "professor": {
                    "professorId": 3,
                    "professorName": "David Lee",
                    "department": "Mathematics"
                }
            },
            {
                "courseId": 4,
                "courseName": "Statistics",
                "credits": 4,
                "professor": {
                    "professorId": 4,
                    "professorName": "Mark Williams",
                    "department": "Mathematics"
                }
            }
        ]
    },
    ...
]
```

</details>

<details>
<summary>**API 15: POST /students**</summary>

#### Path: `/students`

#### Method: `POST`

#### Description:

Creates a new student in the `student` table, if all the `courseId`s in the `courses` field exist in the `course` table. Also, create an association between the student and courses in the `course_student` table. The `studentId` is auto-incremented. If any given `courseId` is not found in the `course` table, raise `ResponseStatusException` with `HttpStatus.BAD_REQUEST`.

#### Request

```json
{
    "studentName": "Harley Hoies",
    "email": "harley@example.com",
    "courses": [
        {
            "courseId": 2
        },
        {
            "courseId": 4
        }
    ]
}
```

#### Success Response

```json
{
    "studentId": 4,
    "studentName": "Harley Hoies",
    "email": "harley@example.com",
    "courses": [
        {
            "courseId": 2,
            "courseName": "Quantum Mechanics",
            "credits": 4,
            "professor": {
                "professorId": 2,
                "professorName": "Mary Johnson",
                "department": "Physics"
            }
        },
        {
            "courseId": 4,
            "courseName": "Statistics",
            "credits": 4,
            "professor": {
                "professorId": 4,
                "professorName": "Mark Williams",
                "department": "Mathematics"
            }
        }
    ]
}
```

</details>

<details>
<summary>**API 16: GET /students/{studentId}**</summary>

#### Path: `/students/{studentId}`

#### Method: `GET`

#### Description:

Returns a student based on the `studentId`. If the given `studentId` is not found in the `student` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.


#### Success Response

```json
{
    "studentId": 1,
    "studentName": "Alice Johnson",
    "email": "alice@example.com",
    "courses": [
        {
            "courseId": 1,
            "courseName": "Introduction to Programming",
            "credits": 3,
            "professor": {
                "professorId": 1,
                "professorName": "John Smith",
                "department": "Computer Science"
            }
        },
        {
            "courseId": 3,
            "courseName": "Calculus",
            "credits": 4,
            "professor": {
                "professorId": 3,
                "professorName": "David Lee",
                "department": "Mathematics"
            }
        },
        {
            "courseId": 4,
            "courseName": "Statistics",
            "credits": 4,
            "professor": {
                "professorId": 4,
                "professorName": "Mark Williams",
                "department": "Mathematics"
            }
        }
    ]
}
```

</details>

<details>
<summary>**API 17: PUT /students/{studentId}**</summary>

#### Path: `/students/{studentId}`

#### Method: `PUT`

#### Description:

Updates the details of a student based on the `studentId` and returns the updated student details. Also update the associations between the student and courses, if the `courses` field is provided. If the given `studentId` is not found in the `student` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`. If any given `courseId` is not found in the `course` table, raise `ResponseStatusException` with `HttpStatus.BAD_REQUEST`.

#### Request

```json
{
    "studentName": "Harley Homes",
    "courses": [
        {
            "courseId": 3
        },
        {
            "courseId": 4
        }
    ]
}
```

#### Success Response

```json
{
    "studentId": 4,
    "studentName": "Harley Homes",
    "email": "harley@example.com",
    "courses": [
        {
            "courseId": 3,
            "courseName": "Calculus",
            "credits": 4,
            "professor": {
                "professorId": 3,
                "professorName": "David Lee",
                "department": "Mathematics"
            }
        },
        {
            "courseId": 4,
            "courseName": "Statistics",
            "credits": 4,
            "professor": {
                "professorId": 4,
                "professorName": "Mark Williams",
                "department": "Mathematics"
            }
        }
    ]
}
```

</details>

<details>
<summary>**API 18: DELETE /students/{studentId}**</summary>

#### Path: `/students/{studentId}`

#### Method: `DELETE`

#### Description:

Deletes a student from the `student` table and its associations from the `course_student` table based on the `studentId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). If the given `studentId` is not found in the `student` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

</details>

<details>
<summary>**API 19: GET /students/{studentId}/courses**</summary>

#### Path: `/students/{studentId}/courses`

#### Method: `GET`

#### Description:

Returns all courses associated with the student based on the `studentId`. If the given `studentId` is not found in the `student` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
[
    {
        "courseId": 1,
        "courseName": "Introduction to Programming",
        "credits": 3,
        "professor": {
            "professorId": 1,
            "professorName": "John Smith",
            "department": "Computer Science"
        },
        "students": [
            {
                "studentId": 1,
                "studentName": "Alice Johnson",
                "email": "alice@example.com"
            },
            {
                "studentId": 2,
                "studentName": "Bob Davis",
                "email": "bob@example.com"
            }
        ]
    },
    {
        "courseId": 4,
        "courseName": "Statistics",
        "credits": 4,
        "professor": {
            "professorId": 4,
            "professorName": "Mark Williams",
            "department": "Mathematics"
        },
        "students": [
            {
                "studentId": 1,
                "studentName": "Alice Johnson",
                "email": "alice@example.com"
            },
            {
                "studentId": 3,
                "studentName": "Eva Wilson",
                "email": "eva@example.com"
            },
            {
                "studentId": 4,
                "studentName": "Harley Homes",
                "email": "harley@example.com"
            }
        ]
    },
    {
        "courseId": 3,
        "courseName": "Calculus",
        "credits": 4,
        "professor": {
            "professorId": 3,
            "professorName": "David Lee",
            "department": "Mathematics"
        },
        "students": [
            {
                "studentId": 1,
                "studentName": "Alice Johnson",
                "email": "alice@example.com"
            },
            {
                "studentId": 3,
                "studentName": "Eva Wilson",
                "email": "eva@example.com"
            },
            {
                "studentId": 4,
                "studentName": "Harley Homes",
                "email": "harley@example.com"
            }
        ]
    }
]
```

</details>

**Do not modify the code in `UniversityApplication.java`**