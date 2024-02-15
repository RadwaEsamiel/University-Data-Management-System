/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityReporting;

/**
 *
 * @author Smart Lap
 */
public class gradesData {
    private Integer COURSES_ID;
private Integer STUDENT_ID;
private Integer STUDENT_GRADE;
private Integer GRADE_POINT;
private String  RESULT_STATUS;



    public gradesData(Integer COURSES_ID, Integer STUDENT_ID, Integer STUDENT_GRADE, Integer GRADE_POINT, String RESULT_STATUS) {
        this.COURSES_ID = COURSES_ID;
        this.STUDENT_ID = STUDENT_ID;
        this.STUDENT_GRADE = STUDENT_GRADE;
        this.GRADE_POINT = GRADE_POINT;
        this.RESULT_STATUS = RESULT_STATUS;
    }
    
    
    public Integer getCOURSES_ID() {
        return COURSES_ID;
    }

    public void setCOURSES_ID(Integer COURSES_ID) {
        this.COURSES_ID = COURSES_ID;
    }

    public Integer getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(Integer STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public Integer getSTUDENT_GRADE() {
        return STUDENT_GRADE;
    }

    public void setSTUDENT_GRADE(Integer STUDENT_GRADE) {
        this.STUDENT_GRADE = STUDENT_GRADE;
    }

    public Integer getGRADE_POINT() {
        return GRADE_POINT;
    }

    public void setGRADE_POINT(Integer GRADE_POINT) {
        this.GRADE_POINT = GRADE_POINT;
    }

    public String getRESULT_STATUS() {
        return RESULT_STATUS;
    }

    public void setRESULT_STATUS(String RESULT_STATUS) {
        this.RESULT_STATUS = RESULT_STATUS;
    }

}
