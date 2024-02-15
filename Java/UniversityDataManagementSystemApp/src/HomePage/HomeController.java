/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HomePage;

import UniversityDepartments.DepartmentsDataController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import static javax.management.remote.JMXConnectorFactory.connect;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class HomeController implements Initializable {

    @FXML
    private HBox main;
    @FXML
    private VBox root;
    @FXML
    private AnchorPane side_ankerpane;
    @FXML
    private VBox pain_top;
    @FXML
    private AnchorPane main_pane;
    @FXML
    private Button lougout;
    @FXML
    private Button student_info;
    @FXML
    private Button department_info;
    @FXML
    private Button courses_info;
    @FXML
    private Button registerations;
    @FXML
    private Button gpacheck;
    @FXML
    private BorderPane center;
    @FXML
    private VBox additional;
    @FXML
    private Text gpa;
    @FXML
    private Text Depid;
    private Connection connect;
    @FXML
    private ProgressIndicator succ;
    @FXML
    private ScatterChart<String, Double> chart;

    
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
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        department_info.setOnAction(this::handleDepartmentInfoButton);
lougout.setOnAction(this::handleLogoutButton);
                 student_info.setOnAction(this::handlestudentsInfoButton);

                courses_info.setOnAction(this::handleCoursesInfoButton);
                registerations.setOnAction(this::handleregisterationsButton);
                gpacheck.setOnAction(this::handlegpacheckButton);
                
        try {
            connect = universityStudents.Database.connectDB();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
try (PreparedStatement preparedStatement = connect.prepareStatement(
        "SELECT ROUND(AVG(STUDENT_GPA), 2) FROM gpa"
    )) {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                double averageGPA = resultSet.getDouble(1);

                gpa.setText(String.format("GPA: %.2f", averageGPA));
            }
        } 
}       catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }



