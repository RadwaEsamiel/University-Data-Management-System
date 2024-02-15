
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
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class UnRegisterStudentController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private Button confirm;

    @FXML
    private TextField CoursesID;
    @FXML
    private Button cancel;
    @FXML
    private TextField STUDENT_IDf;

      
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
    
    private int STUDENTID;
  private int courseId;

    public void setSTUDENTID(int STUDENTID) {
        this.STUDENTID = STUDENTID;
         System.out.println("Received Student ID: " + STUDENTID);
          STUDENT_IDf.setText(String.valueOf(STUDENTID)); 
    }
    
    public void setcourseId(int courseId) {
        this.courseId = courseId;
         System.out.println("Received Student ID: " + courseId);
          CoursesID.setText(String.valueOf(courseId)); 
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        confirm.setOnAction(this::handleunRegisterButton);
      cancel.setOnAction(this::handleCancelButton);
    }    
    
   private void handleunRegisterButton(ActionEvent event) {
      
                 if (STUDENT_IDf.getText().isEmpty() || CoursesID.getText().isEmpty()) {
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



int STUDENTIDf;
            try {
                STUDENTIDf = Integer.parseInt(STUDENT_IDf.getText().trim());
            } catch (NumberFormatException e) {
                showError("Invalid Data. Please enter a numeric value in STUDENT ID.");
                return;
            }

            if (!validateSTUDENTId(STUDENTIDf)) {
                showError("STUDENT ID doesn't exist.");
                return;
            }
            
  if (!validateFirst(STUDENTIDf, courseId)) {
    showError("courses ID isn't assigned to this STUDENT ID.");
    return;
}          

    
            executeQueryForAllCourses(STUDENTIDf, courseId);
      

 

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
    
     private boolean validateSTUDENTId(int STUDENTIDf) throws SQLException {
                 String checkSql = "SELECT COUNT(student_ID) FROM students WHERE student_ID=?";

        int count;
        boolean countflag = false;
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, STUDENTIDf);
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
                countflag = count == 0;
            }
        }
        return !countflag;
    }
     
      private boolean validateFirst(int STUDENTIDf, int coursId) throws SQLException {
                  String checkSql = "SELECT COUNT(*) FROM registration WHERE student_ID=? and courses_ID=?";

        int count;
        boolean countflag = false;
        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
                  checkStatement.setInt(1, STUDENTIDf);
            checkStatement.setInt(2, coursId);
          
            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
                countflag = count == 0;
            }
        }
        return !countflag;
    }
private void executeQueryForAllCourses(int STUDENTIDf, int courseId) throws SQLException {

                  boolean errorOccurred = false;
                    boolean errorOccurredloc = false;
                    
                    String Sql = "delete from registration where courses_ID=? and  student_ID=?";

                    prepare = connect.prepareStatement(Sql);
                          prepare.setInt(1, courseId);
                    prepare.setInt(2, STUDENTIDf);

                    int rowsAffecteddep = prepare.executeUpdate();

                    if (rowsAffecteddep <= 0) {
                        errorOccurred = true;
                    }

                 

                    if (!errorOccurred) {
                        showSuccess("course unregistered successfully.");
                    } else {
                        showError("Failed to unregistered course. Please try again.");
                    }

                   
                }
    
     private void handleCancelButton(ActionEvent event) {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }
}

