import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login extends Database {

    public boolean login() {
        while (true) {
            System.out.println("\n=============================================");
            System.out.println("       ANIMAL SHELTER STAFF LOGIN");
            System.out.println("=============================================");
            System.out.print("Username (enter 0 to exit): ");
            String username = AnimalAction.scan.nextLine().trim();

            if (username.equals("0")) {
                return false;
            }

            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
                continue;
            }

            System.out.print("Password: ");
            String password = AnimalAction.scan.nextLine();

            if (password.isEmpty()) {
                System.out.println("Password cannot be empty.");
                continue;
            }

            String sql = "SELECT id FROM staff_info "
                       + "WHERE username = ? AND password_hash = SHA2(?, 256)";

            try (Connection conn = getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, username);
                stmt.setString(2, password);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        System.out.println("\nLogin successful. Welcome, " + username + "!");
                        return true;
                    }
                }

                System.out.println("Incorrect username or password. Please try again.");

            } catch (SQLException e) {
                System.out.println("Unable to log in. Please check your database connection.");
                System.out.println("Make sure you have run staff_login_setup.sql.");
                return false;
            }
        }
    }
}
