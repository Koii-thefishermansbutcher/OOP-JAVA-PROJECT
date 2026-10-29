import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class View extends AnimalAction {
    @Override
    public void run() {
        View_Shelter_Directory();
    }

    public void View_Shelter_Directory() {
        System.out.println("\n=== Shelter Directory ===");
        System.out.println("[1] View Dogs");
        System.out.println("[2] View Cats");
        System.out.println("[3] View Other Animals");
        System.out.println("[4] View All Animals");

        int choice = getValidInt("Choose an option: ");
        String sql = "SELECT * FROM animal_info";

        if (choice == 1) {
            sql += " WHERE LOWER(species) = 'dog'";
        } else if (choice == 2) {
            sql += " WHERE LOWER(species) = 'cat'";
        } else if (choice == 3) {
            sql += " WHERE LOWER(species) NOT IN ('dog', 'cat')";
        } else if (choice != 4) {
            System.out.println("Invalid option.");
            return;
        }
        sql += " ORDER BY id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.printf("%-4s %-12s %-9s %-12s %-4s %-14s %-10s %-8s %-8s %-12s %-16s %-13s %-12s%n",
                    "ID", "Name", "Species", "Breed", "Age", "Health", "Status", "Weight", "Height",
                    "Eye Color", "Color Pattern", "Accepted", "Adopted");
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-4d %-12s %-9s %-12s %-4d %-14s %-10s %-8.1f %-8.1f %-12s %-16s %-13s %-12s%n",
                        rs.getInt("id"),
                        rs.getString("animal_name"),
                        rs.getString("species"),
                        rs.getString("breed"),
                        rs.getInt("age"),
                        rs.getString("health_status"),
                        rs.getString("adoption_status"),
                        rs.getDouble("weight_kg"),
                        rs.getDouble("height_cm"),
                        rs.getString("eye_color"),
                        rs.getString("color_pattern"),
                        rs.getDate("accepted_date"),
                        rs.getDate("adopted_date"));
            }

            if (!found) {
                System.out.println("No animals found.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing directory: " + e.getMessage());
        }
    }
}