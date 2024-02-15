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
public class GPA_Data {
   private Integer DEPARTMENT_ID ;
           private Integer STUDENT_ID ;
                   private Integer REQUIRED_CREDITS ;
                           private Integer REGISTERED_HOURS ;
                                   private Integer STUDENT_GPA ;
                                   private String FINAL_RESULT;

    public GPA_Data(Integer DEPARTMENT_ID, Integer STUDENT_ID, Integer REQUIRED_CREDITS, Integer REGISTERED_HOURS, Integer STUDENT_GPA, String FINAL_RESULT) {
        this.DEPARTMENT_ID = DEPARTMENT_ID;
        this.STUDENT_ID = STUDENT_ID;
        this.REQUIRED_CREDITS = REQUIRED_CREDITS;
        this.REGISTERED_HOURS = REGISTERED_HOURS;
        this.STUDENT_GPA = STUDENT_GPA;
        this.FINAL_RESULT = FINAL_RESULT;
    }

    public Integer getDEPARTMENT_ID() {
        return DEPARTMENT_ID;
    }

    public void setDEPARTMENT_ID(Integer DEPARTMENT_ID) {
        this.DEPARTMENT_ID = DEPARTMENT_ID;
    }

    public Integer getSTUDENT_ID() {
        return STUDENT_ID;
    }

    public void setSTUDENT_ID(Integer STUDENT_ID) {
        this.STUDENT_ID = STUDENT_ID;
    }

    public Integer getREQUIRED_CREDITS() {
        return REQUIRED_CREDITS;
    }

    public void setREQUIRED_CREDITS(Integer REQUIRED_CREDITS) {
        this.REQUIRED_CREDITS = REQUIRED_CREDITS;
    }

    public Integer getREGISTERED_HOURS() {
        return REGISTERED_HOURS;
    }

    public void setREGISTERED_HOURS(Integer REGISTERED_HOURS) {
        this.REGISTERED_HOURS = REGISTERED_HOURS;
    }

    public Integer getSTUDENT_GPA() {
        return STUDENT_GPA;
    }

    public void setSTUDENT_GPA(Integer STUDENT_GPA) {
        this.STUDENT_GPA = STUDENT_GPA;
    }

    public String getFINAL_RESULT() {
        return FINAL_RESULT;
    }

    public void setFINAL_RESULT(String FINAL_RESULT) {
        this.FINAL_RESULT = FINAL_RESULT;
    }
                                   
                                   

}
