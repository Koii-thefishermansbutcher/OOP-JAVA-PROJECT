import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Animal extends Database {
    Scanner scan = new Scanner(System.in);

    public int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            if (scan.hasNextInt()) {
                int number = scan.nextInt();
                scan.nextLine();
                return number;
            } else {
                System.out.println("Please enter a valid whole number.");
                scan.nextLine();
            }
        }
    }

    public double getValidDouble(String prompt) {
        while (true) {
            System.out.print(prompt);

            if (scan.hasNextDouble()) {
                double number = scan.nextDouble();
                scan.nextLine();
                return number;
            } else {
                System.out.println("Please enter a valid number.");
                scan.nextLine();
            }
        }
    }

    public void Add_Animal() {
        System.out.println("\n=== Register a New Animal ===");

        System.out.print("Animal name: ");
        String name = scan.nextLine();

        System.out.print("Species: ");
        String species = scan.nextLine();

        int age = getValidInt("Age: ");

        String health = "Not Specified";
        String status = "Available";
        String breed = "Unknown";
        double weight = 0.0;
        double height = 0.0;
        String eyeColor = "Not Specified";
        String pattern = "Not Specified";

        System.out.print("Enter additional details? (Y/N): ");
        String choice = scan.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            System.out.print("Health status: ");
            health = scan.nextLine();

            System.out.print("Adoption status: ");
            status = scan.nextLine();

            System.out.print("Breed: ");
            breed = scan.nextLine();

            weight = getValidDouble("Weight in kg: ");
            height = getValidDouble("Height in cm: ");

            System.out.print("Eye color: ");
            eyeColor = scan.nextLine();

            System.out.print("Color pattern: ");
            pattern = scan.nextLine();
        }

        String sql = "INSERT INTO animal_info "
                + "(animal_name, species, age, health_status, "
                + "adoption_status, breed, weight_kg, height_cm, "
                + "eye_color, color_pattern) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, species);
            stmt.setInt(3, age);
            stmt.setString(4, health);
            stmt.setString(5, status);
            stmt.setString(6, breed);
            stmt.setDouble(7, weight);
            stmt.setDouble(8, height);
            stmt.setString(9, eyeColor);
            stmt.setString(10, pattern);
            stmt.executeUpdate();

            System.out.println("Animal registered successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding animal: " + e.getMessage());
        }

        System.out.println("\n[1] View Shelter Directory");
        System.out.println("[2] Add Another Animal");
        System.out.println("[3] Return to Main Menu");

        int next = getValidInt("Choose an option: ");

        switch (next) {
            case 1:
                View_Shelter_Directory();
                break;
            case 2:
                Add_Animal();
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public void Adopt_Animal() {
        System.out.println("\n=== Animal Adoption ===");

        int animalID = getValidInt("Enter animal ID: ");
        String name = "";
        String species = "";
        String status = "";

        String searchSQL = "SELECT animal_name, species, adoption_status "
                + "FROM animal_info WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(searchSQL)) {

            stmt.setInt(1, animalID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    name = rs.getString("animal_name");
                    species = rs.getString("species");
                    status = rs.getString("adoption_status");
                } else {
                    System.out.println("Animal ID not found.");
                    return;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error searching for animal: "
                    + e.getMessage());
            return;
        }

        System.out.println("Animal: " + name);
        System.out.println("Species: " + species);
        System.out.println("Status: " + status);

        if (status.equalsIgnoreCase("Adopted")) {
            System.out.println("This animal has already been adopted.");
            return;
        }

        System.out.print("Adopter's full name: ");
        String fullName = scan.nextLine();

        int adopterAge = getValidInt("Adopter's age: ");

        System.out.print("Occupation: ");
        String occupation = scan.nextLine();

        System.out.print("Contact number: ");
        String contact = scan.nextLine();

        String insertSQL = "INSERT INTO adopter_info "
                + "(full_name, age, occupation, contact_number) "
                + "VALUES (?, ?, ?, ?)";

        String updateSQL = "UPDATE animal_info "
                + "SET adoption_status = 'Adopted', adopter_id = ? "
                + "WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(
                     insertSQL, PreparedStatement.RETURN_GENERATED_KEYS)) {

            insertStmt.setString(1, fullName);
            insertStmt.setInt(2, adopterAge);
            insertStmt.setString(3, occupation);
            insertStmt.setString(4, contact);

            insertStmt.executeUpdate();

            try (ResultSet keys = insertStmt.getGeneratedKeys()) {
                if (keys.next()) {
                    int adopterID = keys.getInt(1);

                    try (PreparedStatement updateStmt =
                                 conn.prepareStatement(updateSQL)) {

                        updateStmt.setInt(1, adopterID);
                        updateStmt.setInt(2, animalID);
                        updateStmt.executeUpdate();

                        System.out.println("Adoption recorded successfully!");
                    }
                } else {
                    System.out.println("Could not get the adopter ID.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error processing adoption: "
                    + e.getMessage());
        }
    }

    public void Update_Animal_Profile() {
        System.out.println("\n=== Update Animal Profile ===");

        int animalID = getValidInt("Enter animal ID: ");

        String searchSQL = "SELECT * FROM animal_info WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(searchSQL)) {

            stmt.setInt(1, animalID);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Animal: "
                            + rs.getString("animal_name"));
                    System.out.println("Health: "
                            + rs.getString("health_status"));
                    System.out.println("Weight: "
                            + rs.getDouble("weight_kg"));
                    System.out.println("Adoption status: "
                            + rs.getString("adoption_status"));
                } else {
                    System.out.println("Animal ID not found.");
                    return;
                }
            }

            System.out.println("\nWhat would you like to update?");
            System.out.println("[1] Health Status");
            System.out.println("[2] Weight");
            System.out.println("[3] Adoption Status");

            int choice = getValidInt("Choose an option: ");

            String updateSQL = "";

            switch (choice) {
                case 1:
                    System.out.print("Enter new health status: ");
                    String health = scan.nextLine();

                    updateSQL = "UPDATE animal_info "
                            + "SET health_status = ? WHERE id = ?";

                    try (PreparedStatement updateStmt =
                                 conn.prepareStatement(updateSQL)) {

                        updateStmt.setString(1, health);
                        updateStmt.setInt(2, animalID);
                        updateStmt.executeUpdate();
                    }
                    break;

                case 2:
                    double weight = getValidDouble("Enter new weight: ");

                    updateSQL = "UPDATE animal_info "
                            + "SET weight_kg = ? WHERE id = ?";

                    try (PreparedStatement updateStmt =
                                 conn.prepareStatement(updateSQL)) {

                        updateStmt.setDouble(1, weight);
                        updateStmt.setInt(2, animalID);
                        updateStmt.executeUpdate();
                    }
                    break;

                case 3:
                    System.out.print("Enter new adoption status: ");
                    String status = scan.nextLine();

                    updateSQL = "UPDATE animal_info "
                            + "SET adoption_status = ? WHERE id = ?";

                    try (PreparedStatement updateStmt =
                                 conn.prepareStatement(updateSQL)) {

                        updateStmt.setString(1, status);
                        updateStmt.setInt(2, animalID);
                        updateStmt.executeUpdate();
                    }
                    break;

                default:
                    System.out.println("Invalid option.");
                    return;
            }

            if (choice >= 1 && choice <= 3) {
                System.out.println("Animal profile updated successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error updating profile: "
                    + e.getMessage());
        }
    }

    public void View_Shelter_Directory() {
        System.out.println("\n=== Shelter Directory ===");
        System.out.println("[1] View Dogs");
        System.out.println("[2] View Cats");
        System.out.println("[3] View All Animals");

        int choice = getValidInt("Choose an option: ");

        String sql = "SELECT * FROM animal_info";

        if (choice == 1) {
            sql += " WHERE LOWER(species) = 'dog'";
        } else if (choice == 2) {
            sql += " WHERE LOWER(species) = 'cat'";
        } else if (choice != 3) {
            System.out.println("Invalid option.");
            return;
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.printf(
                    "%-5s %-12s %-8s %-10s %-5s %-12s %-10s %-8s %-8s %-10s %-18s%n",
                    "ID", "Name", "Species", "Breed", "Age",
                    "Health", "Status", "Weight", "Height",
                    "Eye Color", "Color Pattern");

            System.out.println(
                    "----------------------------------------------------------------------------------------------------------------");

            boolean found = false;

            while (rs.next()) {
                found = true;

                System.out.printf(
                        "%-5d %-12s %-8s %-10s %-5d %-12s %-10s %-8.1f %-8.1f %-10s %-18s%n",
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
                        rs.getString("color_pattern"));
            }

            if (!found) {
                System.out.println("No animals found.");
            }

        } catch (SQLException e) {
            System.out.println("Error viewing directory: "
                    + e.getMessage());
        }
    }
}

