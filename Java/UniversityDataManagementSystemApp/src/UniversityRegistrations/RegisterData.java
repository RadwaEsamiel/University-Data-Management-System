/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityRegistrations;

/**
 *
 * @author Smart Lap
 */
public class RegisterData {
    private Integer student_ID;
     private Integer courses_ID;
      private Integer Department_ID;
             private String schedule;

    public RegisterData(Integer student_ID, Integer courses_ID, Integer Department_ID, String schedule) {
        this.student_ID = student_ID;
        this.courses_ID = courses_ID;
        this.Department_ID = Department_ID;
        this.schedule = schedule;
    }

    public Integer getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(Integer student_ID) {
        this.student_ID = student_ID;
    }

    public Integer getCourses_ID() {
        return courses_ID;
    }

    public void setCourses_ID(Integer courses_ID) {
        this.courses_ID = courses_ID;
    }

    public Integer getDepartment_ID() {
        return Department_ID;
    }

    public void setDepartment_ID(Integer Department_ID) {
        this.Department_ID = Department_ID;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
             
             
             
}
