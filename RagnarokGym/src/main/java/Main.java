import util.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    static void main() {

        Connection connection = DBConnection.getConnection();

        System.out.println(connection);
        try {
            System.out.println(connection.getCatalog());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
