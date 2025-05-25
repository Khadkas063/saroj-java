package ems;

import javax.swing.SwingUtilities;
import java.util.Scanner;

/**
 * Main entry point of the application.
 * Allows the user to choose between GUI or Text-Based Interface.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Employee Management System");
        System.out.println("Select Interface Mode:");
        System.out.println("1 - GUI");
        System.out.println("2 - Text-Based Interface (Console)");
        System.out.print("Enter choice (1 or 2): ");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                // Launch GUI
                SwingUtilities.invokeLater(() -> {
                    GUIInterface gui = new GUIInterface();
                    gui.setVisible(true);
                });
                break;
            case "2":
                // Launch Console Interface
                ConsoleInterface console = new ConsoleInterface();
                console.start();
                break;
            default:
                System.out.println("Invalid choice. Exiting application.");
                System.exit(0);
        }
    }
}
