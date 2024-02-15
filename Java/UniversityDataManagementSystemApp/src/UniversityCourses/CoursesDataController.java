/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityCourses;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import UniversityCourses.CourseADDController;
import UniversityCourses.coursesData;
import UniversityDepartments.DepartmentsDataController;
import UniversityDepartments.departmentData;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import universityStudents.StudentsDataController;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class CoursesDataController implements Initializable {

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
    private Button gpacheck;
    @FXML
    private Button registerations;
    @FXML
    private Button lougout;
    @FXML
    private Button student_info;
    @FXML
    private Button department_info;
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
    private TableView<coursesData> courses_data;
    @FXML
    private TableColumn<coursesData, Integer> ALL_course_ID;
    @FXML
    private TableColumn<coursesData, String> course_NAME;
    @FXML
    private TableColumn<coursesData, Integer> succ_grade;
    @FXML
    private TableColumn<coursesData, Integer> credit_hours;
 

    PreparedStatement prepare;
    ResultSet result;
    private Connection connect;
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

     public ObservableList<coursesData> getcoursesData() throws SQLException, ClassNotFoundException {
        ObservableList<coursesData> coursesData = FXCollections.observableArrayList();

        String sql = " SELECT DISTINCT courses_ID, course_name, Success_Grade, credit_hours FROM courses ";


        connect = Database.connectDB();

        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        System.out.println("Executing query: " + sql);

        while (result.next()) {
            Integer courses_ID = result.getInt("courses_ID");
            String course_name = result.getString("course_name");
            Integer Success_Grade = result.getInt("Success_Grade");
            Integer credit_hours = result.getInt("credit_hours");

            coursesData data = new coursesData(courses_ID, course_name, Success_Grade, credit_hours);

            coursesData.add(data);
        }

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
        }

        return coursesData;

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
  ALL_course_ID.setCellValueFactory(new PropertyValueFactory<>("courses_ID"));

        course_NAME.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        succ_grade.setCellValueFactory(new PropertyValueFactory<>("Success_Grade"));

        credit_hours.setCellValueFactory(new PropertyValueFactory<>("credit_hours"));

         try {
            refreshTableView();  // Initial population of the TableView
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); // or log the error
        }
          add_Button.setOnAction(this::handleAddButton);
                         remove_Button.setOnAction(this::handleRemoveButton);
              update_Button.setOnAction(this::handleUpdateButton);
        department_info.setOnAction(this::handleDepartmentInfoButton);
                        gpacheck.setOnAction(this::handlegpacheckButton);
home.setOnAction(this::handlehomeButton);
                 student_info.setOnAction(this::handlestudentsInfoButton);
        registerations.setOnAction(this::handleregisterationsButton);
lougout.setOnAction(this::handleLogoutButton);
    }
    
          private void refreshTableView() throws SQLException, ClassNotFoundException {
        ObservableList<coursesData> data = getcoursesData();
        courses_data.setItems(data);
    }
          

       
    
private void handleAddButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseADD.fxml"));
Parent root = loader.load();

           CourseADDController courseADDController = loader.getController();
courseADDController.setCoursesDataController(this);


            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Course");
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
            Logger.getLogger(CoursesDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

     
private void handleRemoveButton(ActionEvent event) {
    coursesData selecteddep = courses_data.getSelectionModel().getSelectedItem();

    if (selecteddep != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseRemove.fxml"));
            Parent root = loader.load();

               CourseRemoveController CourseRemoveController = loader.getController();
                     CourseRemoveController.setcoursetId(selecteddep.getCourses_ID());
CourseRemoveController.setCoursesDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Remove Course");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(CourseRemoveController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CourseRemoveController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseRemove.fxml"));
            Parent root = loader.load();

           CourseRemoveController CourseRemoveController = loader.getController();
CourseRemoveController.setCoursesDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Remove Course");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(CoursesDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CoursesDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

        
private void handleUpdateButton(ActionEvent event) {
    coursesData selecteddep = courses_data.getSelectionModel().getSelectedItem();

    if (selecteddep != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseUpdate.fxml"));
            Parent root = loader.load();

               CourseUpdateController CourseUpdateController = loader.getController();
                     CourseUpdateController.setcoursetId(selecteddep.getCourses_ID());
CourseUpdateController.setCoursesDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Course");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(CoursesDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CoursesDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseUpdate.fxml"));
            Parent root = loader.load();

           CourseUpdateController CourseUpdateController = loader.getController();
CourseUpdateController.setCoursesDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Remove Course");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(CoursesDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(CoursesDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        Logger.getLogger(DepartmentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        showError("Error loading Students Data: " + ex.getMessage());
    }
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

    
}
