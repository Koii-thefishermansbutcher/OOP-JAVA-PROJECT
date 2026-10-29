import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Register extends AnimalAction {
    @Override
    public void run() {
        Add_Animal();
    }

    public void Add_Animal() {
        System.out.println("\n=== Register a New Animal ===");

        String name = getRequiredText("Animal name: ");
        String species = getRequiredText("Species (Dog, Cat, or other): ");
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
            health = getRequiredText("Health status (Healthy, Sick, Under Treatment, etc.): ");
            breed = getRequiredText("Breed: ");

            weight = getValidDouble("Weight in kg: ");
            height = getValidDouble("Height in cm: ");

            eyeColor = getRequiredText("Eye color: ");
            pattern = getRequiredText("Color pattern: ");
        }

        System.out.println("\nPlease check the animal details:");
        System.out.println("Name: " + name + " | Species: " + species + " | Age: " + age);
        System.out.println("Health: " + health + " | Breed: " + breed);
        if (!confirmAction("Save this animal record?")) {
            System.out.println("Registration cancelled. No record was saved.");
            return;
        }

        String sql = "INSERT INTO animal_info "
                + "(animal_name, species, age, health_status, adoption_status, "
                + "breed, weight_kg, height_cm, eye_color, color_pattern, "
                + "accepted_date, adopted_date) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_DATE, NULL)";

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
            System.out.println("Date accepted into shelter: " + java.time.LocalDate.now());

        } catch (SQLException e) {
            System.out.println("Error adding animal: " + e.getMessage());
        }

        System.out.println("\n[1] View Shelter Directory");
        System.out.println("[2] Add Another Animal");
        System.out.println("[3] Return to Main Menu");

        int next = getValidInt("Choose an option: ");

        switch (next) {
            case 1:
                new View().View_Shelter_Directory();
                break;
            case 2:
                new Register().Add_Animal();
                break;
            case 3:
                break;
            default:
                System.out.println("Invalid option.");
        }
    }
}