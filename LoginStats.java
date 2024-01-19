package project3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * The LoginStats class processes and manages login records, allowing users to 
 * view various statistics related to the sessions.The LoginStats class is the actual program.
 *  This is the class that contains the main method.
 * author Vedant Desai
 */
public class LoginStats {

    private RecordList recordList = new RecordList();  // List to store login/logout records
    private SortedLinkedList<Session> sessions = new SortedLinkedList<>();  // List to store session details
    
    /**
     * Entry point for the LoginStats program.
     *
     * @param args Command line arguments (unused in this context).
     */
    public static void main(String[] args) {
        LoginStats loginStats = new LoginStats();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter path to the data file: ");
        String filePath = scanner.nextLine();

        try {
            loginStats.loadRecordsFromFile(filePath);
            loginStats.processRecords();
            loginStats.displayMenu(scanner);

        } catch (IOException e) {
            System.out.println("Error reading from the file. Please check the file path and content.");
        } catch (IllegalArgumentException e) {
            System.out.println("Data error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
        } finally {
            scanner.close();
        }
    }

    /**
     * Loads login/logout records from a file.
     *
     * @param filePath The path to the file containing the records.
     * @throws IOException If there's an issue reading the file.
     */
    private void loadRecordsFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int terminal = Integer.parseInt(parts[0]);
                long timeInLong = Long.parseLong(parts[1]);
                Date time = new Date(timeInLong);
                String username = parts[2];
                boolean isLogin = line.contains("login");

                recordList.add(new Record(terminal, isLogin, username, time));
            }
        }
    }

    /**
     * Processes records from the recordList to create sessions.
     */
    private void processRecords() {
        RecordList.Node current = recordList.getHead();

        while (current != null) {
            Record loginRecord = current.data;

            if (loginRecord.isLogin()) {
                Record logoutRecord = recordList.findMatchingLogoutRecord(current, loginRecord.getUsername());
                if (logoutRecord != null) {
                    sessions.add(new Session(loginRecord, logoutRecord));
                }
            }
            current = current.next;
        }
    }

    /**
     * Displays all the sessions to the console.
     */
    private void displaySessions() {
        for (Session session : sessions) {
            System.out.println(session.toString());
        }
    }

    /**
     * Displays a menu to the user to provide options for interacting with the program.
     *
     * @param scanner Scanner object to read user input.
     */
    private void displayMenu(Scanner scanner) {
        boolean continueRunning = true;
        while (continueRunning) {
            System.out.println("1. Display Sessions");
            System.out.println("2. Exit");
            System.out.print("Select an option: ");

            try {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        displaySessions();
                        break;
                    case 2:
                        continueRunning = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next(); // clear the invalid input
            }
        }
    }
}
