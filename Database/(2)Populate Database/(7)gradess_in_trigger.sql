CREATE OR REPLACE TRIGGER gradess_in_trigger
AFTER INSERT ON Registration
FOR EACH ROW
DECLARE
  v_success_grade NUMBER;
BEGIN

  SELECT Success_Grade
  INTO v_success_grade
  FROM courses
  WHERE courses_ID = :new.courses_ID;

  INSERT INTO Grades (courses_ID, Department_ID, STUDENT_ID, Success_Grade)
  VALUES (:new.courses_ID, :new.Department_ID, :new.STUDENT_ID, v_success_grade);
END gradess_in_trigger;
/
