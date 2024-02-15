/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityReporting;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Smart Lap
 */
public class GPAtableController implements Initializable {

    @FXML
    private VBox additional;
    @FXML
    private TableView<GPA_Data> GPAtable;
    @FXML
    private TableColumn<GPA_Data, Integer> Dep_cc;
    @FXML
    private TableColumn<GPA_Data, Integer> STUDENT_IDcc;
    @FXML
    private TableColumn<GPA_Data, Integer> required_credi;
    @FXML
    private TableColumn<GPA_Data, Integer> registered_ho;
    @FXML
    private TableColumn<GPA_Data, Integer> GPA_cc;
    @FXML
    private TableColumn<GPA_Data, String> RESULT;

    
      PreparedStatement prepare;
    ResultSet result;
    private Connection connect;
    private  UniversityReporting.ReportingpageController mainController;


    public void setReportingpageController(UniversityReporting.ReportingpageController mainController) {
        this.mainController = mainController;
    }
    
     public ObservableList<GPA_Data> getGPA_Data() throws SQLException, ClassNotFoundException {
        ObservableList<GPA_Data> GPA_Data = FXCollections.observableArrayList();

        String sql = "select DISTINCT DEPARTMENT_ID, STUDENT_ID, REQUIRED_CREDITS, REGISTERED_HOURS, STUDENT_GPA, FINAL_RESULT from GPA" ;
                
        connect = UniversityReporting.Database.connectDB();

        prepare = connect.prepareStatement(sql);
        result = prepare.executeQuery();

        System.out.println("Executing query: " + sql);

        while (result.next()) {
            Integer DEPARTMENT_ID = result.getInt("DEPARTMENT_ID");
            Integer STUDENT_ID = result.getInt("STUDENT_ID");
            Integer REQUIRED_CREDITS = result.getInt("REQUIRED_CREDITS");
            Integer REGISTERED_HOURS = result.getInt("REGISTERED_HOURS");
           Integer STUDENT_GPA = result.getInt("STUDENT_GPA");
            String FINAL_RESULT = result.getString("FINAL_RESULT");

            GPA_Data data = new GPA_Data(DEPARTMENT_ID, STUDENT_ID, REQUIRED_CREDITS, REGISTERED_HOURS, STUDENT_GPA,FINAL_RESULT);

            GPA_Data.add(data);
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

        return GPA_Data;

    }

    /**
     * Initializes the controller class.
     */
    @Override
   public void initialize(URL url, ResourceBundle rb) {
        try {
            Dep_cc.setCellValueFactory(new PropertyValueFactory<>("DEPARTMENT_ID"));
            STUDENT_IDcc.setCellValueFactory(new PropertyValueFactory<>("STUDENT_ID"));
            required_credi.setCellValueFactory(new PropertyValueFactory<>("REQUIRED_CREDITS"));
            registered_ho.setCellValueFactory(new PropertyValueFactory<>("REGISTERED_HOURS"));
            GPA_cc.setCellValueFactory(new PropertyValueFactory<>("STUDENT_GPA"));
            RESULT.setCellValueFactory(new PropertyValueFactory<>("FINAL_RESULT"));
            
        


    
            ObservableList<GPA_Data> data = getGPA_Data();
            GPAtable.setItems(data);
        } catch (SQLException ex) {
            Logger.getLogger(GPAtableController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GPAtableController.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
}
