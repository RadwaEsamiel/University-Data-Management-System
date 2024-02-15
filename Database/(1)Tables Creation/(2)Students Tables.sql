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






