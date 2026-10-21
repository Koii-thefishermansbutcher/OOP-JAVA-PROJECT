import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    String url = "jdbc:mysql://localhost:3306/animal_shelter_managementsystem";
    String user = "root";
    String password = "";

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
