/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityDepartments;

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
public class DepartmentUpdateController implements Initializable {

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
    private TextField update_dep;
    
    
    
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
    private  UniversityDepartments.DepartmentsDataController mainController;


    public void setDepartmentsDataController(UniversityDepartments.DepartmentsDataController mainController) {
        this.mainController = mainController;
    }
    
    
         private int DepartmentId;
 

    public void setDepartmentId(int DepartmentId) {
        this.DepartmentId = DepartmentId;
         System.out.println("Received Student ID: " + DepartmentId);
          update_dep.setText(String.valueOf(DepartmentId)); 
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         upd_field.setItems(
                FXCollections.observableArrayList(
                        "Department_ID",
                        "Depart_name",
                        "building_location",
                        "REQUIRED_CREDITS"
                )
        );
         
      confirm.setOnAction(this::handleUpdateButton);
      cancel.setOnAction(this::handleCancelButton);
    }
    
    private void handleUpdateButton(ActionEvent event) {
        String selectedColumn = upd_field.getValue();

        if (selectedColumn == null || upd_value.getText().isEmpty() || update_dep.getText().isEmpty()) {
            showError("Please select a column and fill all fields required to update data!");
            return;
        }

        try {
            connect = Database.connectDB();

            int departmentId;
            try {
                departmentId = Integer.parseInt(update_dep.getText().trim());
            } catch (NumberFormatException e) {
                showError("Invalid Data. Please enter a numeric value in DEPARTMENT_ID.");
                return;
            }

            if (!validateDepartmentId(departmentId)) {
                showError("Department ID doesn't exist.");
                return;
            }
            
             if ("Department_ID".equalsIgnoreCase(selectedColumn)) {
            int newDepartmentId;
            try {
                newDepartmentId = Integer.parseInt(upd_value.getText().trim());
            } catch (NumberFormatException e) {
                showError("Invalid Data. Please enter a numeric value for the new DEPARTMENT_ID.");
                return;
            }

            if (!validatenewDepartmentId(newDepartmentId)) {
                showError("New Department ID already exists. Please enter a different value.");
                return;
            }
        }

            if (!validateDataTypes(selectedColumn, upd_value.getText().trim())) {
                return;  
            }

            
            boolean errorOccurred = false;

            if ("building_location".equals(selectedColumn)) {
                updateBuildingLocation(departmentId);
            } else {
                updateUniDepartments(selectedColumn, departmentId, errorOccurred);
            }

            if (errorOccurred) {
                showError("Failed to update Department Data. Please try again.");
            } else {
                showSuccess("Department Data updated successfully.");
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


    private boolean validatenewDepartmentId(int newDepartmentId) throws SQLException {
        String checkSql = "SELECT COUNT(UNI_Departments.DEPARTMENT_ID), COUNT(Departments_Location.DEPARTMENT_ID)  FROM Departments_Location join UNI_Departments on Departments_Location.DEPARTMENT_ID=UNI_Departments.DEPARTMENT_ID where UNI_Departments.DEPARTMENT_ID=?";
        int count;
        boolean countflag = false;
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, newDepartmentId);
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
            if (selectedColumn.equalsIgnoreCase("Department_ID") || selectedColumn.equalsIgnoreCase("Admission_grade")) {
                Integer.parseInt(value); // Check if it's a valid integer
            } else if (selectedColumn.equalsIgnoreCase("Depart_name")) {

                if (value.isEmpty() || value.length() > 50) {
                    showError("Invalid value for " + selectedColumn + ". Please enter a non-empty string with a maximum length of 50 characters.");
                    return false;
                }
            } else if (selectedColumn.equalsIgnoreCase("building_location")) {

                if (value.length() > 255) {
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

    private void updateBuildingLocation(int departmentId) {
        try {
            String sql = "UPDATE Departments_Location SET building_location = ? WHERE Department_ID = ?";
            try (PreparedStatement prepare = connect.prepareStatement(sql)) {
                prepare.setString(1, upd_value.getText().trim());
                prepare.setInt(2, departmentId);
                int rowsAffected = prepare.executeUpdate();
                if (rowsAffected <= 0) {
                    showError("Failed to update Department Data. Please try again.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentUpdateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void updateUniDepartments(String selectedColumn, int departmentId, boolean errorOccurred) {
        String sql = "UPDATE UNI_Departments SET " + selectedColumn + " = ? WHERE Department_ID = ?";
        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, upd_value.getText().trim());
            prepare.setInt(2, departmentId);
            int rowsAffected = prepare.executeUpdate();
            if (rowsAffected <= 0) {
                errorOccurred = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DepartmentUpdateController.class.getName()).log(Level.SEVERE, null, ex);
            errorOccurred = true;
        }
    }
    
     
    private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

}
