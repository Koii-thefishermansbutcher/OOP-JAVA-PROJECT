import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShelterStatistics extends AnimalAction {
    @Override
    public void run() {
        System.out.println("\n=== Shelter Statistics ===");
        String sql = "SELECT COUNT(*) AS total, "
                + "SUM(CASE WHEN LOWER(adoption_status) = 'available' THEN 1 ELSE 0 END) AS available, "
                + "SUM(CASE WHEN LOWER(adoption_status) = 'adopted' THEN 1 ELSE 0 END) AS adopted, "
                + "SUM(CASE WHEN LOWER(health_status) = 'healthy' THEN 1 ELSE 0 END) AS healthy, "
                + "SUM(CASE WHEN LOWER(health_status) <> 'healthy' OR health_status IS NULL THEN 1 ELSE 0 END) AS needs_care "
                + "FROM animal_info";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                System.out.println("Total animals registered: " + rs.getInt("total"));
                System.out.println("Available animals: " + rs.getInt("available"));
                System.out.println("Adopted animals: " + rs.getInt("adopted"));
                System.out.println("Healthy animals: " + rs.getInt("healthy"));
                System.out.println("Animals needing health attention: " + rs.getInt("needs_care"));
            }
        } catch (SQLException e) {
            System.out.println("Error loading shelter statistics: " + e.getMessage());
        }
    }
}
