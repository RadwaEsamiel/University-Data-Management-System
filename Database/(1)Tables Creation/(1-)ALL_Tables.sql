
CREATE TABLE UNI_Departments
(
  Department_ID NUMBER NOT NULL,
  Depart_name VARCHAR2(50) NOT NULL,
  required_credits NUMBER,
  students_number NUMBER,
  PRIMARY KEY (Department_ID),
  UNIQUE (Depart_name)
);

CREATE TABLE Departments_Location
(
  building_location VARCHAR2(255),
  Department_ID NUMBER NOT NULL,
  PRIMARY KEY (building_location, Department_ID),
CONSTRAINT fk_Departments_Locationl
FOREIGN KEY (Department_ID) REFERENCES UNI_Departments(Department_ID) ON DELETE cascade
);


CREATE TABLE students
(
  student_ID NUMBER NOT NULL,
  First_name VARCHAR2(50) NOT NULL,
  Last_name VARCHAR2(50) NOT NULL,
  Gender VARCHAR2(10) NOT NULL,
  Department_ID NUMBER,
  Date_of_birth DATE NOT NULL,
  AGE number,
  Address VARCHAR2(255) NOT NULL,
  PRIMARY KEY (student_ID),
  CONSTRAINT fk_students_Dep
  FOREIGN KEY (Department_ID) REFERENCES UNI_Departments(Department_ID) ON DELETE SET NULL
);

CREATE TABLE students_Email
(
  Email VARCHAR2(255) NOT NULL,
  student_ID NUMBER NOT NULL,
  PRIMARY KEY (Email, student_ID),
CONSTRAINT fk_students_Email_students
FOREIGN KEY (student_ID) REFERENCES students(student_ID) ON DELETE CASCADE
);






CREATE TABLE courses
(
  courses_ID NUMBER NOT NULL,
  course_name VARCHAR2(50) NOT NULL,
  Success_Grade NUMBER NOT NULL,
    credit_hours NUMBER NOT NULL,
  PRIMARY KEY (courses_ID)
);

CREATE TABLE Registration
(
  courses_ID NUMBER NOT NULL,
  Department_ID NUMBER NOT NULL,
    STUDENT_ID NUMBER NOT NULL,
   schedule VARCHAR2(255) NULL,
  PRIMARY KEY (courses_ID, Department_ID,STUDENT_ID),
  
 CONSTRAINT fk_Dep_courses
      FOREIGN KEY (courses_ID) REFERENCES courses(courses_ID) ON DELETE CASCADE,
       CONSTRAINT fk_Dep_cour
   FOREIGN KEY (Department_ID) REFERENCES UNI_Departments(Department_ID) ON DELETE CASCADE,
    CONSTRAINT fk_students_courses
      FOREIGN KEY (STUDENT_ID) REFERENCES students(STUDENT_ID) ON DELETE CASCADE
);

CREATE TABLE Departments_courses
(
  Department_ID NUMBER NOT NULL,
  courses_ID NUMBER NOT NULL,
  schedule VARCHAR2(255),
  PRIMARY KEY (courses_ID, Department_ID),
  CONSTRAINT fk_Dep_co
    FOREIGN KEY (courses_ID) REFERENCES courses(courses_ID) ON DELETE CASCADE,
  CONSTRAINT fk_Depuni
    FOREIGN KEY (Department_ID) REFERENCES UNI_Departments(Department_ID) ON DELETE CASCADE
);


CREATE TABLE Grades
(
  courses_ID NUMBER NOT NULL,
  Department_ID NUMBER NOT NULL,
    STUDENT_ID NUMBER NOT NULL,
    student_Grade NUMBER,
    Success_Grade NUMBER,
    Grade_Point NUMBER,
     result_status char(1),
      PRIMARY KEY (courses_ID, Department_ID,STUDENT_ID),
  
 CONSTRAINT fk_grade_course
      FOREIGN KEY (courses_ID) REFERENCES courses(courses_ID) ON DELETE CASCADE,
       CONSTRAINT fk_grade_dep
   FOREIGN KEY (Department_ID) REFERENCES UNI_Departments(Department_ID) ON DELETE CASCADE,
    CONSTRAINT fk_grade_student
      FOREIGN KEY (STUDENT_ID) REFERENCES students(STUDENT_ID) ON DELETE CASCADE
      );
      
      

CREATE TABLE GPA
(
  Department_ID NUMBER NOT NULL,
    STUDENT_ID NUMBER NOT NULL,
    required_credits NUMBER,
    student_GPA NUMBER,
    registered_hours NUMBER,
     final_result char(1),
      PRIMARY KEY (STUDENT_ID),
       CONSTRAINT  fk_gpa_dep
   FOREIGN KEY (Department_ID) REFERENCES UNI_Departments(Department_ID) ON DELETE CASCADE,
    CONSTRAINT fk_gpa_student
      FOREIGN KEY (STUDENT_ID) REFERENCES students(STUDENT_ID) ON DELETE CASCADE
      );