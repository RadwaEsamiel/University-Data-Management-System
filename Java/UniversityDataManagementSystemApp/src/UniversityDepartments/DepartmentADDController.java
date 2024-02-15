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
public class DepartmentADDController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private Button confirm;
    @FXML
    private Label DEPID;
    @FXML
    private TextField Departments_name;
    @FXML
    private TextField ADD_grade;
    @FXML
    private TextField Department_Loc;
    @FXML
    private Label FNAME;
    @FXML
    private Label LNAME;
    @FXML
    private Label FNAME1;
    @FXML
    private Button cancel;
    @FXML
    private TextField DEPARTMENT_ID;

    

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
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      confirm.setOnAction(this::handleAddButton);
      cancel.setOnAction(this::handleCancelButton);
    }  
    
     private void handleAddButton(ActionEvent event) {
        try {
            if (DEPARTMENT_ID.getText().isEmpty() || Departments_name.getText().isEmpty() || ADD_grade.getText().isEmpty()) {
                showError("Please fill all fields to Add DEPARTMENT!");
                return;
            }

            try {
                try {
                    connect = Database.connectDB();
                } catch (SQLException ex) {
                    Logger.getLogger(DepartmentADDController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DepartmentsDataController.class.getName()).log(Level.SEVERE, null, ex);
            }

            int ADDgrade;
            int departmentId;

            try {
                ADDgrade = Integer.parseInt(ADD_grade.getText().trim());
                departmentId = Integer.parseInt(DEPARTMENT_ID.getText().trim());
            } catch (NumberFormatException e) {
                showError("Invalid Data. Please enter numeric values in Admission grade and DEPARTMENT ID.");
                return;
            }

            String checkSql = "SELECT COUNT(UNI_Departments.DEPARTMENT_ID), COUNT(Departments_Location.DEPARTMENT_ID) "
                    + "FROM Departments_Location join UNI_Departments on Departments_Location.DEPARTMENT_ID=UNI_Departments.DEPARTMENT_ID "
                    + "where UNI_Departments.DEPARTMENT_ID=?";
            
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, departmentId);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                  boolean errorOccurred = false;
                    boolean errorOccurredloc = false;
                    
                    String depSql = "INSERT INTO UNI_Departments (Department_ID, Depart_name, REQUIRED_CREDITS) VALUES (?, ?, ?)";
                    String locSql = "INSERT INTO Departments_Location (building_location, Department_ID) VALUES (?, ?)";

                    prepare = connect.prepareStatement(depSql);
                    prepare.setInt(1, departmentId);
                    prepare.setString(2, Departments_name.getText().trim());
                    prepare.setInt(3, ADDgrade);

                    int rowsAffecteddep = prepare.executeUpdate();

                    prepare.clearParameters();

                    prepare = connect.prepareStatement(locSql);
                    prepare.setString(1, Department_Loc.getText().trim());
                    prepare.setInt(2, departmentId);

                    int rowsAffectedloc = prepare.executeUpdate();

                    if (rowsAffecteddep <= 0) {
                        errorOccurred = true;
                    }

                    if (rowsAffectedloc <= 0) {
                        errorOccurredloc = true;
                    }

                    if (!errorOccurred) {
                        showSuccess("Department added successfully.");
                    } else {
                        showError("Failed to add Department. Please try again.");
                    }

                    if (!errorOccurredloc) {
                        showSuccess("Building location added successfully with Department.");
                    } else {
                        showError("Failed to assign location to Department. Please try again later.");
                    }
                }
            else {
                showError("Department ID already exists.");
            }
            } catch (SQLException ex) {
                Logger.getLogger(DepartmentADDController.class.getName()).log(Level.SEVERE, null, ex);
            }
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