/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityDepartments;

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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import universityStudents.StudentsADDController;
import universityStudents.StudentsDataController;
import universityStudents.StudentsRemoveController;
import universityStudents.studentData;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class DepartmentsDataController implements Initializable {

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
   private TableView<departmentData> Department_data;
    @FXML
    private TableColumn<departmentData, Integer> ALL_Departments_ID;
    @FXML
    private TableColumn<departmentData, String> Department_NAME;
    @FXML
    private TableColumn<departmentData, String> DEPARTMENT_loc;
    @FXML
    private TableColumn<departmentData, Integer> Admission_grade;
    
      PreparedStatement prepare;
    ResultSet result;
    private Connection connect;
    @FXML
    private Button student_info;
    @FXML
    private Button home;
    /**
     * Initializes the controller class.
     */
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

    public ObservableList<departmentData> getdepartmentData() throws SQLException, ClassNotFoundException {
        ObservableList<departmentData> departmentData = FXCollections.observableArrayList();

        String sql = "SELECT DISTINCT D.DEPARTMENT_ID, d.Depart_name, d.REQUIRED_CREDITS,l.building_location FROM UNI_Departments d join Departments_Location l on d.DEPARTMENT_ID=l.DEPARTMENT_ID";

        connect = Database.connectDB();

        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        System.out.println("Executing query: " + sql);

        while (result.next()) {
            Integer Department_ID = result.getInt("DEPARTMENT_ID");
            String Depart_name = result.getString("Depart_name");
            Integer REQUIRED_CREDITS = result.getInt("REQUIRED_CREDITS");
            String building_location = result.getString("building_location");

            departmentData data = new departmentData(Department_ID, Depart_name, building_location, REQUIRED_CREDITS);

            departmentData.add(data);
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
            e.printStackTrace();
        }

        return departmentData;

    }

    /**
     * Initializes the controller class.
     */
    @Override
   public void initialize(URL url, ResourceBundle rb) {
        ALL_Departments_ID.setCellValueFactory(new PropertyValueFactory<>("Department_ID"));
        Department_NAME.setCellValueFactory(new PropertyValueFactory<>("Depart_name"));
        DEPARTMENT_loc.setCellValueFactory(new PropertyValueFactory<>("building_location"));
        Admission_grade.setCellValueFactory(new PropertyValueFactory<>("REQUIRED_CREDITS"));

        try {
            refreshTableView();   
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace(); 
        }

        add_Button.setOnAction(this::handleAddButton);
                remove_Button.setOnAction(this::handleRemoveButton);
              update_Button.setOnAction(this::handleUpdateButton);
                 student_info.setOnAction(this::handlestudentsInfoButton);
        courses_info.setOnAction(this::handleCoursesInfoButton);
lougout.setOnAction(this::handleLogoutButton);
                gpacheck.setOnAction(this::handlegpacheckButton);
home.setOnAction(this::handlehomeButton);
        registerations.setOnAction(this::handleregisterationsButton);

    }

    private void refreshTableView() throws SQLException, ClassNotFoundException {
        ObservableList<departmentData> data = getdepartmentData();
        Department_data.setItems(data);
    }


    private void handleAddButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DepartmentADD.fxml"));
Parent root = loader.load();

            UniversityDepartments.DepartmentADDController DepartmentADDController = loader.getController();
            DepartmentADDController.setDepartmentsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Add Department");
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
            Logger.getLogger(DepartmentsDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
      
private void handleRemoveButton(ActionEvent event) {
    departmentData selecteddep = Department_data.getSelectionModel().getSelectedItem();

    if (selecteddep != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DepartmentRemove.fxml"));
            Parent root = loader.load();

            UniversityDepartments.DepartmentRemoveController DepartmentRemoveController = loader.getController();
            DepartmentRemoveController.setDepartmentId(selecteddep.getDepartment_ID());
            DepartmentRemoveController.setDepartmentsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Remove Department");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DepartmentRemove.fxml"));
            Parent root = loader.load();

            UniversityDepartments.DepartmentRemoveController DepartmentRemoveController = loader.getController();

            DepartmentRemoveController.setDepartmentsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Remove Department");
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

     
private void handleUpdateButton(ActionEvent event) {
    departmentData selecteddep = Department_data.getSelectionModel().getSelectedItem();

    if (selecteddep != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DepartmentUpdate.fxml"));
            Parent root = loader.load();

            UniversityDepartments.DepartmentUpdateController DepartmentUpdateController = loader.getController();
            DepartmentUpdateController.setDepartmentId(selecteddep.getDepartment_ID());
            DepartmentUpdateController.setDepartmentsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Department");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DepartmentUpdate.fxml"));
            Parent root = loader.load();

            UniversityDepartments.DepartmentUpdateController DepartmentUpdateController = loader.getController();

            DepartmentUpdateController.setDepartmentsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Department");
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
