import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner globalScan = AnimalAction.scan;
        boolean running = true;

        while (running) {
            System.out.println("\n=============================================");
            System.out.println("   ANIMAL SHELTER MANAGEMENT SYSTEM v2.6     ");
            System.out.println("=============================================");
            System.out.println(" [1] Register a New Animal");
            System.out.println(" [2] View Shelter Directory");
            System.out.println(" [3] Process an Animal Adoption");
            System.out.println(" [4] Update Animal Profile");
            System.out.println(" [5] Search Animals");
            System.out.println(" [6] View Adoption History");
            System.out.println(" [7] View Shelter Statistics");
            System.out.println(" [8] Shut Down System");
            System.out.print("Please enter an option (1-8): ");

            if (!globalScan.hasNextInt()) {
                System.out.println("Invalid option. Please enter a whole number.");
                globalScan.nextLine();
                continue;
            }

            int menuChoice = globalScan.nextInt();
            globalScan.nextLine();

            switch (menuChoice) {
                case 1:
                    new Register().run();
                    break;
                case 2:
                    new View().run();
                    break;
                case 3:
                    new Adoption().run();
                    break;
                case 4:
                    new Update().run();
                    break;
                case 5:
                    new SearchAnimals().run();
                    break;
                case 6:
                    new AdoptionHistory().run();
                    break;
                case 7:
                    new ShelterStatistics().run();
                    break;
                case 8:
                    System.out.println("System closed. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose from 1 to 8.");
            }
        }
        globalScan.close();
    }
}
