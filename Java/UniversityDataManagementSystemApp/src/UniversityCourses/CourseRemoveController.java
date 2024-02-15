/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityCourses;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class CourseRemoveController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private TextField remove_field;

    
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
          remove_field.setText(String.valueOf(CourseID)); 
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      confirm.setOnAction(this::handleRemoveButton);
      cancel.setOnAction(this::handleCancelButton);
    }
    
     private void handleRemoveButton(ActionEvent event){
            if (remove_field.getText().isEmpty()) {
                showError("Please Enter the ID of the course you want to remove!");
            } else {
                Connection connect = null;
                try {
                    try {
                        connect = Database.connectDB();
                    }catch (ClassNotFoundException | SQLException ex) {
                        Logger.getLogger(CourseRemoveController.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                    
                    try {
                        Integer.parseInt(remove_field.getText());
                    } catch (NumberFormatException e) {
                        showError("Invalid Data. Please enter a numeric value in courses_ID.");
                        return;
                    }
                    
                    String checkSql ="SELECT COUNT(courses_ID)" +
                            "                          FROM courses where courses_ID=?";
                    int count;
                    boolean countflag = false;
                    try (final PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
                        checkStatement.setInt(1, Integer.parseInt(remove_field.getText().trim()));
                        try (ResultSet resultSet = checkStatement.executeQuery()) {
                            resultSet.next();
                            count = resultSet.getInt(1);
                            
                        }
                    }
                    if (count == 0) {
                        
                        countflag = true;
                    }
                    if (countflag) {
                        showError("course ID doesn't exist.");
                    } else {
                        boolean errorOccurred = false;
                        String sql
                                = "DELETE FROM courses WHERE courses_ID = ?";
                        try (final PreparedStatement prepare1 = connect.prepareStatement(sql)) {
                            prepare1.setInt(1, Integer.parseInt(remove_field.getText().trim()));
                            int rowsAffectedStudent = prepare1.executeUpdate();
                            if (rowsAffectedStudent <= 0) {
                                errorOccurred = true;
                                
                            }
                            if (!errorOccurred) {
                                showSuccess("course removed successfully.");
                                
                            } else {
                                showError("Failed to remove course and/or related records. Please try again.");
                                
                            }
                        }
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(CourseRemoveController.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        if (connect != null) {
                            connect.close();
                        }
                    }catch (SQLException ex) {
                        Logger.getLogger(CourseRemoveController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    
    
    
      private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
  
    
}
