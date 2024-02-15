

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



