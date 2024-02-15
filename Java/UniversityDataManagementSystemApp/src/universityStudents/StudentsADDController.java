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
public class StudentsADDController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private Button confirm;
    @FXML
    private TextField STUDENT_ID;
    @FXML
    private TextField FIRST_NAME;
    @FXML
    private TextField LAST_NAME;
    @FXML
    private TextField GENDER;
    @FXML
    private TextField DEPARTMENT_ID;
    @FXML
    private TextField DATE_OF_BIRTH;
    @FXML
    private TextField ADDRESS;
    @FXML
    private TextField EMAIL;
    @FXML
    private Label STUD;
    @FXML
    private Label FNAME;
    @FXML
    private Label LNAME;
    @FXML
    private Label GEN;
    @FXML
    private Label DEPID;
    @FXML
    private Label DBIRTH;
    @FXML
    private Label ADD;
    @FXML
    private Label EM;
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
    
    
    
    private StudentsDataController mainController;


    public void setMainController(StudentsDataController mainController) {
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
    if (EMAIL.getText().isEmpty()
            || ADDRESS.getText().isEmpty()
            || DATE_OF_BIRTH.getText().isEmpty()
            || DEPARTMENT_ID.getText().isEmpty()
            || GENDER.getText().isEmpty()
            || STUDENT_ID.getText().isEmpty()
            || FIRST_NAME.getText().isEmpty()
            || LAST_NAME.getText().isEmpty()) {
        showError("Please fill all fields to Add student!");
        return;
    }

    try {
        try {
            connect = Database.connectDB();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(StudentsADDController.class.getName()).log(Level.SEVERE, null, ex);
        }

        int studentId;
        int departmentId;

        try {
            studentId = Integer.parseInt(STUDENT_ID.getText().trim());
            departmentId = Integer.parseInt(DEPARTMENT_ID.getText().trim());

            if (!validateDepartmentId(departmentId)) {
                showError("Invalid Department ID.");
                return;
            }

          
        } catch (NumberFormatException e) {
            showError("Invalid Data. Please enter numeric values in STUDENT_ID and DEPARTMENT_ID.");
            return;
        }

        String dateFormat = "yyyy-MM-dd";
        String inputDate = DATE_OF_BIRTH.getText().trim();

        if (!inputDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            showError("Invalid date format. Please use 'YYYY-MM-DD'.");
            return;
        }

        String checkSql = "SELECT COUNT(STUDENTS_EMAIL.student_ID) FROM students join students_email on STUDENTS.STUDENT_ID=STUDENTS_EMAIL.STUDENT_ID where  STUDENTS_EMAIL.student_ID = ?";
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, studentId);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                String studentSql = "INSERT INTO students (student_ID, First_name, Last_name, Gender, Department_ID, Date_of_birth, Address) "
                        + "VALUES (?, ?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?)";
                String emailSql = "INSERT INTO students_Email (Email, student_ID) VALUES (?, ?)";

                prepare = connect.prepareStatement(studentSql);
                prepare.setInt(1, studentId);
                prepare.setString(2, FIRST_NAME.getText().trim());
                prepare.setString(3, LAST_NAME.getText().trim());
                prepare.setString(4, GENDER.getText().trim());
                prepare.setInt(5, departmentId);
                prepare.setString(6, inputDate);
                prepare.setString(7, ADDRESS.getText().trim());

                int rowsAffectedStudent = prepare.executeUpdate();

                prepare.clearParameters();

                prepare = connect.prepareStatement(emailSql);
                prepare.setString(1, EMAIL.getText().trim());
                prepare.setInt(2, studentId);

                int rowsAffectedEmail = prepare.executeUpdate();

                if (rowsAffectedStudent > 0 && rowsAffectedEmail > 0) {
                    showSuccess("Student added successfully.");
                } else {
                    showError("Failed to add student and/or email. Please try again.");
                }
            } else {
                showError("Student ID already exists.");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        showError("An error occurred while processing the student ID check or adding the student. Please try again.");
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

  

    private boolean validateDepartmentId(int departmentId) throws SQLException {
        String checkSql = "SELECT COUNT(UNI_Departments.department_ID) FROM UNI_Departments WHERE UNI_Departments.department_ID = ?";
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, departmentId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                int count = resultSet.getInt(1);
                return count != 0;
            }
        }
    }

    private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
        
    }
}