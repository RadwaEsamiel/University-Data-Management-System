CREATE OR REPLACE PROCEDURE calculate_all_students_Fresult 
IS
BEGIN
  FOR student_rec IN (SELECT DISTINCT STUDENT_ID FROM Grades)
  LOOP
    students_Final_result(student_rec.STUDENT_ID);
  END LOOP;
END calculate_all_students_Fresult;
/


BEGIN
  calculate_all_students_Fresult;
END;
/
