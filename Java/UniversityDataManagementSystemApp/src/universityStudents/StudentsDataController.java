/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universityStudents;

import UniversityDepartments.DepartmentsDataController;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import universityStudents.Database;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class StudentsDataController implements Initializable {

    PreparedStatement prepare;
    ResultSet result;
    private Connection connect;
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
    private Button department_info;
    @FXML
    private Button gpacheck;
    @FXML
    private Button registerations;
    @FXML
    private Button courses_info;
    @FXML
    private Button lougout;
    @FXML
    private BorderPane center;
    @FXML
    private VBox additional;
    @FXML
    private Button add_Button;
    @FXML
    private Button update_Button;
    @FXML
    private Button remove_Button;
    @FXML
    private TableView<studentData> students_data;
    @FXML
    private TableColumn<studentData, String> STUDENT_NAME;
    @FXML
    private TableColumn<studentData, Integer> DEPARTMENT_student;
    @FXML
    private TableColumn<studentData, Integer> ALL_STUDENT_ID;
    @FXML
    private TableColumn<studentData, String> STUDENT_FIRST_NAME;
    @FXML
    private Button home;
 

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


    public ObservableList<studentData> getstudentData() throws SQLException, ClassNotFoundException {
        ObservableList<studentData> studentData = FXCollections.observableArrayList();

        String sql = "SELECT DISTINCT STUDENT_ID, LAST_NAME,FIRST_NAME,DEPARTMENT_ID FROM students";

        connect = Database.connectDB();

        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        System.out.println("Executing query: " + sql);

        while (result.next()) {
            Integer STUDENT_ID = result.getInt("STUDENT_ID");
            String LAST_NAME = result.getString("LAST_NAME");
             String FIRST_NAME = result.getString("FIRST_NAME");
            Integer DEPARTMENT_ID = result.getInt("DEPARTMENT_ID");
           

            studentData data = new studentData(STUDENT_ID, LAST_NAME,FIRST_NAME, DEPARTMENT_ID);

            studentData.add(data);
        }

        // Close the resources
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

        return studentData;

    }

    /**
     *
     *
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        STUDENT_FIRST_NAME.setCellValueFactory(new PropertyValueFactory<>("LAST_NAME"));
        STUDENT_NAME.setCellValueFactory(new PropertyValueFactory<>("FIRST_NAME"));
        ALL_STUDENT_ID.setCellValueFactory(new PropertyValueFactory<>("STUDENT_ID"));
        DEPARTMENT_student.setCellValueFactory(new PropertyValueFactory<>("DEPARTMENT_ID"));

        try {
            refreshTableView();   
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();  
        }
        
        department_info.setOnAction(this::handleDepartmentInfoButton);
lougout.setOnAction(this::handleLogoutButton);

        update_Button.setOnAction(this::handleUpdateButton);
        remove_Button.setOnAction(this::handleRemoveButton);
        add_Button.setOnAction(this::handleAddButton);
                courses_info.setOnAction(this::handleCoursesInfoButton);
                registerations.setOnAction(this::handleregisterationsButton);
                gpacheck.setOnAction(this::handlegpacheckButton);
home.setOnAction(this::handlehomeButton);
    }

    private void refreshTableView() throws SQLException, ClassNotFoundException {
        ObservableList<studentData> data = getstudentData();
        students_data.setItems(data);
    }
    
    
private void handleUpdateButton(ActionEvent event) {
    studentData selectedStudent = students_data.getSelectionModel().getSelectedItem();

    if (selectedStudent != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentsUpdate.fxml"));
            Parent root = loader.load();

            StudentsUpdateController studentsUpdateController = loader.getController();
            studentsUpdateController.setStudentId(selectedStudent.getSTUDENT_ID());
            studentsUpdateController.setMainController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Student");
       stage.setScene(new Scene(root));

            stage.setOnHidden(e -> {
                try {
                    refreshTableView();
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            });

            stage.showAndWait();
    } catch (IOException ex) {
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
   else {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentsUpdate.fxml"));
            Parent root = loader.load();

            StudentsUpdateController studentsUpdateController = loader.getController();

            studentsUpdateController.setMainController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Student");
             stage.setScene(new Scene(root));

            stage.setOnHidden(e -> {
                try {
                    refreshTableView();
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            });

            stage.showAndWait();
    } catch (IOException ex) {
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
    }

  
    }
}


   
private void handleRemoveButton(ActionEvent event) {
    studentData selectedStudent = students_data.getSelectionModel().getSelectedItem();

    if (selectedStudent != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentsRemove.fxml"));
            Parent root = loader.load();

            StudentsRemoveController StudentsRemoveController = loader.getController();
            StudentsRemoveController.setStudentId(selectedStudent.getSTUDENT_ID());
            StudentsRemoveController.setMainController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Remove Student");
           stage.setScene(new Scene(root));

            stage.setOnHidden(e -> {
                try {
                    refreshTableView();
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            });

            stage.showAndWait();
    } catch (IOException ex) {
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
    }

  
    } else {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentsRemove.fxml"));
            Parent root = loader.load();
            StudentsRemoveController StudentsRemoveController = loader.getController();
            StudentsRemoveController.setMainController(this);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Remove Student");
            stage.setScene(new Scene(root));
            
            stage.setOnHidden(e -> {
                try {
                    refreshTableView();
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            });

            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
  


  private void handleAddButton(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentsADD.fxml"));
        Parent root = loader.load();

        StudentsADDController StudentsADDController = loader.getController();
        StudentsADDController.setMainController(this);

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Add Student");  // Adjusted the title to "Add Student"
        stage.setScene(new Scene(root));

            stage.setOnHidden(e -> {
                try {
                    refreshTableView();
                } catch (SQLException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            });

            stage.showAndWait();
    } catch (IOException ex) {
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
    }
}
  
  
  
     private void handleDepartmentInfoButton(ActionEvent event) {
        
            try {   
                openupdatedepartments();
            } catch (IOException ex) {
                Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(DepartmentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        showError("Error loading Courses Data: " + ex.getMessage());
    }
}

            
  private void handleregisterationsButton(ActionEvent event) {
    try {
        openRegistrations();
    } catch (IOException ex) {
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        showError("Error loading Registrations Data: " + ex.getMessage());
    }
}



private void handleLogoutButton(ActionEvent event) {
    logout(event);
}

private void logout(ActionEvent event) {
    Alert alert = new Alert(AlertType.CONFIRMATION);
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

   private void handlehomeButton(ActionEvent event) {
    try {
        openhomepage();
    } catch (IOException ex) {
        Logger.getLogger(DepartmentsDataController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

 
private void openhomepage() throws IOException {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/HomePage/Home.fxml"));
        Parent root = loader.load();
        HomePage.HomeController HomeController = loader.getController();
        HomeController.initialize(null, null);

         Stage currentStage = (Stage) home.getScene().getWindow();

         currentStage.setTitle("University Management System | Home Page ");
        currentStage.setScene(new Scene(root));

    } catch (IOException ex) {
        Logger.getLogger(DepartmentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        showError("Error loading Main Page: " + ex.getMessage());
    }
}

private void handlegpacheckButton(ActionEvent event) {
    try {
        opengpacheck();
    } catch (IOException ex) {
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
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
        Logger.getLogger(StudentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        showError("Error loading Reportings Data: " + ex.getMessage());
    }
}
}