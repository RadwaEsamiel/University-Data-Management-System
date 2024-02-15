
-- Triggers for the 'courses' table
CREATE OR REPLACE TRIGGER update_registration_schedule
AFTER UPDATE ON Departments_courses
FOR EACH ROW
BEGIN
  IF UPDATING('schedule') THEN
    UPDATE Registration
    SET schedule = :NEW.schedule
    WHERE Department_ID = :NEW.Department_ID
      AND courses_ID = :NEW.courses_ID;
  END IF;
END;
/


CREATE OR REPLACE TRIGGER courses_update_trigger
AFTER UPDATE ON courses
FOR EACH ROW
BEGIN
  UPDATE Registration
  SET courses_ID = :new.courses_ID
  WHERE courses_ID = :old.courses_ID;
END;
/

CREATE OR REPLACE TRIGGER courseschad_update_trigger
AFTER UPDATE ON Departments_courses
FOR EACH ROW
BEGIN
  UPDATE Registration
  SET SCHEDULE = :new.SCHEDULE
  WHERE courses_ID = :new.courses_ID
    AND department_ID = :new.department_ID;
END;
/

CREATE OR REPLACE TRIGGER trg_updateDepartments_Location
BEFORE UPDATE ON UNI_Departments
FOR EACH ROW
BEGIN
  UPDATE Departments_Location
  SET Department_ID = :new.Department_ID
  WHERE Department_ID = :old.Department_ID;
END;
/

-- Trigger for the 'UNI_Departments' table
CREATE OR REPLACE TRIGGER departments_update_trigger
AFTER UPDATE ON UNI_Departments
FOR EACH ROW
BEGIN
  UPDATE Registration
  SET Department_ID = :new.Department_ID
  WHERE Department_ID = :old.Department_ID;
END;
/

-- Trigger for the 'students' table
CREATE OR REPLACE TRIGGER students_update_trigger
AFTER UPDATE ON students
FOR EACH ROW
BEGIN
  UPDATE Registration
  SET STUDENT_ID = :new.STUDENT_ID
  WHERE STUDENT_ID = :old.STUDENT_ID;
END;
/



CREATE OR REPLACE TRIGGER trg_updateDepartments_Location
BEFORE UPDATE ON UNI_Departments
FOR EACH ROW
BEGIN
  UPDATE Departments_Location
  SET Department_ID = :new.Department_ID
  WHERE Department_ID = :old.Department_ID;
END;
/



CREATE OR REPLACE TRIGGER departmentsnum_trigger
FOR INSERT ON students
COMPOUND TRIGGER
  v_counts sys.odcinumberlist := sys.odcinumberlist();
  
  BEFORE STATEMENT IS
  BEGIN
    v_counts := sys.odcinumberlist();
  END BEFORE STATEMENT;

  AFTER EACH ROW IS
  BEGIN
    IF v_counts.exists(:NEW.DEPARTMENT_ID) THEN
      v_counts(:NEW.DEPARTMENT_ID) := v_counts(:NEW.DEPARTMENT_ID) + 1;
    ELSE
      v_counts.extend;
      v_counts(v_counts.last) := :NEW.DEPARTMENT_ID;
    END IF;
  END AFTER EACH ROW;

  AFTER STATEMENT IS
  BEGIN
    FOR i IN 1..v_counts.count LOOP
      UPDATE uni_departments
      SET students_number = (
        SELECT COUNT(STUDENT_ID)
        FROM students
        WHERE DEPARTMENT_ID = v_counts(i)
      )
      WHERE DEPARTMENT_ID = v_counts(i);

      DBMS_OUTPUT.PUT_LINE('Department ID: ' || v_counts(i) || ', Students Count: ' || v_counts(i));
    END LOOP;
  END AFTER STATEMENT;
END;
/



CREATE OR REPLACE TRIGGER update_studDEP_trigger
AFTER UPDATE OF DEPARTMENT_ID ON UNI_Departments
FOR EACH ROW
BEGIN
  UPDATE students
  SET DEPARTMENT_ID = :NEW.DEPARTMENT_ID
  WHERE DEPARTMENT_ID = :OLD.DEPARTMENT_ID;
END;
/



CREATE OR REPLACE TRIGGER departmentsnum_trigger
FOR INSERT ON students
COMPOUND TRIGGER
  v_counts sys.odcinumberlist := sys.odcinumberlist();
  
  BEFORE STATEMENT IS
  BEGIN
    v_counts := sys.odcinumberlist();
  END BEFORE STATEMENT;

  AFTER EACH ROW IS
  BEGIN
    IF v_counts.exists(:NEW.DEPARTMENT_ID) THEN
      v_counts(:NEW.DEPARTMENT_ID) := v_counts(:NEW.DEPARTMENT_ID) + 1;
    ELSE
      v_counts.extend;
      v_counts(v_counts.last) := :NEW.DEPARTMENT_ID;
    END IF;
  END AFTER EACH ROW;

  AFTER STATEMENT IS
  BEGIN
    FOR i IN 1..v_counts.count LOOP
      UPDATE uni_departments
      SET students_number = (
        SELECT COUNT(STUDENT_ID)
        FROM students
        WHERE DEPARTMENT_ID = v_counts(i)
      )
      WHERE DEPARTMENT_ID = v_counts(i);

      DBMS_OUTPUT.PUT_LINE('Department ID: ' || v_counts(i) || ', Students Count: ' || v_counts(i));
    END LOOP;
  END AFTER STATEMENT;
