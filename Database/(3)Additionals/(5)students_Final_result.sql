CREATE OR REPLACE PROCEDURE students_Final_result(
  p_student_id IN NUMBER
)
IS
  v_final VARCHAR2(1);
BEGIN
  DECLARE
    v_result NUMBER;
  BEGIN
    SELECT COUNT(RESULT_STATUS)
    INTO v_result
    FROM grades
    WHERE STUDENT_ID = p_student_id 
      AND (RESULT_STATUS = 'F' OR RESULT_STATUS = 'N');
    
    IF v_result > 2 THEN 
      v_final := 'F';
    ELSE 
      v_final := 'P';
    END IF;
    
    UPDATE GPA
    SET Final_result = v_final
    WHERE STUDENT_ID = p_student_id;

  END;
END students_Final_result;
/
