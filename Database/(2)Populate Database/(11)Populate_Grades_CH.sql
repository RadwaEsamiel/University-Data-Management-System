DECLARE
  v_student_id NUMBER;
  v_course_id NUMBER;
BEGIN
  FOR rec IN (SELECT STUDENT_ID, COURSES_ID FROM grades) LOOP
    v_student_id := rec.STUDENT_ID;
    v_course_id := rec.COURSES_ID;
    UPDATE_GRADES_points(v_student_id, v_course_id);
  END LOOP;
END;
/