END;
/




CREATE OR REPLACE TRIGGER update_students_grda_trigger
AFTER UPDATE OF student_ID ON students
FOR EACH ROW
BEGIN
  UPDATE grades
  SET student_ID = :NEW.student_ID
  WHERE student_ID = :OLD.student_ID;
END;
/

CREATE OR REPLACE TRIGGER update_students_gpa_trigger
AFTER UPDATE OF student_ID ON students
FOR EACH ROW
BEGIN
  UPDATE gpa
  SET student_ID = :NEW.student_ID
  WHERE student_ID = :OLD.student_ID;
END;
/



CREATE OR REPLACE TRIGGER departments_trigger_gp
AFTER UPDATE ON UNI_Departments
FOR EACH ROW
BEGIN
  UPDATE GPA
  SET Department_ID = :new.Department_ID
  WHERE Department_ID = :old.Department_ID;
END;
/

CREATE OR REPLACE TRIGGER students_trigger_gp
AFTER INSERT ON students
FOR EACH ROW
DECLARE
  v_count NUMBER;
BEGIN

  SELECT COUNT(*) INTO v_count FROM GPA WHERE STUDENT_ID = :new.STUDENT_ID;

  IF v_count > 0 THEN

    UPDATE GPA
    SET STUDENT_ID = :new.STUDENT_ID,
        DEPARTMENT_ID = :new.DEPARTMENT_ID
    WHERE STUDENT_ID = :new.STUDENT_ID;
  ELSE

    DBMS_OUTPUT.PUT_LINE('Warning: Inserted STUDENT_ID does not exist in the GPA table.');
  END IF;
END;
/



CREATE OR REPLACE TRIGGER gpa_insert_trig
AFTER INSERT ON Registration
FOR EACH ROW
DECLARE
  v_count NUMBER;
  v_required_credits NUMBER;
BEGIN
  IF :new.STUDENT_ID IS NOT NULL AND :new.DEPARTMENT_ID IS NOT NULL THEN
    SELECT COUNT(*)
    INTO v_count
    FROM GPA
    WHERE STUDENT_ID = :new.STUDENT_ID AND DEPARTMENT_ID = :new.DEPARTMENT_ID;

    DBMS_OUTPUT.PUT_LINE('Debug: v_count = ' || v_count);

    IF v_count = 0 THEN
      INSERT INTO GPA (STUDENT_ID, DEPARTMENT_ID)
      VALUES (:new.STUDENT_ID, :new.DEPARTMENT_ID);
    END IF;


    SELECT REQUIRED_CREDITS
    INTO v_required_credits
    FROM uni_DEPARTMENTS
    WHERE DEPARTMENT_ID = :new.DEPARTMENT_ID;

    UPDATE GPA
    SET REQUIRED_CREDITS = v_required_credits
    WHERE STUDENT_ID = :new.STUDENT_ID AND DEPARTMENT_ID = :new.DEPARTMENT_ID;
  END IF;

EXCEPTION
  WHEN OTHERS THEN
    DBMS_OUTPUT.PUT_LINE('Error: ' || SQLERRM);
    RAISE;
END;
/



  -- Trigger for the 'courses' table
CREATE OR REPLACE TRIGGER coursese_trigger
AFTER UPDATE ON courses
FOR EACH ROW
BEGIN
  UPDATE Grades
  SET courses_ID = :new.courses_ID
  WHERE courses_ID = :old.courses_ID;
END;
/



-- Trigger for the 'UNI_Departments' table
CREATE OR REPLACE TRIGGER departments_trigger
AFTER UPDATE ON UNI_Departments
FOR EACH ROW
BEGIN
  UPDATE Grades
  SET Department_ID = :new.Department_ID
  WHERE Department_ID = :old.Department_ID;
END;
/

-- Trigger for the 'students' table
CREATE OR REPLACE TRIGGER students_trigger
AFTER UPDATE ON students
FOR EACH ROW
BEGIN
  UPDATE Grades
  SET STUDENT_ID = :new.STUDENT_ID
  WHERE STUDENT_ID = :old.STUDENT_ID;
END;
/




CREATE OR REPLACE TRIGGER update_students_email_trigger
AFTER UPDATE OF student_ID ON students
FOR EACH ROW
BEGIN
  UPDATE students_Email
  SET student_ID = :NEW.student_ID
  WHERE student_ID = :OLD.student_ID;
END;
/


CREATE OR REPLACE TRIGGER students_trigger
BEFORE INSERT OR UPDATE ON students
FOR EACH ROW
DECLARE
  age_calc NUMBER;
BEGIN
  age_calc := TRUNC(MONTHS_BETWEEN(SYSDATE, :NEW.DATE_OF_BIRTH) / 12);

  :NEW.AGE := age_calc;
END;
/
