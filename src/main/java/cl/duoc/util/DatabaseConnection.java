package cl.duoc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection instancia;

    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "25777";

    private DatabaseConnection() {}

    public static Connection getInstance() throws SQLException {
        if (instancia == null || instancia.isClosed()) {
            instancia = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return instancia;
    }
}
