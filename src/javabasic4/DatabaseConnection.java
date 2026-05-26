package javabasic4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Quan ly ket noi den Database
public class DatabaseConnection {
    private static Connection connection = null;
    
    // Phuong thuc static de lay Connection (Singleton pattern)
    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Doc config tu file
                String url = ConfigReader.getProperty("db.url");
                String user = ConfigReader.getProperty("db.username");
                String password = ConfigReader.getProperty("db.password");
                String driver = ConfigReader.getProperty("db.driver");
                
                // Load Driver
                Class.forName(driver);
                
                // Tao ket noi
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Ket noi Database thanh cong!");
                
            } catch (ClassNotFoundException e) {
                System.out.println("Loi: Driver Oracle khong tim thay!");
            } catch (SQLException e) {
                System.out.println("Loi ket noi Database: " + e.getMessage());
            }
        }
        return connection;
    }
    
    // Dong ket noi
    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Da dong ket noi Database");
            }
        } catch (SQLException e) {
            System.out.println("Loi khi dong ket noi: " + e.getMessage());
        }
    }
}
