**University Management System**

---

## Overview

This project implements a **University Management System** for administrative staff to manage students, teachers, courses, semesters, and grades. It provides a PostgreSQL database, JPA/Hibernate persistence, and a GraphQL API (via GraphQL Java Kickstart) to perform all CRUD operations and reporting features without requiring user login.

**Key features:**
- Local PostgreSQL database with primary/foreign keys, arrays, and enums
- JPA entities and repositories linking to the database
- GraphQL schema with Query and Mutation resolvers
- GraphiQL playground for interactive API exploration
- Testing via Postman or Insomnia

---

## Project Requirements

1. **Database Design** (PostgreSQL)
   - Use **PK**, **FK**, **Array**, **Enum** types
   - Entities: `Student`, `Teacher`, `Course`, `Semester`, `Enrollment`, etc.
   - Constraints:
     - 2 semesters per year (`SPRING`, `FALL`)
     - Each course taught by one teacher per semester
     - Max 4 courses per teacher per semester
     - Max 6 enrollments per student per semester
     - Passing grade ≥ 60%; cannot re‐enroll in passed courses

2. **Server Layer**
   - JPA/Hibernate entities and Spring Data Repositories
   - Transactional services handling business logic and constraints

3. **GraphQL API**
   - Define schema (`.graphqls` files) for Queries and Mutations
   - Implement resolvers using **com.graphql-java-kickstart**
   - Expose API at `/graphql` with GraphiQL UI at `/graphiql`

4. **Testing**
   - Verify all operations with Postman or Insomnia
   - Example test cases for each CRUD and report function

5. **System Functions**
   1. Manage students, teachers, courses (add, update, delete, list)
   2. Assign teachers to courses per semester
   3. Enroll students in courses and record exam marks
   4. Retrieve detailed student report (marks, GPA, remaining courses)
   5. List courses by teacher (filter by year and/or semester)
   6. List students in a course (filter by semester)

---

## Package Structure

```
com.university
├── config            # Database and GraphQL configuration
├── entity            # JPA entities: Student, Teacher, Course, Semester, Enrollment
├── repository        # Spring Data JPA repositories
├── service           # Business logic and constraint validation
├── graphql
│   ├── schema        # .graphqls files (Query, Mutation types)
│   └── resolver      # GraphQL resolvers for Queries & Mutations
└── UniversityApplication.java  # Main Spring Boot entry point
```

Each package encapsulates a specific layer:
- **entity**: maps tables and relations
- **repository**: CRUD and custom queries
- **service**: enforce rules (max courses, grade checks)
- **graphql.resolver**: expose data via GraphQL

---

## Getting Started

### Prerequisites
- **Java 17+**
- **Maven 3.6+**
- **PostgreSQL 14+**
- **GraphiQL** (bundled via dependency)

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/Afefhallajow/University.git
   cd University
   ```
2. Configure PostgreSQL in `application.yml`:
   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/university
       username: <your_user>
       password: <your_password>
   ```
3. Initialize database schema (Hibernate `ddl-auto: update` by default)
4. Build and run:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```
5. Access GraphiQL at: `http://localhost:8080/graphiql`

---

## Dependencies (BOM.xml)

| Dependency                                 | Version | Purpose                                                                 |
|--------------------------------------------|---------|-------------------------------------------------------------------------|
| Spring Boot Starter Data JPA                | 2.7.x   | Simplifies JPA configuration and repository support                     |
| Hibernate Core                              | 5.6.x   | ORM provider for entity mapping                                         |
| PostgreSQL Driver                           | 42.3.x  | JDBC driver for PostgreSQL                                              |
| GraphQL Java Kickstart                      | 12.x    | GraphQL servlet, schema parsing, GraphiQL UI                            |
| GraphQL Java Tools                          | 11.x    | Annotation-based schema generation                                      |
| Lombok                                     | 1.18.x  | Reduces boilerplate (getters/setters, constructors)                     |
| Spring Boot Starter Web                      | 2.7.x   | Embedded Tomcat and REST support (for GraphiQL UI)                      |

> **Note:** Versions align with Spring Boot 2.7.x BOM for compatibility.

---

## GraphQL API Reference

The GraphQL schema for this project includes the following type definitions, queries, and mutations:

