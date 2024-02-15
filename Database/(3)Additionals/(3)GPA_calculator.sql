CREATE OR REPLACE PROCEDURE GPA_calculator(v_STUDENT_ID IN NUMBER)
IS
  v_student_gpa NUMBER;
  v_total_grade_points NUMBER := 0;
  v_total_credit_hours NUMBER := 0;
BEGIN

  FOR v_finalgpa_record IN (
    SELECT SUM(g.Grade_Point * c.CREDIT_HOURS) AS total_weighted_grade_points, SUM(c.CREDIT_HOURS) AS total_credit_hours
    FROM grades g
    JOIN courses c ON g.COURSES_ID = c.COURSES_ID
    WHERE g.STUDENT_ID = v_STUDENT_ID
  )
  LOOP
    v_total_grade_points := v_finalgpa_record.total_weighted_grade_points;
    v_total_credit_hours := v_finalgpa_record.total_credit_hours;
  END LOOP;


  IF v_total_credit_hours <> 0 THEN
    v_student_gpa := ROUND(v_total_grade_points / v_total_credit_hours, 2);

  
    UPDATE GPA 
    SET student_GPA = v_student_gpa 
    WHERE student_ID = v_STUDENT_ID;

    DBMS_OUTPUT.PUT_LINE('Student ID: ' || v_STUDENT_ID || ', Final GPA: ' || v_student_gpa);
  ELSE
    DBMS_OUTPUT.PUT_LINE('Student ID: ' || v_STUDENT_ID || ', Final GPA cannot be calculated due to zero credit hours.');
  END IF;
END;
/
