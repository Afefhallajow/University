CREATE TYPE semester_enum AS ENUM ('SPRING', 'FALL');

CREATE TABLE students (
                          id SERIAL PRIMARY KEY,
                          name TEXT NOT NULL,
                          email TEXT UNIQUE NOT NULL,
                          extra JSONB DEFAULT '{}'::JSONB,
                          created_at TIMESTAMPTZ DEFAULT NOW(),
                          updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE teachers (
                          id SERIAL PRIMARY KEY,
                          name TEXT NOT NULL,
                          email TEXT UNIQUE NOT NULL,
                          extra JSONB DEFAULT '{}'::JSONB,
                          created_at TIMESTAMPTZ DEFAULT NOW(),
                          updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE courses (
                         id SERIAL PRIMARY KEY,
                         code VARCHAR(50) UNIQUE NOT NULL,
                         title TEXT NOT NULL,
                         metadata JSONB,
                         created_at TIMESTAMPTZ DEFAULT NOW(),
                         updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE course_tags (
                             course_id INT REFERENCES courses(id) ON DELETE CASCADE,
                             tag TEXT
);

CREATE TABLE course_offerings (
                                  id SERIAL PRIMARY KEY,
                                  course_id INT NOT NULL REFERENCES courses(id) ON DELETE CASCADE,
                                  teacher_id INT NOT NULL REFERENCES teachers(id) ON DELETE CASCADE,
                                  year INT NOT NULL,
                                  semester semester_enum NOT NULL,
                                  created_at TIMESTAMPTZ DEFAULT NOW(),
                                  updated_at TIMESTAMPTZ DEFAULT NOW()
);

CREATE TABLE enrollments (
                             id SERIAL PRIMARY KEY,
                             student_id INT NOT NULL REFERENCES students(id) ON DELETE CASCADE,
                             offering_id INT NOT NULL REFERENCES course_offerings(id) ON DELETE CASCADE,
                             mark INT,
                             created_at TIMESTAMPTZ DEFAULT NOW(),
                             updated_at TIMESTAMPTZ DEFAULT NOW()
);
