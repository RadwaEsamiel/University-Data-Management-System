/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityReporting;

import java.net.URL;
import java.sql.CallableStatement;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class UpdateGradeController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private TextField StudentID;
    @FXML
    private Button confirm;
    @FXML
    private Button cancel;
    @FXML
    private TextField CourseID;
    @FXML
    private TextField newgrade;
    @FXML
    private Text lll;

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
    private  UniversityReporting.ReportingpageController mainController;


    public void setReportingpageController(UniversityReporting.ReportingpageController mainController) {
        this.mainController = mainController;
    }
    
    
         private int courId;
                  private int stuId;

 

    public void setcourseId(int courId) {
        this.courId = courId;
         System.out.println("Received Student ID: " + courId);
          CourseID.setText(String.valueOf(courId)); 
    }
    
        public void setstudentId(int stuId) {
        this.stuId = stuId;
         System.out.println("Received Student ID: " + stuId);
          StudentID.setText(String.valueOf(stuId)); 
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
                if (StudentID.getText().isEmpty() || CourseID.getText().isEmpty()) {
                    showError("Please Enter the ID of the Course and Student you want to update their related Grade!");
                } else {
                    Connection connect = null;
                    try {
                        try {
                              connect = UniversityReporting.Database.connectDB();
                        } catch (ClassNotFoundException | SQLException ex) {
                            Logger.getLogger(RemovegradesController.class.getName()).log(Level.SEVERE, null, ex);
                            return;
                        }

                        try {
                            Integer.parseInt(StudentID.getText());
                        } catch (NumberFormatException e) {
                            showError("Invalid Data. Please enter a numeric value in Student ID.");
                            return;
                        }
                        
                          try {
                            Integer.parseInt(CourseID.getText());
                        } catch (NumberFormatException e) {
                            showError("Invalid Data. Please enter a numeric value in Course ID.");
                            return;
                        }
                            try {
                            Integer.parseInt(newgrade.getText());
                        } catch (NumberFormatException e) {
                            showError("Invalid Data. Please enter a numeric value in Grade.");
                            return;
                        }

                        String checkSql = "SELECT COUNT(STUDENT_ID) as countstu FROM grades where COURSES_ID=? and STUDENT_ID=?";
                        int count;
                        boolean countflag = false;
                        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
                            checkStatement.setInt(1, Integer.parseInt(CourseID.getText().trim()));
                             checkStatement.setInt(2, Integer.parseInt(StudentID.getText().trim()));

                            try (ResultSet resultSet = checkStatement.executeQuery()) {
                                resultSet.next();
                                count = resultSet.getInt(1);

                            }
                        }

                        if (count == 0) {

                            countflag = true;
                        }

                        if (countflag) {
                            showError("Grade for this student in this course doesn't exist.");
                        } else {
                            boolean errorOccurred = false;

                    try (CallableStatement callableStatement2 = connect.prepareCall("{call UPDATE_GRADES_points(?,?)}")) {
    callableStatement2.setInt(1, Integer.parseInt(StudentID.getText().trim()));
                        callableStatement2.setInt(2, Integer.parseInt(CourseID.getText().trim()));

    callableStatement2.execute();
}
                    
     try (CallableStatement callableStatement3 = connect.prepareCall("{call SET_RESULT_STATUS()}")) {
    callableStatement3.execute();
}


                            String sql
                                    = "update grades set STUDENT_GRADE=?  where COURSES_ID=? and STUDENT_ID=?";

                            try (PreparedStatement prepare = connect.prepareStatement(sql)) {
                                 prepare.setInt(1, Integer.parseInt(newgrade.getText().trim()));
                            prepare.setInt(2, Integer.parseInt(CourseID.getText().trim()));
                             prepare.setInt(3, Integer.parseInt(StudentID.getText().trim()));

                                int rowsAffectedStudent = prepare.executeUpdate();
                                if (rowsAffectedStudent <= 0) {
                                    errorOccurred = true;

                                }

                                if (!errorOccurred) {
                                    showSuccess("Grade updated successfully.");

                                } else {
                                    showError("Failed to update Grade and/or related records. Please try again.");

                                }
                            }

                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(RemovegradesController.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        try {
                            if (connect != null) {
                                connect.close();
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(RemovegradesController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }

            }
    
    
    private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

}

    
