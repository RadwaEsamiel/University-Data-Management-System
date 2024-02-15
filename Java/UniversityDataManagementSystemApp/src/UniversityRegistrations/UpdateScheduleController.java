/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityRegistrations;

import UniversityCourses.CourseUpdateController;
import UniversityDepartments.DepartmentUpdateController;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class UpdateScheduleController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private Button confirm;
    @FXML
    private TextField DEPARTMENT_ID;
    @FXML
    private Button cancel;
    @FXML
    private TextField CoursesID;
    @FXML
    private TextField Schedule;

      
 private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
     private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        
        
         Stage stage = (Stage) confirm.getScene().getWindow();
    stage.close();
    }
    
         PreparedStatement prepare;
    ResultSet result;
    private Connection connect;
    private  UniversityRegistrations.RegistrationsDataController mainController;


    public void setRegistrationsDataController(UniversityRegistrations.RegistrationsDataController mainController) {
        this.mainController = mainController;
    }
    
    
         private int DepartmentId;
  private int courseId;

    public void setDepartmentId(int DepartmentId) {
        this.DepartmentId = DepartmentId;
         System.out.println("Received Student ID: " + DepartmentId);
          DEPARTMENT_ID.setText(String.valueOf(DepartmentId)); 
    }
    
    public void setcourseId(int courseId) {
        this.courseId = courseId;
         System.out.println("Received Student ID: " + courseId);
          CoursesID.setText(String.valueOf(courseId)); 
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       confirm.setOnAction(this::handleUpdateButton);
      cancel.setOnAction(this::handleCancelButton);
    }   
    
     private void handleUpdateButton(ActionEvent event) {
            try {
                 if (DEPARTMENT_ID.getText().isEmpty() || CoursesID.getText().isEmpty() || Schedule.getText().isEmpty()) {
                showError("Please fill all fields to Add DEPARTMENT!");
                return;
            } 
 
                try {
                    connect = UniversityDepartments.Database.connectDB();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UpdateScheduleController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                int coursId;
                try {
                    coursId = Integer.parseInt(CoursesID.getText().trim());
                } catch (NumberFormatException e) {
                    showError("Invalid Data. Please enter a numeric value in Courses ID.");
                    return;
                }
                
                if (!validatecoursId(coursId)) {
                    showError("course ID doesn't exist or it isn't assigned to any DEPARTMENT yet.");
                    return;
                }
                
                    int departmentId;
                try {
                    departmentId = Integer.parseInt(DEPARTMENT_ID.getText().trim());
                } catch (NumberFormatException e) {
                    showError("Invalid Data. Please enter a numeric value in DEPARTMENT ID.");
                    return;
                }
                
                if (!validateDepartmentId(departmentId)) {
                    showError("Department ID doesn't exist or there's no courses assigned to it yet.");
                    return;
                }
                
                
                 if (!validateschadule(departmentId,coursId) ){
  showError("course ID isn't assigned to this DEPARTMENT yet.");

                return;  
            }
                
          
     
      boolean errorOccurred = false;

         
                updateCoursesschedule(departmentId ,coursId , errorOccurred);
            

            if (errorOccurred) {
                showError("Failed to update schedule . Please try again.");
            } else {
                showSuccess("schedule has been updated successfully.");
            }
     
                if (connect != null) {
                    connect.close();
                }
      
           
            }
                       catch (SQLException e) {
        // handle the exception or log it
        e.printStackTrace();
    }
     }

     
   
     
     
 private void updateCoursesschedule(int departmentId, int courseId, boolean errorOccurred) {
        try {
            String sql = "update Departments_courses set SCHEDULE = ? where  COURSES_ID=? and Department_ID=?";
            PreparedStatement prepare1 = connect.prepareStatement(sql);
          
prepare1.setString(1, Schedule.getText().trim());
         prepare1.setInt(2, courseId);
                  prepare1.setInt(3, departmentId);

        int rowsAffectedStudent = prepare1.executeUpdate();
        
            
            if (rowsAffectedStudent <= 0) {
                errorOccurred = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseUpdateController.class.getName()).log(Level.SEVERE, null, ex);
             errorOccurred = true;
        }
                    
                }
        
    
    
    private boolean validateDepartmentId(int departmentId) throws SQLException {
        String checkSql = "SELECT COUNT(DEPARTMENT_ID) FROM Departments_courses where DEPARTMENT_ID=?";
        int count;
        boolean countflag = false;
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, departmentId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
                countflag = count == 0;
            }
        }
        return !countflag;
    }
    
    private boolean validatecoursId(int coursId) throws SQLException {
        String checkSql = "SELECT COUNT(COURSES_ID) FROM Departments_courses where COURSES_ID=?";
        int count;
        boolean countflag = false;
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, coursId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
                countflag = count == 0;
            }
        }
        return !countflag;
    }
    
    private boolean validateschadule(int departmentId, int coursId) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM Departments_courses where COURSES_ID=? and Department_ID=?";
        int count;
        boolean countflag = false;
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, coursId);
                checkStatement.setInt(2, departmentId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
                countflag = count == 0;
            }
        }
        return !countflag;
    }
    
       private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    
}
