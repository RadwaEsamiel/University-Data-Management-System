/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityCourses;

import UniversityDepartments.DepartmentADDController;
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
public class CourseADDController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private Button confirm;
    @FXML
    private Label DEPID;
    @FXML
    private Label FNAME;
    @FXML
    private Label LNAME;
    @FXML
    private Label FNAME1;
    @FXML
    private TextField courseID;
    @FXML
    private TextField course_name;
    @FXML
    private TextField Succ_grade;
    @FXML
    private TextField credit_h;
    @FXML
    private Button cancel;

    

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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      confirm.setOnAction(this::handleAddButton);
      cancel.setOnAction(this::handleCancelButton);
    }
    
private void handleAddButton(ActionEvent event) {
    if (courseID.getText().isEmpty()
            || course_name.getText().isEmpty()
            || Succ_grade.getText().isEmpty()
            || credit_h.getText().isEmpty()) {
        showError("Please fill all fields to Add course!");
        return;
    }

    try {
        connect = Database.connectDB();

        int courseidd;
        int Succgrade;
        int credithr;
        try {
            courseidd = Integer.parseInt(courseID.getText().trim());
            Succgrade = Integer.parseInt(Succ_grade.getText().trim());
            credithr = Integer.parseInt(credit_h.getText().trim());
        } catch (NumberFormatException e) {
            showError("Invalid Data. Please enter numeric values in Success Grade, credit hours and course ID.");
            return;
        }

        String checkSql = "SELECT COUNT(courses_ID) FROM courses WHERE courses_ID=?";
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, courseidd);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                boolean errorOccurred = false;

                String depSql = "INSERT INTO courses (courses_ID, course_name, Success_Grade, credit_hours) VALUES (?, ?, ?, ?)";

                prepare = connect.prepareStatement(depSql);
                prepare.setInt(1, courseidd);
                prepare.setString(2, course_name.getText().trim());
                prepare.setInt(3, Succgrade);
                prepare.setInt(4, credithr);

                int rowsAffecteddep = prepare.executeUpdate();

                if (rowsAffecteddep <= 0) {
                    errorOccurred = true;
                }

                if (!errorOccurred) {
                    showSuccess("course added successfully.");
                } else {
                    showError("Failed to add course. Please try again.");
                }
            } else {
                showError("course ID already exists.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CourseADDController.class.getName()).log(Level.SEVERE, null, ex);
            showError("An error occurred. Please check the logs for details.");
        }
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(CourseADDController.class.getName()).log(Level.SEVERE, null, ex);
        showError("An error occurred. Please check the logs for details.");
    } finally {
        try {
            if (result != null) {
                result.close();
            }
            if (prepare != null) {
                prepare.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

                
          private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
    
    
}
