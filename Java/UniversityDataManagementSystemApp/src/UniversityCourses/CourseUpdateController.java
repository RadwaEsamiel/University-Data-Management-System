/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityCourses;

import UniversityDepartments.DepartmentUpdateController;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class CourseUpdateController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private TextField upd_value;
    @FXML
    private ComboBox<String> upd_field;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private TextField update_field;
  
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
    
    
     private  UniversityCourses.CoursesDataController mainController;


    public void setCoursesDataController(UniversityCourses.CoursesDataController mainController) {
        this.mainController = mainController;
    }
    
          private int CourseID;
 

    public void setcoursetId(int CourseID) {
        this.CourseID = CourseID;
         System.out.println("Received Student ID: " + CourseID);
          update_field.setText(String.valueOf(CourseID)); 
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       upd_field.setItems(
                FXCollections.observableArrayList(
                        "courses_ID",
                        "course_name",
                        "Success_Grade",
                        "credit_hours"
                )
        );
         
      confirm.setOnAction(this::handleUpdateButton);
      cancel.setOnAction(this::handleCancelButton);
    }
    
    private void handleUpdateButton(ActionEvent event) {
        String selectedColumn = upd_field.getValue();

        if (selectedColumn == null || upd_value.getText().isEmpty() || update_field.getText().isEmpty()) {
            showError("Please select a column and fill all fields required to update data!");
        } else {
            try {
                connect = Database.connectDB(); 

int courseId;
try {
    courseId = Integer.parseInt(update_field.getText().trim());
} catch (NumberFormatException e) {
    showError("Invalid Data. Please enter a numeric value in course ID.");
    return;
}

if (!validatecourseId(courseId)) {
    showError("courses ID doesn't exist.");
    return;
}

if ("courses_ID".equalsIgnoreCase(selectedColumn)) {
    int newcourseId;
    try {
        newcourseId = Integer.parseInt(upd_value.getText().trim());
    } catch (NumberFormatException e) {
        showError("Invalid Data. Please enter a numeric value for the new courses ID.");
        return;
    }

    if (!validatenewcourseId(newcourseId)) {
        showError("New courses ID already exists. Please enter a different value.");
        return;
    }
}

                    if (!validateDataTypes(selectedColumn, upd_value.getText().trim())) {
                        return;  
                    }
                    
                    boolean errorOccurred = false;

         
                updateCourses(selectedColumn, courseId, errorOccurred);
            

            if (errorOccurred) {
                showError("Failed to update Department Data. Please try again.");
            } else {
                showSuccess("courses Data updated successfully.");
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DepartmentUpdateController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentUpdateController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    }

 private void updateCourses(String selectedColumn, int courseId, boolean errorOccurred) {
        try {
            String sql = "UPDATE courses SET " + selectedColumn + " = ? WHERE courses_ID = ?";
            PreparedStatement prepare1 = connect.prepareStatement(sql);
             if (selectedColumn.equalsIgnoreCase("courses_ID") || selectedColumn.equalsIgnoreCase("Success_Grade") || selectedColumn.equalsIgnoreCase("credit_hours")) {
            prepare1.setInt(1, Integer.parseInt(upd_value.getText().trim()));
        } else {
            prepare1.setString(1, upd_value.getText().trim());
        }

        prepare1.setInt(2, Integer.parseInt(update_field.getText().trim()));
        int rowsAffectedStudent = prepare1.executeUpdate();
        
            
            if (rowsAffectedStudent <= 0) {
                errorOccurred = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseUpdateController.class.getName()).log(Level.SEVERE, null, ex);
             errorOccurred = true;
        }
                    
                }
        
    
    private boolean validatecourseId(int courseId) throws SQLException {
        String checkSql = "SELECT COUNT(courses_ID) FROM courses WHERE courses_ID=?";
                        int count;

        boolean countflag = false;
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, courseId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
                countflag = count == 0;
            }
        }
        return !countflag;
    }

        
    private boolean validatenewcourseId(int newcourseId) throws SQLException {
        String checkSql = "SELECT COUNT(courses_ID) FROM courses WHERE courses_ID=?";
        int count;
        boolean countflag = false;
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, newcourseId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
                countflag = count == 0;
            }
        }
        return countflag;
    }
    
    private boolean validateDataTypes(String selectedColumn, String value) {
        try {
            if (selectedColumn.equalsIgnoreCase("courses_ID") || selectedColumn.equalsIgnoreCase("Success_Grade") || selectedColumn.equalsIgnoreCase("credit_hours")) {
                Integer.parseInt(value);
            } else if (selectedColumn.equalsIgnoreCase("course_name")) {

                if (value.isEmpty() || value.length() > 50) {
                    showError("Invalid value for " + selectedColumn + ". Please enter a non-empty string with a maximum length of 50 characters.");
                    return false;
                }
            }
            return true; 
        } catch (NumberFormatException e) {
            showError("Invalid numeric value for " + selectedColumn + ". Please enter a valid number.");
            return false;
        }
    }
  

   private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    
    
}
