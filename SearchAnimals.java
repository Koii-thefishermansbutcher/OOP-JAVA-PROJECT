import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchAnimals extends AnimalAction {
    @Override
    public void run() {
        System.out.println("\n=== Search Animals ===");
        System.out.println("[1] Search by Animal ID");
        System.out.println("[2] Search by Name");
        int choice = getValidInt("Choose an option: ");

        if (choice == 1) {
            int id = getValidInt("Enter animal ID: ");
            String sql = "SELECT * FROM animal_info WHERE id = ?";
            search(sql, id, null);
        } else if (choice == 2) {
            String name = getRequiredText("Enter all or part of the animal name: ");
            String sql = "SELECT * FROM animal_info WHERE animal_name LIKE ? ORDER BY id";
            search(sql, 0, name);
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void search(String sql, int id, String name) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (name == null) {
                stmt.setInt(1, id);
            } else {
                stmt.setString(1, "%" + name + "%");
            }

            try (ResultSet rs = stmt.executeQuery()) {
                boolean found = false;
                printHeader();
                while (rs.next()) {
                    found = true;
                    System.out.printf("%-4d %-12s %-9s %-12s %-4d %-14s %-10s %-8.1f %-8.1f %-12s %-16s %-13s %-12s%n",
                            rs.getInt("id"), rs.getString("animal_name"), rs.getString("species"),
                            rs.getString("breed"), rs.getInt("age"), rs.getString("health_status"),
                            rs.getString("adoption_status"), rs.getDouble("weight_kg"),
                            rs.getDouble("height_cm"), rs.getString("eye_color"),
                            rs.getString("color_pattern"), rs.getDate("accepted_date"),
                            rs.getDate("adopted_date"));
                }
                if (!found) {
                    System.out.println("No matching animals found.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error searching animals: " + e.getMessage());
        }
    }

    private void printHeader() {
        System.out.printf("%-4s %-12s %-9s %-12s %-4s %-14s %-10s %-8s %-8s %-12s %-16s %-13s %-12s%n",
                "ID", "Name", "Species", "Breed", "Age", "Health", "Status", "Weight", "Height",
                "Eye Color", "Color Pattern", "Accepted", "Adopted");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }
}