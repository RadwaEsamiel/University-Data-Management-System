/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityRegistrations;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class RegisterStudentController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private Button confirm;
    @FXML
    private TextField StudentID;
    @FXML
    private TextField CoursesID;
    @FXML
    private CheckBox all_check;
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
    
     private  UniversityRegistrations.RegistrationsDataController mainController;


    public void setRegistrationsDataController(UniversityRegistrations.RegistrationsDataController mainController) {
        this.mainController = mainController;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        confirm.setOnAction(this::handleRegisterButton);
      cancel.setOnAction(this::handleCancelButton);
    }    
    
   private void handleRegisterButton(ActionEvent event) {
      
                 if (StudentID.getText().isEmpty() || CoursesID.getText().isEmpty()) {
                showError("Please fill all fields to complete Registration!");
                return;
                 }
    try {
        connect = UniversityRegistrations.Database.connectDB();


        int courseId;
try {
    courseId = Integer.parseInt(CoursesID.getText().trim());
} catch (NumberFormatException e) {
    showError("Invalid Data. Please enter a numeric value in course ID.");
    return;
}

if (!validatecourseId(courseId)) {
    showError("courses ID doesn't exist.");
    return;
}

int StudentsID;
            try {
                StudentsID = Integer.parseInt(StudentID.getText().trim());
            } catch (NumberFormatException e) {
                showError("Invalid Data. Please enter a numeric value in Student ID.");
                return;
            }

            if (!validateStudentID(StudentsID)) {
                showError("Student ID doesn't exist.");
                return;
            }
            
       if (validateFirst(StudentsID, courseId)) {
    showError("courses ID is already assigned to this Student ID.");
    return;
}         

        if (all_check.isSelected()) {
            executeQueryForAllCourses(StudentsID, courseId);
        } else {
            executeQueryForSpecificCourse(StudentsID, courseId);
        }

 

    } catch (Exception e) {
        showError("Error occurred during registration. Please try again.");
    } finally {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
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
    
     private boolean validateStudentID(int StudentsID) throws SQLException {
        String checkSql = "SELECT COUNT(student_ID) FROM students WHERE student_ID=?";
        int count;
        boolean countflag = false;
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, StudentsID);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
                countflag = count == 0;
            }
        }
        return !countflag;
    }
     
     
      private boolean validateFirst(int StudentsID, int coursId) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM registration WHERE student_ID=? and courses_ID=?";
        int count;
        boolean countflag = false;
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, StudentsID);
                checkStatement.setInt(2, coursId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
                countflag = count == 0;
            }
        }
        return !countflag;
    }
      

private void executeQueryForAllCourses(int StudentsID, int courseId) throws SQLException {

                  boolean errorOccurred = false;
                    boolean errorOccurredloc = false;
                    
                String depSql = "INSERT INTO registration (student_ID, courses_ID, Department_ID) VALUES (?, ?, (SELECT Department_ID FROM students WHERE student_ID = ?))";
                    String Sql = "INSERT INTO Departments_courses (courses_ID, Department_ID) VALUES (?, (SELECT Department_ID FROM students WHERE student_ID = ?))";


                    prepare = connect.prepareStatement(depSql);
                                  prepare.setInt(1, StudentsID);
                prepare.setInt(2, courseId);
          prepare.setInt(3, StudentsID);

                    int rowsAffecteddep = prepare.executeUpdate();

                    prepare.clearParameters();

                    prepare = connect.prepareStatement(Sql);
                   prepare.setInt(1, courseId);

                    prepare.setInt(2, StudentsID);

                    int rowsAffectedloc = prepare.executeUpdate();

                    if (rowsAffecteddep <= 0) {
                        errorOccurred = true;
                    }

                    if (rowsAffectedloc <= 0) {
                        errorOccurredloc = true;
                    }

                    if (!errorOccurred) {
                        showSuccess("course Registered successfully.");
                    } else {
                        showError("Failed to Register course. Please try again.");
                    }

                    if (!errorOccurredloc) {
                        showSuccess("course Registered successfully to all Departments students.");
                    } else {
                        showError("Failed to Registered successfully to all Departments students. Please try again later.");
                    }
                }
    

private void executeQueryForSpecificCourse(int StudentsID, int courseId) throws SQLException {
     boolean errorOccurred = false;

                String depSql = "INSERT INTO registration (student_ID, courses_ID, Department_ID) VALUES (?, ?, (SELECT Department_ID FROM students WHERE student_ID = ?))";

                prepare = connect.prepareStatement(depSql);
                      prepare.setInt(1, StudentsID);
                prepare.setInt(2, courseId);
          prepare.setInt(3, StudentsID);
             
                int rowsAffecteddep = prepare.executeUpdate();

                if (rowsAffecteddep <= 0) {
                    errorOccurred = true;
                }

                if (!errorOccurred) {
                    showSuccess("course Registered successfully.");
                } else {
                    showError("Failed to Register course. Please try again.");
                }
}

    
    
     private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}

