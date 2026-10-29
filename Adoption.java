import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Adoption extends AnimalAction {
    @Override
    public void run() {
        Adopt_Animal();
    }

    public void Adopt_Animal() {
        System.out.println("\n=== Animal Adoption ===");
        System.out.println("Choose which animals to view:");
        System.out.println("[1] Cats");
        System.out.println("[2] Dogs");
        System.out.println("[3] Other Animals");
        System.out.println("[4] All Animals");

        int category = getValidInt("Choose an option: ");
        if (category < 1 || category > 4) {
            System.out.println("Invalid option.");
            return;
        }

        String categorySQL = "";
        if (category == 1) {
            categorySQL = " AND LOWER(species) = 'cat'";
        } else if (category == 2) {
            categorySQL = " AND LOWER(species) = 'dog'";
        } else if (category == 3) {
            categorySQL = " AND LOWER(species) NOT IN ('cat', 'dog')";
        }

        String listSQL = "SELECT * FROM animal_info "
                + "WHERE LOWER(health_status) = 'healthy' "
                + "AND LOWER(adoption_status) = 'available'"
                + categorySQL + " ORDER BY id";

        boolean found = false;

        System.out.println("\n=== Healthy and Available Animals ===");
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(listSQL);
             ResultSet rs = stmt.executeQuery()) {

            printHeader();
            while (rs.next()) {
                found = true;
                printAnimal(rs);
            }

        } catch (SQLException e) {
            System.out.println("Error viewing animals: " + e.getMessage());
            return;
        }

        if (!found) {
            System.out.println("No healthy and available animals in this category.");
            return;
        }

        int animalID = getValidInt("\nEnter the ID of the animal to adopt (0 to cancel): ");
        if (animalID == 0) {
            System.out.println("Adoption cancelled.");
            return;
        }

        String searchSQL = "SELECT animal_name, species, health_status, adoption_status "
                + "FROM animal_info WHERE id = ?";
        String name;
        String species;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(searchSQL)) {
            stmt.setInt(1, animalID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Animal ID not found.");
                    return;
                }

                name = rs.getString("animal_name");
                species = rs.getString("species");
                String health = rs.getString("health_status");
                String status = rs.getString("adoption_status");

                if (health == null || !health.equalsIgnoreCase("Healthy")) {
                    System.out.println("This animal cannot be adopted because its health status is not Healthy.");
                    return;
                }

                if (status == null || !status.equalsIgnoreCase("Available")) {
                    System.out.println("This animal is not available for adoption.");
                    return;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking animal: " + e.getMessage());
            return;
        }

        System.out.println("Selected animal: " + name + " (" + species + ")");
        String fullName = getRequiredText("Adopter's full name: ");
        int adopterAge = getValidInt("Adopter's age: ");
        while (adopterAge == 0) {
            System.out.println("Adopter age must be greater than zero.");
            adopterAge = getValidInt("Adopter's age: ");
        }
        String occupation = getRequiredText("Occupation: ");
        String contact = getRequiredText("Contact number: ");

        System.out.println("\nAdoption summary:");
        System.out.println("Animal: " + name + " (" + species + "), ID: " + animalID);
        System.out.println("Adopter: " + fullName + ", age " + adopterAge);
        if (!confirmAction("Confirm and save this adoption?")) {
            System.out.println("Adoption cancelled. No changes were saved.");
            return;
        }

        String insertSQL = "INSERT INTO adopter_info "
                + "(full_name, age, occupation, contact_number) VALUES (?, ?, ?, ?)";
        String updateSQL = "UPDATE animal_info "
                + "SET adoption_status = 'Adopted', adopter_id = ?, adopted_date = CURRENT_DATE "
                + "WHERE id = ? AND LOWER(health_status) = 'healthy' "
                + "AND LOWER(adoption_status) = 'available'";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement insertStmt = conn.prepareStatement(
                    insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {
                insertStmt.setString(1, fullName);
                insertStmt.setInt(2, adopterAge);
                insertStmt.setString(3, occupation);
                insertStmt.setString(4, contact);
                insertStmt.executeUpdate();

                try (ResultSet keys = insertStmt.getGeneratedKeys()) {
                    if (!keys.next()) {
                        conn.rollback();
                        System.out.println("Could not get the adopter ID.");
                        return;
                    }

                    int adopterID = keys.getInt(1);
                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                        updateStmt.setInt(1, adopterID);
                        updateStmt.setInt(2, animalID);

                        int rowsChanged = updateStmt.executeUpdate();
                        if (rowsChanged == 1) {
                            conn.commit();
                            System.out.println("Adoption recorded successfully!");
                            System.out.println("Date adopted: " + java.time.LocalDate.now());
                        } else {
                            conn.rollback();
                            System.out.println("Adoption was not completed. The animal may no longer be healthy or available.");
                        }
                    }
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println("Error processing adoption: " + e.getMessage());
        }
    }

    private void printHeader() {
        System.out.printf("%-4s %-12s %-9s %-12s %-4s %-14s %-10s %-8s %-8s %-12s %-16s %-13s %-12s%n",
                "ID", "Name", "Species", "Breed", "Age", "Health", "Status", "Weight", "Height",
                "Eye Color", "Color Pattern", "Accepted", "Adopted");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private void printAnimal(ResultSet rs) throws SQLException {
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
}