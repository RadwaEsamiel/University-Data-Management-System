/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universityStudents;

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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class StudentsRemoveController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;

    /**
     * Initializes the controller class.
     */
    
     PreparedStatement prepare;
    ResultSet result;
    private Connection connect;
    
    
     private StudentsDataController mainController;
    @FXML
    private TextField remove_student;

    public void setMainController(StudentsDataController mainController) {
        this.mainController = mainController;
    }
    
     private int studentId;
 

    public void setStudentId(int studentId) {
        this.studentId = studentId;
         System.out.println("Received Student ID: " + studentId);
          remove_student.setText(String.valueOf(studentId)); 
    }

   
    

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
    /**
     * Initializes the controller class.
     */
    @Override
    
    
    public void initialize(URL url, ResourceBundle rb) {

         
      confirm.setOnAction(this::handleRemoveButton);
      cancel.setOnAction(this::handleCancelButton);


    }
    
    private void handleRemoveButton(ActionEvent event){
        if (remove_student.getText().isEmpty()) {
            showError("Please Enter the ID of the student you want to remove!");
        } else {
            Connection connect = null;
            try {
                try {
                    connect = Database.connectDB();
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }

                try {
                    Integer.parseInt(remove_student.getText());
                } catch (NumberFormatException e) {
                    showError("Invalid Data. Please enter a numeric value in STUDENT_ID.");
                    return;
                }

                String checkSql = "SELECT COUNT(STUDENTS.student_ID) FROM students WHERE STUDENTS.student_ID = ?";
                int count;
                try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
                    checkStatement.setInt(1, Integer.parseInt(remove_student.getText().trim()));
                    try (ResultSet resultSet = checkStatement.executeQuery()) {
                        resultSet.next();
                        count = resultSet.getInt(1);
                    }
                }

                if (count == 0) {
                    showError("Student ID doesn't exist.");
                } else {
                    boolean errorOccurred = false;

                    String sql = 
                            "DELETE FROM students WHERE student_ID = ?";
                    

                   
                        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
                            prepare.setInt(1, Integer.parseInt(remove_student.getText().trim()));

                           int rowsAffectedStudent = prepare.executeUpdate();
                    if (rowsAffectedStudent <= 0) 
                             {
                                errorOccurred = true;
                              
                                
                            }
                    
                        
                    if (!errorOccurred){
                        showSuccess("Student removed successfully.");
                        
                    } else {
                        showError("Failed to remove student and/or related records. Please try again.");
                          
                    }
                        }
                        
                        
                }
            } catch (SQLException ex) {
                Logger.getLogger(StudentsRemoveController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (connect != null) {
                        connect.close();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(StudentsRemoveController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    
}           
      
    
private void handleCancelButton(ActionEvent event) {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
}
}
