import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Update extends AnimalAction {
    @Override
    public void run() {
        Update_Animal_Profile();
    }

    public void Update_Animal_Profile() {
        System.out.println("\n=== Update Animal Profile ===");
        int animalID = getValidInt("Enter animal ID: ");
        String searchSQL = "SELECT animal_name, health_status, weight_kg, adoption_status "
                + "FROM animal_info WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(searchSQL)) {
            stmt.setInt(1, animalID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Animal: " + rs.getString("animal_name"));
                    System.out.println("Health: " + rs.getString("health_status"));
                    System.out.println("Weight: " + rs.getDouble("weight_kg"));
                    System.out.println("Adoption status: " + rs.getString("adoption_status"));
                } else {
                    System.out.println("Animal ID not found.");
                    return;
                }
            }

            System.out.println("\nWhat would you like to update?");
            System.out.println("[1] Health Status");
            System.out.println("[2] Weight");
            System.out.println("Note: Adoption status is changed by the adoption process only.");
            int choice = getValidInt("Choose an option: ");

            String updateSQL;
            if (choice == 1) {
                String health = getRequiredText("Enter new health status: ");
                if (!confirmAction("Change this animal's health status to " + health + "?")) {
                    System.out.println("Update cancelled.");
                    return;
                }
                updateSQL = "UPDATE animal_info SET health_status = ? WHERE id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                    updateStmt.setString(1, health);
                    updateStmt.setInt(2, animalID);
                    updateStmt.executeUpdate();
                }
            } else if (choice == 2) {
                double weight = getValidDouble("Enter new weight: ");
                if (!confirmAction("Change this animal's weight to " + weight + " kg?")) {
                    System.out.println("Update cancelled.");
                    return;
                }
                updateSQL = "UPDATE animal_info SET weight_kg = ? WHERE id = ?";
                try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                    updateStmt.setDouble(1, weight);
                    updateStmt.setInt(2, animalID);
                    updateStmt.executeUpdate();
                }
            } else {
                System.out.println("Invalid option.");
                return;
            }

            System.out.println("Animal profile updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating profile: " + e.getMessage());
        }
    }
}