try (PreparedStatement preparedStatement = connect.prepareStatement(
        "SELECT DEPARTMENT_ID, AVERAGE_GPA FROM (SELECT DEPARTMENT_ID, AVG(STUDENT_GPA) AS AVERAGE_GPA FROM gpa GROUP BY DEPARTMENT_ID ORDER BY AVERAGE_GPA DESC)WHERE ROWNUM = 1"
    )) {
       try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                String departmentId = resultSet.getString("DEPARTMENT_ID");
                double averageGPA = resultSet.getDouble("AVERAGE_GPA");

                Depid.setText(String.format("ID: %s, GPA: %.2f", departmentId, averageGPA));
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
    }

     
 Task<Integer> task = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                int totalStudents = 0;
                int successfulStudents = 0;
                try (PreparedStatement preparedStatement = connect.prepareStatement(
                        "SELECT COUNT(STUDENT_ID) FROM gpa WHERE FINAL_RESULT = 'P'"
                )) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            successfulStudents = resultSet.getInt(1);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    throw ex; 
                }

                try (PreparedStatement preparedStatement = connect.prepareStatement(
                        "SELECT COUNT(STUDENT_ID) FROM gpa"
                )) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            totalStudents = resultSet.getInt(1);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                    throw ex; 
                }

                int maxProgress = totalStudents;

                for (int i = 0; i <= maxProgress; i++) {
                    updateProgress(successfulStudents, totalStudents);
                    Thread.sleep(10); 
                }

                return successfulStudents;
            }
        };

        succ.progressProperty().bind(task.progressProperty());

        task.setOnSucceeded(event -> {
            int successfulStudents = task.getValue();
            Platform.runLater(() -> {
                System.out.println("Query completed successfully. Successful Students: " + successfulStudents);
            });
        });

        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });

        Thread thread = new Thread(task);
        thread.start();
        
        
        
        
         String query = "SELECT DEPARTMENT_ID, ROUND(AVG(STUDENT_GPA), 2) AS AVG_GPA FROM gpa GROUP BY DEPARTMENT_ID";

        try (PreparedStatement preparedStatement = connect.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName("Average GPA | DEPARTMENT ID ");

            while (resultSet.next()) {
                String departmentId = resultSet.getString("DEPARTMENT_ID");
                double avgGPA = resultSet.getDouble("AVG_GPA");

                XYChart.Data<String, Double> dataPoint = new XYChart.Data<>(departmentId, avgGPA);
                series.getData().add(dataPoint);
            }

            chart.getData().add(series);

        } catch (SQLException ex) {
            Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     
     private void handleDepartmentInfoButton(ActionEvent event) {
        
            try {   
                openupdatedepartments();
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        
        
        }

            private void openupdatedepartments() throws IOException {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/UniversityDepartments/DepartmentsData.fxml"));
        Parent root = loader.load();
        UniversityDepartments.DepartmentsDataController DepartmentsDataController = loader.getController();
        DepartmentsDataController.initialize(null, null);

         Stage currentStage = (Stage) department_info.getScene().getWindow();

         currentStage.setTitle("University Management System | Departments Information System ");
        currentStage.setScene(new Scene(root));

    } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
         showError("Error loading Departments Data: " + ex.getMessage());
    }
}

      private void handleCoursesInfoButton(ActionEvent event) {
    try {
        openupdatecourses();
    } catch (IOException ex) {
        Logger.getLogger(DepartmentsDataController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private void openupdatecourses() throws IOException {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/UniversityCourses/coursesData.fxml"));
        Parent root = loader.load();
        UniversityCourses.CoursesDataController CoursesDataController = loader.getController();
        CoursesDataController.initialize(null, null);

         Stage currentStage = (Stage) courses_info.getScene().getWindow();

         currentStage.setTitle("University Management System | Courses Information System ");
        currentStage.setScene(new Scene(root));

    } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        showError("Error loading Courses Data: " + ex.getMessage());
    }
}

            
  private void handleregisterationsButton(ActionEvent event) {
    try {
        openRegistrations();
    } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private void openRegistrations() throws IOException {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/UniversityRegistrations/RegistrationsData.fxml"));
        Parent root = loader.load();
        UniversityRegistrations.RegistrationsDataController RegistrationsDataController = loader.getController();
        RegistrationsDataController.initialize(null, null);

         Stage currentStage = (Stage) registerations.getScene().getWindow();

         currentStage.setTitle("University Management System | Registrations Information System ");
        currentStage.setScene(new Scene(root));

    } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        showError("Error loading Registrations Data: " + ex.getMessage());
    }
}



private void handleLogoutButton(ActionEvent event) {
    logout(event);
}

private void logout(ActionEvent event) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Sign Out Confirmation");
    alert.setHeaderText(null);
    alert.setContentText("Are you sure you want to sign out?");

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    alert.initOwner(stage);

    alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            System.out.println("User signed out");
            stage.close();
            lougout.getScene().getWindow().hide();
        }
    });
}

 

private void handlegpacheckButton(ActionEvent event) {
    try {
        opengpacheck();
    } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

private void opengpacheck() throws IOException {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/UniversityReporting/Reportingpage.fxml"));
        Parent root = loader.load();
        UniversityReporting.ReportingpageController ReportingpageController = loader.getController();
        ReportingpageController.initialize(null, null);

         Stage currentStage = (Stage) gpacheck.getScene().getWindow();

         currentStage.setTitle("University Management System | University Reportings ");
        currentStage.setScene(new Scene(root));

    } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        showError("Error loading Reportings Data: " + ex.getMessage());
    }
}

 private void handlestudentsInfoButton(ActionEvent event) {
    try {
        openupdatestudents();
    } catch (IOException ex) {
        Logger.getLogger(DepartmentsDataController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

 
private void openupdatestudents() throws IOException {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/universityStudents/StudentsData.fxml"));
        Parent root = loader.load();
        universityStudents.StudentsDataController StudentsDataController = loader.getController();
        StudentsDataController.initialize(null, null);

         Stage currentStage = (Stage) student_info.getScene().getWindow();

         currentStage.setTitle("University Management System | Students Information System ");
        currentStage.setScene(new Scene(root));

    } catch (IOException ex) {
        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
        showError("Error loading Students Data: " + ex.getMessage());
    }
}

}

    
