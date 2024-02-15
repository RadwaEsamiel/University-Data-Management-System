

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
