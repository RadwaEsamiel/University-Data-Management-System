/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UniversityDepartments;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection connectDB() throws ClassNotFoundException, SQLException {
         Class.forName("oracle.jdbc.OracleDriver");
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String username = "UNIVERSITYDATABASE";
        String password = "123";
        return DriverManager.getConnection(url, username, password);
    }
}

