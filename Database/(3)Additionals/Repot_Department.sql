/*<TOAD_FILE_CHUNK>*/
SET SERVEROUTPUT ON;

CREATE OR REPLACE PROCEDURE DEP_GPA_Report(v_Department_ID NUMBER)
IS 
  CURSOR DPGPA_cursor IS
    SELECT 
      STUDENT_ID,
      Department_ID,
      STUDENT_GPA,
      FINAL_RESULT
    FROM GPA
    WHERE Department_ID = v_Department_ID;
   
  v_row DPGPA_cursor%ROWTYPE;
BEGIN
  OPEN DPGPA_cursor;
  
  LOOP
    FETCH DPGPA_cursor INTO v_row;
    
    EXIT WHEN DPGPA_cursor%NOTFOUND;

    DBMS_OUTPUT.PUT_LINE('STUDENT_ID: ' || v_row.STUDENT_ID || '--Department_ID: ' || v_row.Department_ID||'--STUDENT_GPA: ' || v_row.STUDENT_GPA ||'--FINAL_RESULT: ' || v_row.FINAL_RESULT);
  END LOOP;
  
  CLOSE DPGPA_cursor;
END DEP_GPA_Report;
/
/*<TOAD_FILE_CHUNK>*/

set serveroutput on
begin 
DEP_GPA_Report(101);
end;
