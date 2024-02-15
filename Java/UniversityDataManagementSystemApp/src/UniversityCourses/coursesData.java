/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityCourses;

/** 
 * @author Smart Lap
 */
public class coursesData {
  private Integer courses_ID;
  private String course_name ;
  private Integer Success_Grade ;
   private Integer credit_hours ;

    public coursesData(Integer courses_ID, String course_name, Integer Success_Grade, Integer credit_hours) {
        this.courses_ID = courses_ID;
        this.course_name = course_name;
        this.Success_Grade = Success_Grade;
        this.credit_hours = credit_hours;
    }

    public Integer getCourses_ID() {
        return courses_ID;
    }

    public void setCourses_ID(Integer courses_ID) {
        this.courses_ID = courses_ID;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Integer getSuccess_Grade() {
        return Success_Grade;
    }

    public void setSuccess_Grade(Integer Success_Grade) {
        this.Success_Grade = Success_Grade;
    }

    public Integer getCredit_hours() {
        return credit_hours;
    }

    public void setCredit_hours(Integer credit_hours) {
        this.credit_hours = credit_hours;
    }
   
    
   
}
