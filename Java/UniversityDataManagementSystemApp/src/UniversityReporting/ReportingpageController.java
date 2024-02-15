/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityReporting;

import UniversityDepartments.DepartmentsDataController;
import UniversityDepartments.departmentData;
import UniversityRegistrations.RegistrationsDataController;
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
import javafx.scene.control.TextField;
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
public class ReportingpageController implements Initializable {

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
    private BorderPane center;
    @FXML
    private VBox additional;
    @FXML
    private Button remove_Button;
    @FXML
    private Button update_Button;
    @FXML
    private TableColumn<gradesData, Integer> STUDENT_ID;
    @FXML
    private TableColumn<gradesData, Integer> COURSES_ID;
    @FXML
    private TableColumn<gradesData, Integer> GRADE;
    @FXML
    private TableColumn<gradesData, Integer> POINT;
    @FXML
    private TableColumn<gradesData, String> RESULT;
    
   PreparedStatement prepare;
    ResultSet result;
    private Connection connect;
    @FXML
    private TableView<gradesData> gradesTable;

    @FXML
    private ComboBox<String> gradescom;
    @FXML
    private TextField searchid;
    @FXML
    private Button SearchButton;
    @FXML
    private Button GPADat;
    @FXML
    private Button GPA_calc;
    @FXML
    private Button ReadReport;
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

