
CREATE OR REPLACE PROCEDURE SET_RESULT_STATUS
IS
  CURSOR result_cursor IS
    SELECT * FROM grades;
BEGIN
  FOR v_student_record IN result_cursor LOOP

    UPDATE grades
    SET RESULT_STATUS = 
      CASE
        WHEN v_student_record.STUDENT_GRADE IS NULL THEN 'N'
        WHEN v_student_record.STUDENT_GRADE < v_student_record.SUCCESS_GRADE THEN 'F'
        WHEN v_student_record.STUDENT_GRADE >= v_student_record.SUCCESS_GRADE THEN 'P'
      END
    WHERE STUDENT_ID = v_student_record.STUDENT_ID and COURSES_ID = v_student_record.COURSES_ID  ;

  END LOOP;
END;
/
/*<TOAD_FILE_CHUNK>*/

begin 
SET_RESULT_STATUS;
end;
/*<TOAD_FILE_CHUNK>*/


CREATE OR REPLACE PROCEDURE UPDATE_GRADES_points(
  p_STUDENT_ID IN NUMBER,
  p_COURSES_ID IN NUMBER
)
IS
  v_course_gpa VARCHAR2(20);
  v_result NUMBER;
BEGIN
  FOR v_student_record IN (SELECT * FROM grades WHERE STUDENT_ID = p_STUDENT_ID and COURSES_ID= p_COURSES_ID ) LOOP
    v_course_gpa :=
    
      CASE
        WHEN v_student_record.STUDENT_GRADE >= 90 THEN 'A+'
        WHEN v_student_record.STUDENT_GRADE >= 85 AND v_student_record.STUDENT_GRADE < 90 THEN 'A'
        WHEN v_student_record.STUDENT_GRADE >= 80 AND v_student_record.STUDENT_GRADE < 85 THEN 'B+'
        WHEN v_student_record.STUDENT_GRADE >= 75 AND v_student_record.STUDENT_GRADE < 80 THEN 'B'
        WHEN v_student_record.STUDENT_GRADE >= 70 AND v_student_record.STUDENT_GRADE < 75 THEN 'C+'
        WHEN v_student_record.STUDENT_GRADE >= 65 AND v_student_record.STUDENT_GRADE < 70 THEN 'C'
        WHEN v_student_record.STUDENT_GRADE >= 50 AND v_student_record.STUDENT_GRADE < 65 THEN 'D'
        WHEN v_student_record.STUDENT_GRADE < 50 THEN 'F'
        ELSE 'N'
      END;

   FOR v_coursegpa_record IN (SELECT * FROM courses WHERE COURSES_ID = p_COURSES_ID) LOOP
      IF v_coursegpa_record.credit_hours = 4 THEN 
        v_result :=
          CASE 
            WHEN v_course_gpa = 'A+' THEN 3.9
            WHEN v_course_gpa = 'A' THEN 3.3
            WHEN v_course_gpa = 'B+' THEN 3.0
            WHEN v_course_gpa = 'B' THEN 2.3
            WHEN v_course_gpa = 'C+' THEN 2.0
            WHEN v_course_gpa = 'C' THEN 1.5
            WHEN v_course_gpa = 'D' THEN 1.0
            WHEN v_course_gpa = 'F' THEN 0.0
            ELSE NULL 
          END;
          
          
    ELSIF v_coursegpa_record.credit_hours = 3 THEN 
        v_result :=
        
          CASE 
            WHEN v_course_gpa = 'A+' THEN 2.9
            WHEN v_course_gpa = 'A' THEN 2.5
            WHEN v_course_gpa = 'B+' THEN 2.2
            WHEN v_course_gpa = 'B' THEN 1.9
            WHEN v_course_gpa = 'C+' THEN 1.5
            WHEN v_course_gpa = 'C' THEN 1.2
            WHEN v_course_gpa = 'D' THEN 1.0
            WHEN v_course_gpa = 'F' THEN 0.0
            ELSE NULL 
          END;
          
          
      ELSIF v_coursegpa_record.credit_hours = 2 THEN 
        v_result :=
          CASE 
            WHEN v_course_gpa = 'A+' THEN 1.9
            WHEN v_course_gpa = 'A' THEN 1.8
            WHEN v_course_gpa = 'B+' THEN 1.5
            WHEN v_course_gpa = 'B' THEN 1.2
            WHEN v_course_gpa = 'C+' THEN 1.0
            WHEN v_course_gpa = 'C' THEN 0.9
            WHEN v_course_gpa = 'D' THEN 0.5
            WHEN v_course_gpa = 'F' THEN 0.0
            ELSE NULL 
          END;
          
     ELSE
        v_result := NULL;
      END IF;

 UPDATE Grades 
      SET Grade_Point = v_result
      WHERE STUDENT_ID = p_STUDENT_ID 
        AND COURSES_ID = p_COURSES_ID;




    END LOOP;
  END LOOP;
END;
/
