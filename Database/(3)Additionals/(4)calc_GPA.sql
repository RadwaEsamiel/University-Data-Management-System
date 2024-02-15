CREATE OR REPLACE PROCEDURE calculate_all_students_gpa
IS
BEGIN
  FOR student_rec IN (SELECT DISTINCT STUDENT_ID FROM Grades)
  LOOP
    GPA_calculator(student_rec.STUDENT_ID);
  END LOOP;
END calculate_all_students_gpa;
/


BEGIN
  calculate_all_students_gpa;
END;
/
