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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import universityStudents.Database;
import universityStudents.FXMLDocumentController;
import universityStudents.StudentsDataController;
import static javax.management.remote.JMXConnectorFactory.connect;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class StudentsUpdateController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private TextField update_stu;
    @FXML
    private TextField upd_value;
    @FXML
    private ComboBox<String> upd_field;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;

     PreparedStatement prepare;
    ResultSet result;
    private Connection connect;
    
    
     private StudentsDataController mainController;

    public void setMainController(StudentsDataController mainController) {
        this.mainController = mainController;
    }
    
     private int studentId;
 

    public void setStudentId(int studentId) {
        this.studentId = studentId;
         System.out.println("Received Student ID: " + studentId);
          update_stu.setText(String.valueOf(studentId)); 
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

          
               upd_field.setItems(
            FXCollections.observableArrayList(
                    "STUDENT_ID", "FIRST_NAME", "LAST_NAME", "GENDER",
                    "DEPARTMENT_ID", "DATE_OF_BIRTH", "ADDRESS", "EMAIL"
            )
    );
      confirm.setOnAction(this::handleUpdateButton);
      cancel.setOnAction(this::handleCancelButton);

      

    
   }  
     private boolean validateDepartmentId(int departmentId) throws SQLException {
        String checkSql = "SELECT COUNT(UNI_Departments.DEPARTMENT_ID), COUNT(Departments_Location.DEPARTMENT_ID)  FROM Departments_Location join UNI_Departments on Departments_Location.DEPARTMENT_ID=UNI_Departments.DEPARTMENT_ID where UNI_Departments.DEPARTMENT_ID=?";
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
     
    
    
    private void handleUpdateButton(ActionEvent event) {
 String selectedColumn = upd_field.getValue();

    if (selectedColumn == null || upd_value.getText().isEmpty() || update_stu.getText().isEmpty()) {
        showError("Please select a column and fill all fields required to update data!");
        return;
    }

    try {
        connect = Database.connectDB();
    } catch (ClassNotFoundException | SQLException ex) {
        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        showError("Failed to connect to the database.");
        return;
    }

    try {
        int studentId;
        try {
            studentId = Integer.parseInt(update_stu.getText().trim());
        } catch (NumberFormatException e) {
            showError("Invalid Data. Please enter a numeric value in STUDENT_ID.");
            return;
        }

        if (!validateStudentId(studentId)) {
            showError("Student ID doesn't exist.");
            return;
        }

        if (!validateDataTypes(selectedColumn, upd_value.getText().trim())) {
            return; 
        }
        
        if ("STUDENT_ID".equalsIgnoreCase(selectedColumn)) {
            int newStudentId;
            try {
                newStudentId = Integer.parseInt(upd_value.getText().trim());
            } catch (NumberFormatException e) {
                showError("Invalid Data. Please enter a numeric value for the new STUDENT ID.");
                return;
            }

            if (!validatenewStudentId(newStudentId)) {
                showError("New STUDENT ID already exists. Please enter a different value.");
                return;
            }
        }
        
        if ("Department_ID".equalsIgnoreCase(selectedColumn)) {
            int departmentId;
            try {
                departmentId = Integer.parseInt(upd_value.getText().trim());
            } catch (NumberFormatException e) {
                showError("Invalid Data. Please enter a numeric value for the new DEPARTMENT_ID.");
                return;
            }
            

       if (!validateDepartmentId(departmentId)) {
                showError("Department ID doesn't exist.");
                return;
            }
       
       }
        boolean errorOccurred = false;

        if ("DATE_OF_BIRTH".equalsIgnoreCase(selectedColumn)) {
            errorOccurred = updateDateOfBirth(studentId);
        } else if ("EMAIL".equalsIgnoreCase(selectedColumn)) {
            errorOccurred = updateEmail(studentId);
        } else {
            errorOccurred = updateStudentTable(selectedColumn, studentId);
        }

        if (errorOccurred) {
            showError("Failed to update student Data. Please try again.");
        } else {
            showSuccess("Student Data updated successfully.");
        }

    } catch (SQLException ex) {
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        showError("An error occurred while updating student data.");
    } finally {
        try {
            if (connect != null) {
                connect.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

private boolean validateStudentId(int studentId) throws SQLException {
    String checkSql = "SELECT COUNT(STUDENTS.student_ID) FROM students WHERE STUDENTS.student_ID = ?";
    try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
        checkStatement.setInt(1, studentId);
        try (ResultSet resultSet = checkStatement.executeQuery()) {
            resultSet.next();
            int count = resultSet.getInt(1);
            return count != 0;
        }
    }
}

private boolean validateDataTypes(String selectedColumn, String value) {
    try {
        if (selectedColumn.equalsIgnoreCase("STUDENT_ID") || selectedColumn.equalsIgnoreCase("Department_ID")) {
            Integer.parseInt(value);  
        } else if (selectedColumn.equalsIgnoreCase("Gender")) {
            String genderValue = value.trim();
            if (!genderValue.equalsIgnoreCase("Male") && !genderValue.equalsIgnoreCase("Female")) {
                showError("Invalid value for Gender. Please enter either 'Male' or 'Female'.");
                return false;
            }
        } else if (selectedColumn.equalsIgnoreCase("First_name") || selectedColumn.equalsIgnoreCase("Last_name")) {
            String stringValue = value.trim();
            if (stringValue.isEmpty() || stringValue.length() > 50) {
                showError("Invalid value for " + selectedColumn + ". Please enter a non-empty string with a maximum length of 50 characters.");
                return false;
            }
        } else if (selectedColumn.equalsIgnoreCase("Address") || selectedColumn.equalsIgnoreCase("EMAIL")) {
            String addressValue = value.trim();
            if (addressValue.length() > 255) {
                showError("Invalid value for " + selectedColumn + ". Please enter a string with a maximum length of 255 characters.");
                return false;
            }
        }
        return true; 
    } catch (NumberFormatException e) {
        showError("Invalid numeric value for " + selectedColumn + ". Please enter a valid number.");
        return false;
    }
}


    private boolean validatenewStudentId(int newStudentId) throws SQLException {
    String checkSql = "SELECT COUNT(STUDENTS.student_ID) FROM students WHERE STUDENTS.student_ID = ?";
        int count;
        boolean countflag = false;
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, newStudentId);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
                countflag = count == 0;
            }
        }
        return countflag;
    }

private boolean updateDateOfBirth(int studentId) {
    try {
        String dateFormat = "yyyy-MM-dd";
        String inputDate = upd_value.getText().trim();

        if (!inputDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            showError("Invalid date format. Please use 'YYYY-MM-DD'.");
            return true; // Error occurred
        }

        String sql = "UPDATE students SET DATE_OF_BIRTH = TO_DATE(?, 'YYYY-MM-DD') WHERE STUDENT_ID = ?";
        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, inputDate);
            prepare.setInt(2, studentId);

            int rowsAffectedStudent = prepare.executeUpdate();
            return rowsAffectedStudent <= 0;
        }
    } catch (SQLException ex) {
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        return true; 
    }
}

private boolean updateEmail(int studentId) {
    try {
        String sql = "UPDATE students_email SET EMAIL = ? WHERE STUDENT_ID = ?";
        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, upd_value.getText().trim());
            prepare.setInt(2, studentId);

            int rowsAffectedStudent = prepare.executeUpdate();
            return rowsAffectedStudent <= 0;
        }
    } catch (SQLException ex) {
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        return true;  
    }
}

private boolean updateStudentTable(String selectedColumn, int studentId) {
    try {
        boolean errorOccurred = false;

        String sql = "UPDATE students SET " + selectedColumn + " = ? WHERE STUDENT_ID = ?";
        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, upd_value.getText().trim());
            prepare.setInt(2, studentId);

            int rowsAffectedStudent = prepare.executeUpdate();
            if (rowsAffectedStudent <= 0) {
                errorOccurred = true;
            }
        }

        return errorOccurred;
    } catch (SQLException ex) {
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        return true;  
    }
}
     

private void handleCancelButton(ActionEvent event) {
    Stage stage = (Stage) cancel.getScene().getWindow();
    stage.close();
}
    
}
