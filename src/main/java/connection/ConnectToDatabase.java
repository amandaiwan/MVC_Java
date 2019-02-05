package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectToDatabase {

    private static String hostAddress =  "jdbc:mysql://utb-mysql.du.se:3306/db30";
    private static String username = "db30";
    private static String password = "FJJAcyMU";

    public ConnectToDatabase() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    public static Connection createConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(hostAddress, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

}
