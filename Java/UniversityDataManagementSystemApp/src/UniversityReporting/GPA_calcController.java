package UniversityReporting;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GPA_calcController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private ComboBox<String> gradescom;
    @FXML
    private TextField searchid;
    @FXML
    private Button SearchButton;
    @FXML
    private Text gpa;

    private Connection connect;
    private UniversityReporting.ReportingpageController mainController;

    public void setReportingpageController(UniversityReporting.ReportingpageController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       SearchButton.setOnAction(this::handleSearchButton);

    gradescom.setItems(
            FXCollections.observableArrayList(
                    "Department",
                    "Student"
            )
    );

    }

  
    

    private void handleSearchButton(javafx.event.ActionEvent event) {
     if (event.getSource() != SearchButton) {
        return;
    }

    String selectedColumn = gradescom.getValue();

    if (selectedColumn == null) {
        showError("Please select an option first!");
        return;
    }

    if (searchid.getText().isEmpty()) {
        showError("Please insert the ID to Calculate!");
        return;
    }

    try {
        connect = UniversityReporting.Database.connectDB();
        if ("Department".equals(selectedColumn)) {
            openGPADepartmentDialog();
        } else if ("Student".equals(selectedColumn)) {
            openGPAStudentDialog();
        } else {
            showError("Invalid option selected!");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        showError("An error occurred while connecting to the database!");
    } 
}

    

      


    private void openGPADepartmentDialog() throws SQLException {
    if (searchid.getText().isEmpty()) {
        showError("Please enter a valid ID!");
        return;
    }

    int departmentId;
    try {
         departmentId = Integer.parseInt(searchid.getText().trim());
    } catch (NumberFormatException e) {
        showError("Invalid ID format. Please enter a valid number.");
        return;
    }
 String checkSql = "SELECT COUNT(STUDENT_ID) as countstu FROM gpa where Department_ID=?";
                        int count;
                        boolean countflag = false;
                        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
                             checkStatement.setInt(1, Integer.parseInt(searchid.getText().trim()));

                            try (ResultSet resultSet = checkStatement.executeQuery()) {
                                resultSet.next();
                                count = resultSet.getInt(1);

                            }
                        }

                        if (count == 0) {

                            countflag = true;
                        }

                        if (countflag) {
                            showError("Grade for this Department ID in doesn't exist in the GPA table.");
                        } else {
                            boolean errorOccurred = false;


        try (CallableStatement callableStatement1 = connect.prepareCall("{call students_totregistered_hours(?, ?)}")) {
            try (PreparedStatement studentIdsStatement = connect.prepareStatement("SELECT STUDENT_ID FROM Students WHERE DEPARTMENT_ID = ?")) {
                studentIdsStatement.setInt(1, departmentId);
                try (ResultSet studentIdsResultSet = studentIdsStatement.executeQuery()) {
                    while (studentIdsResultSet.next()) {
                        int studentId = studentIdsResultSet.getInt("STUDENT_ID");
                        callableStatement1.setInt(1, departmentId);
                        callableStatement1.setInt(2, studentId);
                        callableStatement1.execute();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while executing students_total registered hours!");
            return;
        }

        try (CallableStatement callableStatement2 = connect.prepareCall("{call GPA_calculator(?)}")) {
            try (PreparedStatement studentIdsStatement = connect.prepareStatement("SELECT STUDENT_ID FROM Students WHERE DEPARTMENT_ID = ?")) {
                studentIdsStatement.setInt(1, departmentId);
                try (ResultSet studentIdsResultSet = studentIdsStatement.executeQuery()) {
                    while (studentIdsResultSet.next()) {
                        int studentId = studentIdsResultSet.getInt("STUDENT_ID");
                        callableStatement2.setInt(1, studentId);
                        callableStatement2.execute();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while executing GPA_calculator!");
            return;
        }

        try (CallableStatement callableStatement3 = connect.prepareCall("{call students_Final_result(?)}")) {
            try (PreparedStatement studentIdsStatement = connect.prepareStatement("SELECT STUDENT_ID FROM Students WHERE DEPARTMENT_ID = ?")) {
                studentIdsStatement.setInt(1, departmentId);
                try (ResultSet studentIdsResultSet = studentIdsStatement.executeQuery()) {
                    while (studentIdsResultSet.next()) {
                        int studentId = studentIdsResultSet.getInt("STUDENT_ID");
                        callableStatement3.setInt(1, studentId);
                        callableStatement3.execute();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while executing students_Final_result!");
            return;
        }

        try (PreparedStatement preparedStatement = connect.prepareStatement(
                "SELECT STUDENT_ID, Department_ID, STUDENT_GPA, FINAL_RESULT FROM GPA WHERE Department_ID = ?"
        )) {
            preparedStatement.setInt(1, departmentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder gpaResultBuilder = new StringBuilder();

                while (resultSet.next()) {
                    String studentId = resultSet.getString("STUDENT_ID");
                    String studentGPA = resultSet.getString("STUDENT_GPA");
                    String finalResult = resultSet.getString("FINAL_RESULT");

                  gpaResultBuilder.append("Department ID you entered: ").append(departmentId)
            .append("\nALL Student ID in this Department: ").append(studentId)
            .append("\nStudent achieved GPA: ").append(studentGPA)
            .append("\nAnd Student Final Result: ").append(finalResult)
            .append("\n\n"); 

                }

                 gpa.setText(gpaResultBuilder.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                showError("An error occurred while fetching GPA results!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while preparing the statement for GPA results!");
        }
    
     finally {
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
    
      private void openGPAStudentDialog() throws SQLException {
    if (searchid.getText().isEmpty()) {
        showError("Please enter a valid ID!");
        return;
    }

    int studentId;
    try {
   
        studentId = Integer.parseInt(searchid.getText().trim());
    } catch (NumberFormatException e) {
        showError("Invalid ID format. Please enter a valid number.");
        return;
    }

String checkSql = "SELECT COUNT(STUDENT_ID) as countstu FROM gpa where STUDENT_ID=?";
                        int count;
                        boolean countflag = false;
                        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
                             checkStatement.setInt(1, Integer.parseInt(searchid.getText().trim()));

                            try (ResultSet resultSet = checkStatement.executeQuery()) {
                                resultSet.next();
                                count = resultSet.getInt(1);

                            }
                        }

                        if (count == 0) {

                            countflag = true;
                        }

                        if (countflag) {
                            showError("Grade for this STUDENT ID doesn't exist in the GPA table.");
                        } else {
                            boolean errorOccurred = false;

        try (CallableStatement callableStatement1 = connect.prepareCall("{call students_totregistered_hours(?, ?)}")) {
            try (PreparedStatement studentIdsStatement = connect.prepareStatement("SELECT DEPARTMENT_ID FROM Students WHERE STUDENT_ID = ?")) {
                studentIdsStatement.setInt(1, studentId);
                try (ResultSet studentIdsResultSet = studentIdsStatement.executeQuery()) {
                    while (studentIdsResultSet.next()) {
                        int depId = studentIdsResultSet.getInt("DEPARTMENT_ID");
                        callableStatement1.setInt(1, studentId);
                        callableStatement1.setInt(2, depId);
                        callableStatement1.execute();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while executing students registered hours!");
            return;
        }

        try (CallableStatement callableStatement2 = connect.prepareCall("{call GPA_calculator(?)}")) {
            try (PreparedStatement studentIdsStatement = connect.prepareStatement("SELECT DEPARTMENT_ID FROM Students WHERE STUDENT_ID = ?")) {
                studentIdsStatement.setInt(1, studentId);
                try (ResultSet studentIdsResultSet = studentIdsStatement.executeQuery()) {
                    while (studentIdsResultSet.next()) {
                        int depId = studentIdsResultSet.getInt("DEPARTMENT_ID");
                        callableStatement2.setInt(1, studentId);
                        callableStatement2.execute();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while executing GPA calculator!");
            return;
        }

        try (CallableStatement callableStatement3 = connect.prepareCall("{call students_Final_result(?)}")) {
            try (PreparedStatement studentIdsStatement = connect.prepareStatement("SELECT DEPARTMENT_ID FROM Students WHERE STUDENT_ID = ?")) {
                studentIdsStatement.setInt(1, studentId);
                try (ResultSet studentIdsResultSet = studentIdsStatement.executeQuery()) {
                    while (studentIdsResultSet.next()) {
                            int depId = studentIdsResultSet.getInt("DEPARTMENT_ID");
                callableStatement3.setInt(1, studentId);
                        callableStatement3.execute();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while executing students Final result!");
            return;
        }

        try (PreparedStatement preparedStatement = connect.prepareStatement(
                "SELECT STUDENT_ID, Department_ID, STUDENT_GPA, FINAL_RESULT FROM GPA WHERE STUDENT_ID = ?"
        )) {
            preparedStatement.setInt(1, studentId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                StringBuilder gpaResultBuilder = new StringBuilder();

                while (resultSet.next()) {
                    String depid = resultSet.getString("Department_ID");
                    String studentGPA = resultSet.getString("STUDENT_GPA");
                    String finalResult = resultSet.getString("FINAL_RESULT");

                     gpaResultBuilder.append("Student ID you entered: ").append(studentId)
            .append("\nAT Department ID: ").append(depid)
            .append("\nThe GPA student achieved: ").append(studentGPA)
            .append("\nAnd Final Result: ").append(finalResult)
            .append("\n\n");
                }

       
                gpa.setText(gpaResultBuilder.toString());
            } catch (SQLException e) {
                e.printStackTrace();
                showError("An error occurred while fetching GPA results!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while preparing the statement for GPA results!");
        }
    
                          finally {
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
    }
}