    public ObservableList<gradesData> getgradesData() throws SQLException, ClassNotFoundException {
        ObservableList<gradesData> gradesData = FXCollections.observableArrayList();

        String sql = "select DISTINCT COURSES_ID, STUDENT_ID,STUDENT_GRADE, GRADE_POINT, RESULT_STATUS from grades" ;
                
        connect = UniversityReporting.Database.connectDB();

        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        System.out.println("Executing query: " + sql);

        while (result.next()) {
            Integer COURSES_ID = result.getInt("COURSES_ID");
            Integer STUDENT_ID = result.getInt("STUDENT_ID");
            Integer STUDENT_GRADE = result.getInt("STUDENT_GRADE");
            Integer GRADE_POINT = result.getInt("GRADE_POINT");
            String RESULT_STATUS = result.getString("RESULT_STATUS");

            gradesData data = new gradesData(COURSES_ID, STUDENT_ID, STUDENT_GRADE, GRADE_POINT, RESULT_STATUS);

            gradesData.add(data);
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

        return gradesData;

    }

    /**
     * Initializes the controller class.
     */
    @Override
   public void initialize(URL url, ResourceBundle rb) {
        STUDENT_ID.setCellValueFactory(new PropertyValueFactory<>("STUDENT_ID"));
        COURSES_ID.setCellValueFactory(new PropertyValueFactory<>("COURSES_ID"));
        GRADE.setCellValueFactory(new PropertyValueFactory<>("STUDENT_GRADE"));
        POINT.setCellValueFactory(new PropertyValueFactory<>("GRADE_POINT"));
        RESULT.setCellValueFactory(new PropertyValueFactory<>("RESULT_STATUS"));

         gradescom.setItems(
                FXCollections.observableArrayList(
                        "Department",
                        "STUDENT",
                        "COURSES"
                        
                )
        );
         
  
         
        try {
            refreshTableView();   
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();  
        }
          remove_Button.setOnAction(this::handleRemoveButton);
              update_Button.setOnAction(this::handleUpdateButton);
courses_info.setOnAction(this::handleCoursesInfoButton);
                    department_info.setOnAction(this::handleDepartmentInfoButton);
                 student_info.setOnAction(this::handlestudentsInfoButton);
lougout.setOnAction(this::handleLogoutButton);
SearchButton.setOnAction(this::handlesearchButton);
GPADat.setOnAction(this::handleGPAtableButton);
ReadReport.setOnAction(this::openReportsDialog);
GPA_calc.setOnAction(this::openGPADepartmentDialog);
        registerations.setOnAction(this::handleregisterationsButton);
home.setOnAction(this::handlehomeButton);
    }

    private void refreshTableView() throws SQLException, ClassNotFoundException {
        ObservableList<gradesData> data = getgradesData();
        gradesTable.setItems(data);
    }
    

    
 
private void openGPADepartmentDialog(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GPA_calc.fxml"));
            Parent root = loader.load();
            
            UniversityReporting.GPA_calcController GPA_calcController = loader.getController();
            GPA_calcController.setReportingpageController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("GPA for Department");
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
    



       
private void handlesearchButton(ActionEvent event) {
        String selectedColumn = gradescom.getValue();

        if (selectedColumn == null || searchid.getText().isEmpty()) {
            showError("Please select an option and insert its ID first to Search!");
            return;
        }

        try {
            connect = UniversityReporting.Database.connectDB();

            if ("Department".equals(selectedColumn)) {
                handleDepartmentSearch();
            } else if ("STUDENT".equals(selectedColumn)){ 
                    handleStudentSearch();
            }
            else if ("COURSES".equals(selectedColumn)){ 
                    handleCOURSESSearch();}
            else
                    {
                showError("Invalid selection for search!");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            showError("An error occurred while connecting to the database!");
        } finally {
            closeDatabaseResources();
        }
    }

    private void handleDepartmentSearch() throws SQLException {
        String checkSql = "SELECT COUNT(STUDENT_ID) as countstu FROM grades where Department_ID=?";
        int count;

        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, Integer.parseInt(searchid.getText().trim()));

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
            }

            if (count == 0) {
                showError("Grade for students in this Department in this course doesn't exist.");
                return;
            }
        }

        ObservableList<gradesData> searchData = FXCollections.observableArrayList();
        String sql = "select DISTINCT COURSES_ID, STUDENT_ID, STUDENT_GRADE, GRADE_POINT, RESULT_STATUS from grades where Department_ID=?";

        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setInt(1, Integer.parseInt(searchid.getText().trim()));

            try (ResultSet result = prepare.executeQuery()) {
                System.out.println("Executing query: " + sql);

                while (result.next()) {
                    Integer COURSES_ID = result.getInt("COURSES_ID");
                    Integer STUDENT_ID = result.getInt("STUDENT_ID");
                    Integer STUDENT_GRADE = result.getInt("STUDENT_GRADE");
                    Integer GRADE_POINT = result.getInt("GRADE_POINT");
                    String RESULT_STATUS = result.getString("RESULT_STATUS");

                    gradesData data = new gradesData(COURSES_ID, STUDENT_ID, STUDENT_GRADE, GRADE_POINT, RESULT_STATUS);
                    searchData.add(data);
                }
            }
            gradesTable.setItems(searchData);
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while searching!");
        }
    }

    private void handleCOURSESSearch() throws SQLException {
        String checkSql = "SELECT COUNT(STUDENT_ID) as countstu FROM grades where COURSES_ID=?";
        int count;

        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, Integer.parseInt(searchid.getText().trim()));

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
            }

            if (count == 0) {
                showError("Grade for students in this COURSE doesn't exist.");
                return;
            }
        }

        ObservableList<gradesData> searchData = FXCollections.observableArrayList();
        String sql = "select DISTINCT COURSES_ID, STUDENT_ID, STUDENT_GRADE, GRADE_POINT, RESULT_STATUS from grades where COURSES_ID=?";

        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setInt(1, Integer.parseInt(searchid.getText().trim()));

            try (ResultSet result = prepare.executeQuery()) {
                System.out.println("Executing query: " + sql);

                while (result.next()) {
                    Integer COURSES_ID = result.getInt("COURSES_ID");
                    Integer STUDENT_ID = result.getInt("STUDENT_ID");
                    Integer STUDENT_GRADE = result.getInt("STUDENT_GRADE");
                    Integer GRADE_POINT = result.getInt("GRADE_POINT");
                    String RESULT_STATUS = result.getString("RESULT_STATUS");

                    gradesData data = new gradesData(COURSES_ID, STUDENT_ID, STUDENT_GRADE, GRADE_POINT, RESULT_STATUS);
                    searchData.add(data);
                }
            }
            gradesTable.setItems(searchData);
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while searching!");
        }
    }
