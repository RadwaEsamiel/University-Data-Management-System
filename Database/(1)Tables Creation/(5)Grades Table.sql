/*<TOAD_FILE_CHUNK>*/
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
      
      
