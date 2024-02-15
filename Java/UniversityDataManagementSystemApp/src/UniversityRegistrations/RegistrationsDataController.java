/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityRegistrations;

import UniversityDepartments.DepartmentsDataController;
import UniversityDepartments.departmentData;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import universityStudents.StudentsDataController;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class RegistrationsDataController implements Initializable {

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
    private Button lougout;
    @FXML
    private Button student_info;
    @FXML
    private Button department_info;
    @FXML
    private Button courses_info;
    @FXML
    private BorderPane center;
    @FXML
    private VBox additional;
    @FXML
    private Button update_Button;
    @FXML
    private TableView<RegisterData> courses_data;
    @FXML
    private TableColumn<RegisterData, Integer> ALL_course_ID;
    @FXML
    private TableColumn<RegisterData, Integer> DEP_ID;
    @FXML
    private TableColumn<RegisterData, Integer> StudentIDC;
    @FXML
    private TableColumn<RegisterData, String> scheduleC;
    @FXML
    private ComboBox<String> Register;
    @FXML
    private ComboBox<String> Unregister;

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

    public ObservableList<RegisterData> getRegisterData() throws SQLException, ClassNotFoundException {
        ObservableList<RegisterData> RegisterData = FXCollections.observableArrayList();

     
        String sql = "select s.COURSES_ID,s.DEPARTMENT_ID,r.STUDENT_ID,s.SCHEDULE from registration r right join Departments_courses s on r.COURSES_ID=S.COURSES_ID and R.DEPARTMENT_ID=S.DEPARTMENT_ID";

        connect = UniversityRegistrations.Database.connectDB();

        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        System.out.println("Executing query: " + sql);

        while (result.next()) {
            Integer student_ID = result.getInt("student_ID");
            Integer courses_ID = result.getInt("courses_ID");
            Integer Department_ID = result.getInt("Department_ID");
            String schedule = result.getString("schedule");

            RegisterData data = new RegisterData(student_ID, courses_ID, Department_ID, schedule);

            RegisterData.add(data);
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

        return RegisterData;

    }

   
    /**
     * Initializes the controller class.
     */
    @Override
   public void initialize(URL url, ResourceBundle rb) {
        StudentIDC.setCellValueFactory(new PropertyValueFactory<>("student_ID"));
        ALL_course_ID.setCellValueFactory(new PropertyValueFactory<>("courses_ID"));
        DEP_ID.setCellValueFactory(new PropertyValueFactory<>("Department_ID"));
        scheduleC.setCellValueFactory(new PropertyValueFactory<>("schedule"));

        try {
            refreshTableView();   
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();  
        }
            courses_info.setOnAction(this::handleCoursesInfoButton);
                    department_info.setOnAction(this::handleDepartmentInfoButton);
                 student_info.setOnAction(this::handlestudentsInfoButton);
lougout.setOnAction(this::handleLogoutButton);
                gpacheck.setOnAction(this::handlegpacheckButton);
home.setOnAction(this::handlehomeButton);
           update_Button.setOnAction(this::handleupdatescheduleButton);

            Register.setItems(
                FXCollections.observableArrayList(
                        "Department",
                        "Student"
                )
        );
         
               Unregister.setItems(
                FXCollections.observableArrayList(
                        "Department",
                        "Student"
                )
        );
               
           Register.setOnAction(e -> handleRegisterSelection(Register.getValue()));
    Unregister.setOnAction(e -> handleUnregisterSelection(Unregister.getValue()));
     
    }

    private void refreshTableView() throws SQLException, ClassNotFoundException {
        ObservableList<RegisterData> data = getRegisterData();
        courses_data.setItems(data);
    }
    
    
    private void handleRegisterSelection(String selectedItem) {
    switch (selectedItem) {
        case "Department":
            openRegisterDepartmentDialog();
            break;
        case "Student":
            openRegisterStudentDialog();
            break;
    }
}
    
       private void handleUnregisterSelection(String selectedItem) {
    switch (selectedItem) {
        case "Department":
            openunRegisterDepartmentDialog();
            break;
        case "Student":
            openunRegisterStudentDialog();
            break;
    }
}

private void openRegisterDepartmentDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("departmentRegister.fxml"));
            Parent root = loader.load();
            
            UniversityRegistrations.DepartmentRegisterController DepartmentRegisterController = loader.getController();
            DepartmentRegisterController.setRegistrationsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Register Course for Department");
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
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    


private void openRegisterStudentDialog() {
  try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RegisterStudent.fxml"));
            Parent root = loader.load();
            
            UniversityRegistrations.RegisterStudentController RegisterStudentController = loader.getController();
            RegisterStudentController.setRegistrationsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Register Course for Student");
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
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
}
    
private void openunRegisterDepartmentDialog() {
          
  RegisterData selecteddep = courses_data.getSelectionModel().getSelectedItem();

    if (selecteddep != null) {
        try {
          FXMLLoader loader = new FXMLLoader(getClass().getResource("unRegisterDepartment.fxml"));
            Parent root = loader.load();
            

            UniversityRegistrations.UnRegisterDepartmentController UnRegisterDepartmentController = loader.getController();
       UnRegisterDepartmentController.setDepartmentId(selecteddep.getDepartment_ID());
             UnRegisterDepartmentController.setcourseId(selecteddep.getCourses_ID());
            UnRegisterDepartmentController.setRegistrationsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("unRegister Department");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("unRegisterStudent.fxml"));
            Parent root = loader.load();

            UniversityRegistrations.UnRegisterStudentController UnRegisterStudentController = loader.getController();
            UnRegisterStudentController.setSTUDENTID(selecteddep.getStudent_ID());
             UnRegisterStudentController.setcourseId(selecteddep.getCourses_ID());
            UnRegisterStudentController.setRegistrationsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("unRegister Department");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

    private void openunRegisterStudentDialog() {
            

              RegisterData selecteddep = courses_data.getSelectionModel().getSelectedItem();

    if (selecteddep != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("unRegisterStudent.fxml"));
            Parent root = loader.load();

            UniversityRegistrations.UnRegisterStudentController UnRegisterStudentController = loader.getController();
            UnRegisterStudentController.setSTUDENTID(selecteddep.getStudent_ID());
             UnRegisterStudentController.setcourseId(selecteddep.getCourses_ID());
            UnRegisterStudentController.setRegistrationsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("unRegister Student ");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("unRegisterStudent.fxml"));
            Parent root = loader.load();

            UniversityRegistrations.UnRegisterStudentController UnRegisterStudentController = loader.getController();
            UnRegisterStudentController.setSTUDENTID(selecteddep.getStudent_ID());
             UnRegisterStudentController.setcourseId(selecteddep.getCourses_ID());
            UnRegisterStudentController.setRegistrationsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("unRegister Student");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}  
     
private void handleupdatescheduleButton(ActionEvent event) {
    RegisterData selecteddep = courses_data.getSelectionModel().getSelectedItem();

    if (selecteddep != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateSchedule.fxml"));
            Parent root = loader.load();

           UniversityRegistrations.UpdateScheduleController UpdateScheduleController = loader.getController();
            UpdateScheduleController.setDepartmentId(selecteddep.getDepartment_ID());
             UpdateScheduleController.setcourseId(selecteddep.getCourses_ID());
            UpdateScheduleController.setRegistrationsDataController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Schedule");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } else {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateSchedule.fxml"));
            Parent root = loader.load();

           UniversityRegistrations.UpdateScheduleController UpdateScheduleController = loader.getController();

            UpdateScheduleController.setRegistrationsDataController(this);


            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Schedule");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            refreshTableView();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegistrationsDataController.class.getName()).log(Level.SEVERE, null, ex);
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
