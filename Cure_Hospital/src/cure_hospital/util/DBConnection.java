package cure_hospital.util;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private String url = "jdbc:mysql://localhost:3306/cure_hospital";
    private String user = "root";
    private String password = "1234";
    private String driver = "com.mysql.cj.jdbc.Driver";
    private Connection con = null;

    public Connection getCon() throws SQLException {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }

        con = DriverManager.getConnection(url, user, password);

        return con;

    }
}
