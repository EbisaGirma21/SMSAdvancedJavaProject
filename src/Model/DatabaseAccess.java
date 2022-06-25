package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseAccess {
    private final static String DB_URL = "jdbc:mysql://localhost/SMS";
    private final static String user = "root";
    private final static String password = "root";
    Connection connection = null;

    public DatabaseAccess() {
        try {
            connection = DriverManager.getConnection(DB_URL, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection Connection() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection(DB_URL, user, password);
        return connection;
    }

}
