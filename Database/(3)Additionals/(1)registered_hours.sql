CREATE OR REPLACE PROCEDURE students_totregistered_hours(
  p_student_id IN NUMBER,
  p_department_id IN NUMBER
)
IS
  v_total_reg_hours NUMBER;
BEGIN
  SELECT NVL(SUM(C.CREDIT_HOURS), 0)
  INTO v_total_reg_hours
  FROM Registration R
  JOIN Courses C ON R.COURSES_ID = C.COURSES_ID
  WHERE R.STUDENT_ID = p_student_id AND R.DEPARTMENT_ID = p_department_id;

  UPDATE GPA
  SET REGISTERED_HOURS = v_total_reg_hours
  WHERE STUDENT_ID = p_student_id AND DEPARTMENT_ID = p_department_id;
END students_totregistered_hours;
/

