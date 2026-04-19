package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    public static Connection getConnection(){
        if (connection == null) {
            createConnection();
        }
        return connection;
    }

    private static void createConnection(){
        String user = "root";
        String pass = "";
        String database = "gimnasio_ragnarok";

        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+database,user,pass);
        } catch (SQLException e) {
            System.out.println("Error en la conexión con la base de datos");
            System.out.println(e.getMessage());
        }
    }
}
