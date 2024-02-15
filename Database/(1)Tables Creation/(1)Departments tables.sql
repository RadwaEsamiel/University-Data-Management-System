
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



