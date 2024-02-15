/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityDepartments;

/**
 *
 * @author Smart Lap
 */
public class departmentData {
    private Integer Department_ID;
            private String Depart_name;
            private String building_location;
                  private Integer REQUIRED_CREDITS;

    public departmentData(Integer Department_ID, String Depart_name, String building_location, Integer REQUIRED_CREDITS) {
        this.Department_ID = Department_ID;
        this.Depart_name = Depart_name;
        this.building_location = building_location;
        this.REQUIRED_CREDITS = REQUIRED_CREDITS;
    }

    public Integer getDepartment_ID() {
        return Department_ID;
    }

    public void setDepartment_ID(Integer Department_ID) {
        this.Department_ID = Department_ID;
    }

    public String getDepart_name() {
        return Depart_name;
    }

    public void setDepart_name(String Depart_name) {
        this.Depart_name = Depart_name;
    }

    public String getBuilding_location() {
        return building_location;
    }

    public void setBuilding_location(String building_location) {
        this.building_location = building_location;
    }

    public Integer getREQUIRED_CREDITS() {
        return REQUIRED_CREDITS;
    }

    public void setREQUIRED_CREDITS(Integer REQUIRED_CREDITS) {
        this.REQUIRED_CREDITS = REQUIRED_CREDITS;
    }
    
    
    
    
}