```graphql
scalar JSON


### Queries

| Query Name                         | Arguments                                       | Description                                               |
|------------------------------------|--------------------------------------------------|-----------------------------------------------------------|
| `allEnrollments`                   | —                                                | List all enrollment records                              |
| `allCourseOfferings`              | `year?`, `semester?`                            | List course offerings, optionally filtered by term       |
| `getReport`                       | `studentId: ID!`                                 | Get detailed report: marks, GPA, remaining courses        |
| `allStudents`                     | `page: Int!`, `size: Int!`                       | Paginated list of students                               |
| `allTeachers`                     | `page: Int!`, `size: Int!`                       | Paginated list of teachers                               |
| `allCourses`                      | `page: Int!`, `size: Int!`                       | Paginated list of courses                                |
| `getCourseById`                   | `id: ID!`                                        | Retrieve a specific course                               |
| `getTeacherById`                  | `id: ID!`                                        | Retrieve a specific teacher                              |
| `getStudentById`                  | `id: ID!`                                        | Retrieve a specific student                              |
| `getOfferById`                    | `id: ID!`                                        | Retrieve a specific course offering                      |
| `getCoursesByTeacher`            | `teacherId: ID!`, `year?`, `semester?`           | Courses taught by a teacher                              |
| `studentsByCourse`               | `courseId: ID!`, `semester?`                     | Students enrolled in a course                            |

### Mutations

| Mutation Name                     | Input Type                                      | Description                                                           |
|----------------------------------|--------------------------------------------------|-----------------------------------------------------------------------|
| `enrollStudent`                  | `studentId: ID!`, `offeringId: ID!`              | Enroll a student in a course offering                                |
| `setMark`                        | `enrollmentId: ID!`, `mark: Int!`                | Record exam mark (must be ≥ 0 and ≤ 100; pass ≥ 60)                  |
| `addStudent`                     | `name: String!`, `email: String!`, `extra: JSON` | Create a new student                                                 |
| `updateStudent`                 | `id: ID!`, `name?`, `email?`, `extra?`           | Modify existing student                                              |
| `deleteStudent`                 | `id: ID!`                                        | Remove a student                                                     |
| `addTeacher`                    | `name: String!`, `email: String!`, `extra: JSON` | Create a new teacher                                                 |
| `updateTeacher`                | `id: ID!`, `name?`, `email?`, `extra?`           | Modify existing teacher                                              |
| `deleteTeacher`                | `id: ID!`                                        | Remove a teacher                                                     |
| `addCourse`                     | `code: String!`, `title: String!`, `tags?`, `metadata?` | Create a new course                                       |
| `updateCourse`                 | `id: ID!`, `code?`, `title?`, `tags?`, `metadata?` | Modify a course                                                |
| `deleteCourse`                 | `id: ID!`                                        | Remove a course                                                     |
| `addOffer`                      | `input: CourseOfferInput!`                      | Assign teacher to course offering                                    |
| `updateOffer`                 | `id: ID!`, `input: CourseOfferInput!`            | Modify course offering assignment                                    |
| `deleteOffer`                 | `id: ID!`                                        | Remove a course offering                                             |

## System Function to GraphQL Mapping

| Function | GraphQL Queries / Mutations |
|----------|------------------------------|
| 1. Manage students, teachers, courses (add, update, delete, list) | `addStudent`, `updateStudent`, `deleteStudent`, `allStudents`, `getStudentById`<br>`addTeacher`, `updateTeacher`, `deleteTeacher`, `allTeachers`, `getTeacherById`<br>`addCourse`, `updateCourse`, `deleteCourse`, `allCourses`, `getCourseById` |
| 2. Assign teachers to courses in semester | `addOffer`, `updateOffer`, `deleteOffer`, `allCourseOfferings`, `getOfferById` |
| 3. Enroll students in course & set exam mark | `enrollStudent`, `setMark`, `allEnrollments` |
| 4. Get student report (marks, GPA, remaining courses) | `getReport` |
| 5. Show teacher's courses by semester/year | `getCoursesByTeacher` |
| 6. Show students enrolled in a course by semester | `studentsByCourse` |

## Business Rules and Their Implementation

| Rule | Implementation Detail |
|------|------------------------|
| Two semesters per year (SPRING, FALL) | Defined via `SemesterEnum` and stored in the DB using PostgreSQL ENUMs. |
| Each course taught by one teacher per semester | Enforced by unique constraint on `course_id`, `semester`, and `year` in `CourseOffering`. |
| Max 4 courses per teacher per semester | Enforced in service layer before calling repository save in `CourseOfferingService`. |
| Max 6 courses per student per semester | Enforced in `EnrollmentService.enrollStudent` by counting student’s current enrollments in the given semester. |
| Cannot re-enroll passed course (>=60) | Checked in `EnrollmentService` before allowing new enrollment. |
| Mark must be between 0 and 100 | Validated in `setMark` mutation logic, with passing threshold of 60. |

### Database Constraint Details

- **SemesterEnum (SPRING, FALL)**: Defined as a PostgreSQL `ENUM` via Hibernate’s `@Enumerated(EnumType.STRING)` on `CourseOffering.semester`.
- **One teacher per course per semester**: Unique constraint on `course_id, semester, year` in `CourseOffering` table.
- **Max 4 courses per teacher per semester**: Enforced in `CourseOfferingService`.
- **Max 6 courses per student per semester**: Enforced in `EnrollmentService`.
- **Pass mark check (≥60)**: Enforced in `EnrollmentService` and via GraphQL input validation in `setMark()` resolver.
- **Cannot re-enroll in passed courses**: Checked in `enrollStudent()` before creating a new `Enrollment`.

