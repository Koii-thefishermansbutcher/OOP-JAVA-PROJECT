import java.util.Scanner;

public abstract class AnimalAction extends Database {
    protected static Scanner scan = new Scanner(System.in);

    public int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt);

            if (scan.hasNextInt()) {
                int number = scan.nextInt();
                scan.nextLine();

                if (number >= 0) {
                    return number;
                }
                System.out.println("Please enter zero or a positive whole number.");
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

                if (number >= 0) {
                    return number;
                }
                System.out.println("The value cannot be negative.");
            } else {
                System.out.println("Please enter a valid number.");
                scan.nextLine();
            }
        }
    }

    public String getRequiredText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scan.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }

    public boolean confirmAction(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String answer = scan.nextLine().trim();

            if (answer.equalsIgnoreCase("Y")) {
                return true;
            }
            if (answer.equalsIgnoreCase("N")) {
                return false;
            }
            System.out.println("Please enter Y or N.");
        }
    }

    public abstract void run();
}