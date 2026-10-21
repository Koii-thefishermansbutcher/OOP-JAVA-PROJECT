import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner globalScan = new Scanner(System.in);
        Animal shelter = new Animal();
        boolean running = true;

        while (running) {
            System.out.println("\n=============================================");
            System.out.println("   ANIMAL SHELTER MANAGEMENT SYSTEM v2.5     ");
            System.out.println("=============================================");
            System.out.println(" [1] Register a New Animal");
            System.out.println(" [2] View Shelter Database Logs");
            System.out.println(" [3] Process an Animal Adoption");
            System.out.println(" [4] Update Existing Animal Profile"); 
            System.out.println(" [5] Shut Down System");
            System.out.print("Please enter a selection option (1-5): ");
            
            if (!globalScan.hasNextInt()) {
                System.out.println("❌ Invalid option. Please enter a number between 1 and 5.");
                globalScan.nextLine(); 
                continue;
            }
            
            int menuChoice = globalScan.nextInt();
            globalScan.nextLine(); 

            switch (menuChoice) {
                case 1:
                    shelter.Add_Animal();
                    break;
                case 2:
                    shelter.View_Shelter_Directory();
                    break;
                case 3:
                    shelter.Adopt_Animal();
                    break;
                case 4:
                    shelter.Update_Animal_Profile(); 
                    break;
                case 5:
                    System.out.println("Safely flushing connections... System closed. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid selection option code. Try again.");
            }
        }
        globalScan.close();
    }
}