import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdoptionHistory extends AnimalAction {
    @Override
    public void run() {
        System.out.println("\n=== Adoption History ===");
        String sql = "SELECT a.id, a.animal_name, a.species, a.breed, a.adopted_date, "
                + "d.full_name, d.contact_number "
                + "FROM animal_info a LEFT JOIN adopter_info d ON a.adopter_id = d.id "
                + "WHERE LOWER(a.adoption_status) = 'adopted' OR a.adopted_date IS NOT NULL "
                + "ORDER BY a.adopted_date DESC, a.id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.printf("%-4s %-16s %-12s %-16s %-13s %-22s %-18s%n",
                    "ID", "Animal", "Species", "Breed", "Adopted Date", "Adopter", "Contact");
            System.out.println("------------------------------------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-4d %-16s %-12s %-16s %-13s %-22s %-18s%n",
                        rs.getInt("id"), rs.getString("animal_name"), rs.getString("species"),
                        rs.getString("breed"), rs.getDate("adopted_date"),
                        rs.getString("full_name"), rs.getString("contact_number"));
            }
            if (!found) {
                System.out.println("No adoption records found yet.");
            }
        } catch (SQLException e) {
            System.out.println("Error viewing adoption history: " + e.getMessage());
        }
    }
}