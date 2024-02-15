/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universityStudents;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.CubicCurve;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import universityStudents.Database;

/**
 *
 * @author Smart Lap
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private TextField studentid;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    
     PreparedStatement prepare;
    ResultSet result;
    
      private Connection connect;
    @FXML
    private AnchorPane login_form;
      
      private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
  
    
   @Override
public void initialize(URL url, ResourceBundle rb) {
        login.setOnAction(this::handlelogin);


}

         private void handlelogin(ActionEvent event) {
            try {
                if (password.getText().isEmpty() || studentid.getText().isEmpty()) {
                    showError("Please fill all fields to login!");
                } else {
                    try {
                        connect = Database.connectDB();
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    try {
                        Integer.parseInt(studentid.getText());
                    } catch (NumberFormatException e) {
                        showError("Invalid Student ID. Please enter a numeric value.");
                        return;
                    }
                    boolean flag = false;

                    String sql = "SELECT * FROM admins where admin_ID = ? and admin_password = ? ";
                    prepare = connect.prepareStatement(sql);
prepare.setInt(1, Integer.parseInt(studentid.getText().trim()));
prepare.setString(2, password.getText().trim());


System.out.println("Executing query: " + sql);
System.out.println("Student ID: " + studentid.getText());
System.out.println("Password: " + password.getText());




                    result = prepare.executeQuery();

                    if (result.next()) {
                                        flag = true ;}
                    else 
                    {flag = false;}
                    
                    if (flag){

                        System.out.println("Login Successful.");
                        
                                  openhomepage();   



                    } else {
                        showError("Login Failed, Please enter valid credentials!");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
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
        }

        private void openhomepage() throws IOException {
FXMLLoader loader = new FXMLLoader(getClass().getResource("/HomePage/Home.fxml"));
    Parent root = loader.load();
HomePage.HomeController HomeController = loader.getController();
        HomeController.initialize(null, null);
    Stage stage = new Stage();
    stage.setTitle("University Management System | Main Page ");
    stage.setScene(new Scene(root));
    stage.show();

    login.getScene().getWindow().hide();

        }
 

}
