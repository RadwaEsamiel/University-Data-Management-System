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


