
    DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 101) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1027, 101, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 102) LOOP
    v_student_id := student_rec.student_ID;
    
    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1029, 102, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 103) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1031, 103, 'Mon-Wed 11:00 AM to 3:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 104) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1033, 104, 'Tue-Thu 2:00 PM to 6:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 105) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1035, 105, 'Mon-Wed 10:00 AM to 2:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 106) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1037, 106, 'Mon-Wed 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 107) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1039, 107, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 108) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1041, 108, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 109) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1043, 109, 'Tue-Thu 10:00 AM to 12:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 110) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1045, 110, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/
DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 111) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1047, 111, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 112) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1049, 112, 'Mon-Wed 11:00 AM to 3:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 113) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1051, 113, 'Tue-Thu 2:00 PM to 6:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 114) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1053, 114, 'Mon-Wed 10:00 AM to 2:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 115) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1055, 115, 'Mon-Wed 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 116) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1057, 116, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 117) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1059, 117, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 118) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1061, 118, 'Tue-Thu 10:00 AM to 12:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 119) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1063, 119, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 120) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1065, 120, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/


-- Department_ID 101
DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 101) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1067, 101, 'Mon-Wed 11:00 AM to 3:00 PM');
  END LOOP;
END;
/

-- Department_ID 102
DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 102) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1069, 102, 'Tue-Thu 2:00 PM to 6:00 PM');
  END LOOP;
END;
/

-- Department_ID 103
DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 103) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1071, 103, 'Mon-Wed 10:00 AM to 2:00 PM');
  END LOOP;
END;
/

-- Department_ID 104
DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 104) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1073, 104, 'Mon-Wed 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

-- Department_ID 105
DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 105) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1075, 105, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 110) LOOP
    v_student_id := student_rec.student_ID;
    
    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1165, 110, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 111) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1167, 111, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 112) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1169, 112, 'Tue-Thu 10:00 AM to 12:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 113) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1171, 113, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 114) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1173, 114, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 115) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1175, 115, 'Mon-Wed 11:00 AM to 3:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 116) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1177, 116, 'Tue-Thu 2:00 PM to 6:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 117) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1179, 117, 'Mon-Wed 10:00 AM to 2:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 118) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1181, 118, 'Mon-Wed 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 119) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1183, 119, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 120) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1185, 120, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 101) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1187, 101, 'Tue-Thu 10:00 AM to 12:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 102) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1189, 102, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 103) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1191, 103, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 104) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1193, 104, 'Mon-Wed 11:00 AM to 3:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 105) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1195, 105, 'Tue-Thu 2:00 PM to 6:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 106) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1197, 106, 'Mon-Wed 10:00 AM to 2:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 107) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1199, 107, 'Mon-Wed 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 108) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1201, 108, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 109) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1203, 109, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 110) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1205, 110, 'Tue-Thu 10:00 AM to 12:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 111) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1207, 111, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 112) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1209, 112, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 113) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1211, 113, 'Mon-Wed 11:00 AM to 3:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 114) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1213, 114, 'Tue-Thu 2:00 PM to 6:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 115) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1215, 115, 'Mon-Wed 10:00 AM to 2:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 116) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1217, 116, 'Mon-Wed 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 117) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1219, 117, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 118) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1221, 118, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 119) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1223, 119, 'Tue-Thu 10:00 AM to 12:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 120) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1225, 120, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 101) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1227, 101, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 102) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1229, 102, 'Mon-Wed 11:00 AM to 3:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 103) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1231, 103, 'Tue-Thu 2:00 PM to 6:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 104) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1233, 104, 'Mon-Wed 10:00 AM to 2:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 105) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1235, 105, 'Mon-Wed 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 106) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1237, 106, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 107) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1239, 107, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 108) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1241, 108, 'Tue-Thu 10:00 AM to 12:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 109) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1243, 109, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 110) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1245, 110, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 111) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1247, 111, 'Mon-Wed 11:00 AM to 3:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 112) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1249, 112, 'Tue-Thu 2:00 PM to 6:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 113) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1251, 113, 'Mon-Wed 10:00 AM to 2:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 114) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1251, 114, 'Mon-Wed 9:00 AM to 1:00 PM');
  END LOOP;
END;
/


DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 115) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1251, 115, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 116) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1249, 116, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 117) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1159, 117, 'Tue-Thu 10:00 AM to 12:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 118) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1161, 118, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 119) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1163, 119, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 120) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1165, 120, 'Mon-Wed 11:00 AM to 3:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 101) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1167, 101, 'Tue-Thu 2:00 PM to 6:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 102) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1169, 102, 'Mon-Wed 10:00 AM to 2:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 103) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1171, 103, 'Mon-Wed 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 104) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1173, 104, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 105) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1175, 105, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 106) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1177, 106, 'Tue-Thu 10:00 AM to 12:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 107) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1219, 107, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 108) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1221, 108, 'Mon-Wed 11:00 AM to 3:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 109) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1223, 109, 'Tue-Thu 2:00 PM to 6:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 110) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1225, 110, 'Mon-Wed 10:00 AM to 2:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 111) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1227, 111, 'Mon-Wed 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 112) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1229, 112, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 113) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1231, 113, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 114) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1233, 114, 'Tue-Thu 10:00 AM to 12:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 115) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1235, 115, 'Mon-Wed 1:00 PM to 5:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 116) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1237, 116, 'Tue-Thu 9:00 AM to 1:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 117) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1239, 117, 'Mon-Wed 11:00 AM to 3:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 118) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1241, 118, 'Tue-Thu 2:00 PM to 6:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 119) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1243, 119, 'Mon-Wed 10:00 AM to 2:00 PM');
  END LOOP;
END;
/

DECLARE
  v_student_id students.student_id%TYPE;
BEGIN
  FOR student_rec IN (SELECT student_ID FROM students WHERE Department_ID = 120) LOOP
    v_student_id := student_rec.student_ID;

    INSERT INTO Registration (student_ID, courses_ID, Department_ID, schedule)
    VALUES (v_student_id, 1245, 120, 'Mon-Wed 9:00 AM to 1:00 PM');
  END LOOP;
END;
/
