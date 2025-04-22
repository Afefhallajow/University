-- PostgreSQL Schema for Simple University System

-- 1. ENUM for semesters
CREATE TYPE semester_enum AS ENUM ('SPRING', 'FALL');

-- 2. Table for Teachers
CREATE TABLE teachers (
                         id SERIAL PRIMARY KEY,
                         name TEXT NOT NULL,
                         email TEXT NOT NULL UNIQUE,
                         extra JSONB
);

-- 3. Table for Students
CREATE TABLE students (
                         id SERIAL PRIMARY KEY,
                         name TEXT NOT NULL,
                         email TEXT NOT NULL UNIQUE,
                         extra JSONB
);

-- 4. Table for Courses
CREATE TABLE courses (
                        id SERIAL PRIMARY KEY,
                        code TEXT NOT NULL UNIQUE,
                        title TEXT NOT NULL,
                        tags TEXT[] DEFAULT ARRAY[]::TEXT[],       -- Use array for tags
                        metadata JSONB
);

-- 5. Table for Course Offerings
CREATE TABLE course_offerings (
                                 id SERIAL PRIMARY KEY,
                                 course_id INT NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
                                 teacher_id INT NOT NULL REFERENCES teachers(id) ON DELETE SET NULL,
                                 semester semester_enum NOT NULL,
                                 year INT NOT NULL,
                                 UNIQUE (course_id, semester, year)        -- One offering per course per semester/year
);

-- 6. Table for Enrollments
CREATE TABLE enrollments (
                            id SERIAL PRIMARY KEY,
                            offering_id INT NOT NULL REFERENCES course_offerings(id) ON DELETE CASCADE,
                            student_id INT NOT NULL REFERENCES students(id) ON DELETE CASCADE,
                            mark INT CHECK (mark BETWEEN 0 AND 100),  -- Exam mark between 0 and 100
                            UNIQUE (offering_id, student_id)          -- Avoid duplicate enrollment
);

-- 7. Constraints for business rules (to be enforced in service layer or triggers)
--    a) A teacher can teach at most 4 courses per semester
--    b) A student can enroll in at most 6 courses per semester
--    c) A student cannot enroll in a course they already passed (mark >= 60)

-- Notes:
-- Use application-level checks or database triggers for these complex constraints
