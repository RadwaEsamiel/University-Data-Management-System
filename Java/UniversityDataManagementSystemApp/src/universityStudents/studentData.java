/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universityStudents;

import java.time.LocalDate;


/**
 *
 * @author Smart Lap
 */
public class studentData {
    private Integer STUDENT_ID;
private String FIRST_NAME;
private String LAST_NAME;
        private Integer DEPARTMENT_ID;

    public studentData(Integer STUDENT_ID, String LAST_NAME,String FIRST_NAME, Integer DEPARTMENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
        this.LAST_NAME = LAST_NAME;
                this.FIRST_NAME = FIRST_NAME;

        this.DEPARTMENT_ID = DEPARTMENT_ID;
    }

    public String getFIRST_NAME() {
        return FIRST_NAME;
    }

    public void setFIRST_NAME(String FIRST_NAME) {
        this.FIRST_NAME = FIRST_NAME;
    }

    public Integer getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(Integer STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public String getLAST_NAME() {
        return LAST_NAME;
    }

    public void setLAST_NAME(String LAST_NAME) {
        this.LAST_NAME = LAST_NAME;
    }

    public Integer getDEPARTMENT_ID() {
        return DEPARTMENT_ID;
    }

    public void setDEPARTMENT_ID(Integer DEPARTMENT_ID) {
        this.DEPARTMENT_ID = DEPARTMENT_ID;
    }
 
        
        
        
}
