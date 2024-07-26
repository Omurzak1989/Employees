package employees.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection= DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres",
                    "postgres",
                    "3223"
            );
            System.out.println("Successfully connected to database");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
}
