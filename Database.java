import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private String url = "jdbc:mysql://localhost:3306/animal_shelter_managementsystem";
    private String user = "root";
    private String password = "";

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
