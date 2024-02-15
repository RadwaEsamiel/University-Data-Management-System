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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class ReadReportController implements Initializable {
private Connection connect;

    private UniversityReporting.ReportingpageController mainController;
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
    
         PreparedStatement prepare;
    ResultSet result;
    
    
    public void setReportingpageController(UniversityReporting.ReportingpageController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       SearchButton.setOnAction(this::handleSearchButton);

    gradescom.setItems(
            FXCollections.observableArrayList(
                    "Department",
                    "Student",
                    "Course"
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
            openReportDepartmentDialog();
        }  
        else if ("Student".equals(selectedColumn)) {
            openReportStudentDialog();}
            else if ("Course".equals(selectedColumn)) {
            openReportCourseDialog();
        }
            else {
            showError("Invalid option selected!");
        }
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
        showError("An error occurred while connecting to the database!");
    } 
}

    

   private void openReportDepartmentDialog() {
    try {
        if (searchid.getText().isEmpty()) {
            showError("Please enter a valid ID!");
            return;
        }

        int departmentId = parseDepartmentId();
        if (departmentId == -1) {
            return; // Invalid ID format
        }

        if (!departmentExists(departmentId)) {
            showError("Department ID doesn't exist.");
            return;
        }

        executeGPAProcedures(departmentId);
        displayGPAResults(departmentId);

    } catch (SQLException e) {
        e.printStackTrace();
        showError("An error occurred.");
    }
}

private int parseDepartmentId() {
    try {
        return Integer.parseInt(searchid.getText().trim());
    } catch (NumberFormatException e) {
        showError("Invalid ID format. Please enter a valid number.");
        return -1;
    }
}

private boolean departmentExists(int departmentId) throws SQLException {
    String checkSql = "SELECT COUNT(UNI_Departments.DEPARTMENT_ID) FROM Departments_Location JOIN UNI_Departments ON Departments_Location.DEPARTMENT_ID = UNI_Departments.DEPARTMENT_ID WHERE UNI_Departments.DEPARTMENT_ID = ?";
    try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
        checkStatement.setInt(1, departmentId);
        try (ResultSet resultSet = checkStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }
}

private void executeGPAProcedures(int departmentId) throws SQLException {
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
            showError("An error occurred while executing students_totregistered_hours!");
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
}

private void displayGPAResults(int departmentId) throws SQLException {
    String sql = "SELECT D.STUDENTS_NUMBER, S.STUDENT_ID, D.DEPART_NAME, L.BUILDING_LOCATION, C.COURSE_NAME, G.COURSES_ID AS GRADE_COURSE_ID, G.STUDENT_GRADE, P.STUDENT_GPA, P.FINAL_RESULT, P.REGISTERED_HOURS FROM students S JOIN uni_DEPARTMENTS D ON D.DEPARTMENT_ID = S.DEPARTMENT_ID JOIN DEPARTMENTS_LOCATION L ON L.DEPARTMENT_ID = D.DEPARTMENT_ID JOIN Registration R ON R.STUDENT_ID = S.STUDENT_ID AND R.DEPARTMENT_ID = D.DEPARTMENT_ID JOIN courses C ON C.COURSES_ID = R.COURSES_ID JOIN grades G ON S.STUDENT_ID = G.STUDENT_ID JOIN gpa P ON S.STUDENT_ID = P.STUDENT_ID WHERE S.DEPARTMENT_ID = ?";

   try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
    preparedStatement.setInt(1, departmentId);

    try (ResultSet resultSet = preparedStatement.executeQuery()) {
        StringBuilder gpaResultBuilder = new StringBuilder();

        String previousStudentId = null;

        while (resultSet.next()) {
            String currentStudentId = resultSet.getString("STUDENT_ID");

             if (!currentStudentId.equals(previousStudentId)) {
  String Depname = resultSet.getString("DEPART_NAME");    
                String Deploc = resultSet.getString("BUILDING_LOCATION");
  String studentnum = resultSet.getString("STUDENTS_NUMBER");
                String studentId = resultSet.getString("STUDENT_ID");
                                    String studentGPA = resultSet.getString("STUDENT_GPA");
            String finalResult = resultSet.getString("FINAL_RESULT");
         

                gpaResultBuilder.append("Department ID you entered: ").append(departmentId)
                        .append("\n The name of this Department: ").append(Depname)
                        .append("\nThe Location of this Department: ").append(Deploc)
                        .append("\n Number of students in this Department: ").append(studentnum)
                        .append("\n one Student ID in this Department: ").append(studentId)
                         .append("\nStudent achieved GPA: ").append(studentGPA)
                    .append("\nAnd Student Final Result: ").append(finalResult)
                        ;
            }
            
String studentsId = resultSet.getString("STUDENT_ID");
            String courname = resultSet.getString("COURSE_NAME");
            String reg = resultSet.getString("REGISTERED_HOURS");
 

            gpaResultBuilder.
                    append("\n Breakdown of Students grades in this Department || StudentID : ").append(studentsId)
                    .append("\nStudent achieved grade in COURSE with the NAME : ").append(courname)
                    .append("\nStudent total REGISTERED HOURS : ").append(reg)
                                           

                    .append("\n\n");

             previousStudentId = currentStudentId;
        }

        gpa.setText(gpaResultBuilder.toString());
    } catch (SQLException e) {
        e.printStackTrace();
        showError("An error occurred while fetching GPA results!");
    }
} catch (SQLException e) {
    e.printStackTrace();
    showError("An error occurred while preparing the statement for GPA results!");
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

   private void openReportStudentDialog() {
    try {
        if (searchid.getText().isEmpty()) {
            showError("Please enter a valid ID!");
            return;
        }

        int studentId = parsestudentId();
        if (studentId == -1) {
            return;  
        }

        if (!studentExists(studentId)) {
            showError("Student ID doesn't exist.");
            return;
        }

        executeStudentProcedures(studentId);
        displaystudResults(studentId);

    } catch (SQLException e) {
        e.printStackTrace();
        showError("An error occurred.");
    }
}

private int parsestudentId() {
    try {
        return Integer.parseInt(searchid.getText().trim());
    } catch (NumberFormatException e) {
        showError("Invalid ID format. Please enter a valid number.");
        return -1;
    }
}

private boolean studentExists(int studentId) throws SQLException {
    String checkSql ="SELECT COUNT(STUDENTS.student_ID) FROM students WHERE STUDENTS.student_ID = ?";
            
    try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
        checkStatement.setInt(1, studentId);
        try (ResultSet resultSet = checkStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }
}

private void executeStudentProcedures(int studentId) throws SQLException {
   try (CallableStatement callableStatement1 = connect.prepareCall("{call students_totregistered_hours(?, ?)}")) {
            try (PreparedStatement studentIdsStatement = connect.prepareStatement("SELECT DEPARTMENT_ID FROM Students WHERE STUDENT_ID = ?")) {
                studentIdsStatement.setInt(1, studentId);
                try (ResultSet studentIdsResultSet = studentIdsStatement.executeQuery()) {
                    while (studentIdsResultSet.next()) {
                        int departmentId = studentIdsResultSet.getInt("DEPARTMENT_ID");
                                callableStatement1.setInt(1, studentId);
                        callableStatement1.setInt(2, departmentId);
                
                        callableStatement1.execute();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while executing students_totregistered_hours!");
            return;
        }

        try (CallableStatement callableStatement2 = connect.prepareCall("{call GPA_calculator(?)}")) {
          
                        callableStatement2.setInt(1, studentId);
                        callableStatement2.execute();
                    }
          
       

        try (CallableStatement callableStatement3 = connect.prepareCall("{call students_Final_result(?)}")) {
           
                        callableStatement3.setInt(1, studentId);
                        callableStatement3.execute();
        }
}

private void displaystudResults(int studentId) throws SQLException {
    String sql = "SELECT s.STUDENT_ID, s.First_name, s.Last_name, s.Gender, s.Department_ID, s.Date_of_birth, s.Address, e.EMAIL, d.DEPART_NAME, l.BUILDING_LOCATION, c.COURSE_NAME, g.RESULT_STATUS, g.COURSES_ID, g.STUDENT_GRADE, p.STUDENT_GPA, p.FINAL_RESULT, p.REGISTERED_HOURS FROM students s JOIN students_Email e ON e.STUDENT_ID = s.STUDENT_ID JOIN uni_DEPARTMENTS d ON d.DEPARTMENT_ID = s.DEPARTMENT_ID JOIN DEPARTMENTS_LOCATION l ON l.DEPARTMENT_ID = d.DEPARTMENT_ID JOIN Registration r ON r.STUDENT_ID = s.STUDENT_ID AND r.DEPARTMENT_ID = d.DEPARTMENT_ID JOIN courses c ON c.COURSES_ID = r.COURSES_ID JOIN grades g ON s.STUDENT_ID = g.STUDENT_ID JOIN gpa p ON s.STUDENT_ID = p.STUDENT_ID WHERE s.STUDENT_ID = ?";

    try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
        preparedStatement.setInt(1, studentId);



try (ResultSet resultSet = preparedStatement.executeQuery()) {
    StringBuilder gpaResultBuilder = new StringBuilder();

    ResultSetMetaData metaData = resultSet.getMetaData();
    int columnCount = metaData.getColumnCount();

    for (int i = 1; i <= columnCount; i++) {
        System.out.println("Column Name: " + metaData.getColumnName(i));
    }

    String previousStudentId = null;

    while (resultSet.next()) {
        String currentStudentId = resultSet.getString("STUDENT_ID");

         if (!currentStudentId.equals(previousStudentId)) {
             String stunam = resultSet.getString("First_name");
            String stunaml = resultSet.getString("Last_name");
            String gender = resultSet.getString("Gender");
            String dofb = resultSet.getString("Date_of_birth");
            String add = resultSet.getString("Address");
            String email = resultSet.getString("EMAIL");
            String depid = resultSet.getString("Department_ID");
            String Depname = resultSet.getString("DEPART_NAME");
            String Deploc = resultSet.getString("BUILDING_LOCATION");
                    String studentGPA = resultSet.getString("STUDENT_GPA");
        String finalResult = resultSet.getString("FINAL_RESULT");
        String reg = resultSet.getString("REGISTERED_HOURS");
        
            gpaResultBuilder.append("\n  Student ID you entered: ").append(currentStudentId)
                    .append("\nStudent First name: ").append(stunam)
                    .append("\nStudent last name: ").append(stunaml)
                    .append("\nStudent Gender: ").append(gender)
                    .append("\nStudent Date of birth: ").append(dofb)
                    .append("\nStudent Address : ").append(add)
                    .append("\nStudent EMAIL: ").append(email)
                    .append("\nStudent is at Department: ").append(depid)
                    .append("\n The name of this Department: ").append(Depname)
                    .append("\nThe Location of this Department: ").append(Deploc)
                         .append("\nStudent total REGISTERED HOURS : ").append(reg)
                .append("\nStudent achieved GPA: ").append(studentGPA)
                .append("\nAnd Student Final Result: ").append(finalResult);
        }

         String courname = resultSet.getString("COURSE_NAME");
        String courid = resultSet.getString("COURSES_ID");
        String grade = resultSet.getString("STUDENT_GRADE");
           String cres = resultSet.getString("RESULT_STATUS");


        gpaResultBuilder.append("\nStudent is enrolled In COURSE with ID : ").append(courid)
                .append("\nCOURSE NAME : ").append(courname)
                .append("\nStudent achieved grade: ").append(grade)
           .append("\nStudent result in this course: ").append(cres)
                .append("\n\n");

         previousStudentId = currentStudentId;
    }

    gpa.setText(gpaResultBuilder.toString());
} catch (SQLException e) {
    e.printStackTrace();
    showError("An error occurred while fetching GPA results!");
}



    } catch (SQLException e) {
        e.printStackTrace();
        showError("An error occurred while preparing the statement for GPA results!");
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


   private void openReportCourseDialog() {
    try {
        if (searchid.getText().isEmpty()) {
            showError("Please enter a valid ID!");
            return;
        }

        int courseId = parsecoursetId();
        if (courseId == -1) {
            return;  
        }

        if (!courseExists(courseId)) {
            showError("Course ID doesn't exist.");
            return;
        }

        executecourserocedures(courseId);
        displaycourseResults(courseId);

    } catch (SQLException e) {
        e.printStackTrace();
        showError("An error occurred.");
    }
}

private int parsecoursetId() {
    try {
        return Integer.parseInt(searchid.getText().trim());
    } catch (NumberFormatException e) {
        showError("Invalid ID format. Please enter a valid number.");
        return -1;
    }
}

private boolean courseExists(int courseId) throws SQLException {
    String checkSql ="SELECT COUNT(courses_ID) FROM courses WHERE courses_ID=?";
    try (PreparedStatement checkStatement = connect.prepareStatement(checkSql)) {
        checkStatement.setInt(1, courseId);
        try (ResultSet resultSet = checkStatement.executeQuery()) {
            resultSet.next();
            return resultSet.getInt(1) > 0;
        }
    }
}

private void executecourserocedures(int courseId) throws SQLException {
    
                    try (CallableStatement callableStatement5 = connect.prepareCall("{call UPDATE_GRADES_points(?,?)}")) {
    try (PreparedStatement studentIdsStatement = connect.prepareStatement("SELECT STUDENT_ID FROM Registration WHERE courses_ID = ?")) {
                studentIdsStatement.setInt(1, courseId);
                try (ResultSet studentIdsResultSet = studentIdsStatement.executeQuery()) {
                    while (studentIdsResultSet.next()) {
                        int studentId = studentIdsResultSet.getInt("STUDENT_ID");
                               callableStatement5.setInt(1, studentId);

                        callableStatement5.setInt(2, courseId);
                        callableStatement5.execute();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while executing students_totregistered_hours!");
            return;
        }
             
                    
     try (CallableStatement callableStatement3 = connect.prepareCall("{call SET_RESULT_STATUS()}")) {
    callableStatement3.execute();
}
     
   try (CallableStatement callableStatement1 = connect.prepareCall("{call students_totregistered_hours(?, ?)}")) {
            try (PreparedStatement studentIdsStatement = connect.prepareStatement("SELECT STUDENT_ID FROM Registration WHERE courses_ID = ?")) {
                studentIdsStatement.setInt(1, courseId);
                try (ResultSet studentIdsResultSet = studentIdsStatement.executeQuery()) {
                    while (studentIdsResultSet.next()) {
                        int studentId = studentIdsResultSet.getInt("STUDENT_ID");
                        callableStatement1.setInt(1, courseId);
                        callableStatement1.setInt(2, studentId);
                        callableStatement1.execute();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while executing students_totregistered_hours!");
            return;
        }

        try (CallableStatement callableStatement2 = connect.prepareCall("{call GPA_calculator(?)}")) {
            try (PreparedStatement studentIdsStatement = connect.prepareStatement("SELECT STUDENT_ID FROM Registration WHERE courses_ID = ?")) {
                studentIdsStatement.setInt(1, courseId);
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
            try (PreparedStatement studentIdsStatement = connect.prepareStatement("SELECT STUDENT_ID FROM Registration WHERE courses_ID = ?")) {
                studentIdsStatement.setInt(1, courseId);
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
}
private void displaycourseResults(int courseId) throws SQLException {
    String countQuery = "SELECT COUNT(R.STUDENT_ID) AS STUDENTS_NUMBER FROM Registration R WHERE R.COURSES_ID=?";
    String mainQuery = "SELECT C.COURSES_ID, R.STUDENT_ID, D.Department_ID, D.DEPART_NAME, C.COURSE_NAME, G.GRADE_POINT, G.RESULT_STATUS, G.STUDENT_GRADE, P.STUDENT_GPA, P.FINAL_RESULT " +
            "FROM students S " +
            "JOIN uni_DEPARTMENTS D ON D.DEPARTMENT_ID = S.DEPARTMENT_ID " +
            "JOIN DEPARTMENTS_LOCATION L ON L.DEPARTMENT_ID = D.DEPARTMENT_ID " +
            "JOIN Registration R ON R.STUDENT_ID = S.STUDENT_ID AND R.DEPARTMENT_ID = D.DEPARTMENT_ID " +
            "JOIN courses C ON C.COURSES_ID = R.COURSES_ID " +
            "JOIN grades G ON S.STUDENT_ID = G.STUDENT_ID " +
            "JOIN gpa P ON S.STUDENT_ID = P.STUDENT_ID " +
            "WHERE C.COURSES_ID=? " +
            "GROUP BY D.DEPART_NAME, C.COURSE_NAME, G.GRADE_POINT, G.RESULT_STATUS, G.COURSES_ID, G.STUDENT_GRADE, P.STUDENT_GPA, C.COURSES_ID, P.FINAL_RESULT, R.STUDENT_ID, D.Department_ID";

    try (PreparedStatement countStatement = connect.prepareStatement(countQuery)) {
        countStatement.setInt(1, courseId);
        try (ResultSet countResult = countStatement.executeQuery()) {
            countResult.next(); // Move to the first row
            int studentsNumber = countResult.getInt("STUDENTS_NUMBER");

            try (PreparedStatement preparedStatement = connect.prepareStatement(mainQuery)) {
                preparedStatement.setInt(1, courseId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    StringBuilder gpaResultBuilder = new StringBuilder();

                    while (resultSet.next()) {
                        String studid = resultSet.getString("STUDENT_ID");
                        String depid = resultSet.getString("Department_ID");
                        String Depname = resultSet.getString("DEPART_NAME");
                        String grade = resultSet.getString("STUDENT_GRADE");
                        String Deploc = resultSet.getString("GRADE_POINT");
                        String reg = resultSet.getString("RESULT_STATUS");
                        String studentGPA = resultSet.getString("STUDENT_GPA");
                        String finalResult = resultSet.getString("FINAL_RESULT");

                        gpaResultBuilder.append("\nCourse ID you entered: ").append(courseId)
                                .append("\nNumber of Students enrolled In this COURSE: ").append(studentsNumber)
                                .append("\nOne STUDENT ID enrolled In this COURSE: ").append(studid)
                                .append("\nStudents is at Department: ").append(depid)
                                .append("\nThe name of this Department: ").append(Depname)
                                .append("\nStudent achieved grade In COURSE  : ").append(grade)
                                .append("\nAnd the RESULT is: : ").append(reg)
                                .append("\nStudent achieved GPA: ").append(studentGPA)
                                .append("\nAnd Student Final Result: ").append(finalResult)
                                .append("\n\n");
                    }

                    gpa.setText(gpaResultBuilder.toString());
                } catch (SQLException e) {
                    e.printStackTrace();
                    showError("An error occurred while fetching course results!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showError("An error occurred while fetching students count!");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showError("An error occurred while preparing the statement for course results!");
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