private void handleStudentSearch() throws SQLException {
        String checkSql = "SELECT COUNT(STUDENT_ID) as countstu FROM grades where STUDENT_ID=?";
        int count;

        try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
            checkStatement.setInt(1, Integer.parseInt(searchid.getText().trim()));

            try (ResultSet resultSet = checkStatement.executeQuery()) {
                resultSet.next();
                count = resultSet.getInt(1);
            }

            if (count == 0) {
                showError("Grade for students doesn't exist.");
                return;
            }
        }

        ObservableList<gradesData> searchData = FXCollections.observableArrayList();
        String sql = "select DISTINCT COURSES_ID, STUDENT_ID, STUDENT_GRADE, GRADE_POINT, RESULT_STATUS from grades where STUDENT_ID=?";

        try (PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setInt(1, Integer.parseInt(searchid.getText().trim()));

            try (ResultSet result = prepare.executeQuery()) {
                System.out.println("Executing query: " + sql);

                while (result.next()) {
                    Integer COURSES_ID = result.getInt("COURSES_ID");
                    Integer STUDENT_ID = result.getInt("STUDENT_ID");
                    Integer STUDENT_GRADE = result.getInt("STUDENT_GRADE");
                    Integer GRADE_POINT = result.getInt("GRADE_POINT");
                    String RESULT_STATUS = result.getString("RESULT_STATUS");

                    gradesData data = new gradesData(COURSES_ID, STUDENT_ID, STUDENT_GRADE, GRADE_POINT, RESULT_STATUS);
                    searchData.add(data);
                }
            }
            gradesTable.setItems(searchData);
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while searching!");
        }
    }



    private void closeDatabaseResources() {
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


      
private void handleRemoveButton(ActionEvent event) {
    gradesData selecteddep = gradesTable.getSelectionModel().getSelectedItem();

    if (selecteddep != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Removegrades.fxml"));
            Parent root = loader.load();

            UniversityReporting.RemovegradesController RemovegradesController = loader.getController();
            RemovegradesController.setcourseId(selecteddep.getCOURSES_ID());
            RemovegradesController.setstudentId(selecteddep.getSTUDENT_ID());

            RemovegradesController.setReportingpageController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Remove Grades");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Removegrades.fxml"));
            Parent root = loader.load();
  
            UniversityReporting.RemovegradesController RemovegradesController = loader.getController();


            RemovegradesController.setReportingpageController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Remove Grades");
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
     gradesData selecteddep = gradesTable.getSelectionModel().getSelectedItem();

    if (selecteddep != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateGrade.fxml"));
            Parent root = loader.load();

            UniversityReporting.UpdateGradeController UpdateGradeController = loader.getController();
            UpdateGradeController.setcourseId(selecteddep.getCOURSES_ID());
                        UpdateGradeController.setstudentId(selecteddep.getSTUDENT_ID());

            UpdateGradeController.setReportingpageController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Grades");
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UpdateGrade.fxml"));
            Parent root = loader.load();

            UniversityReporting.UpdateGradeController UpdateGradeController = loader.getController();

            UpdateGradeController.setReportingpageController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Update Grades");
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

private void handleGPAtableButton(ActionEvent event) {
   
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("GPAtable.fxml"));
            Parent root = loader.load();

            UniversityReporting.GPAtableController GPAtableController = loader.getController();

            GPAtableController.setReportingpageController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("GPA");
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
  

private void openReportsDialog(ActionEvent event) {
   
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ReadReport.fxml"));
            Parent root = loader.load();

            UniversityReporting.ReadReportController ReadReportController = loader.getController();

            ReadReportController.setReportingpageController(this);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("GPA");
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
