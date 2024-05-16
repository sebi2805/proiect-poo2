package main.menus;

import main.services.*;

import java.util.Scanner;

public class MainMenu {
    private static final Scanner scanner = new Scanner(System.in);
    private static final ClientService clientService = ServiceManager.getClientService();
    private static final MedicService medicService = ServiceManager.getMedicService();
    private static final MedicalRecordService medicalRecordService = ServiceManager.getMedicalRecordService();
    private static final ScheduleService regularAppointmentService = ServiceManager.getScheduleService();
    private static final AppointmentService oneTimeAppointmentService = ServiceManager.getAppointmentService();

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    new ClientMenu(clientService).displayMenu();
                    break;
                case 2:
                    new MedicMenu(medicService).displayMenu();
                    break;
                case 3:
                    new MedicalRecordMenu(medicalRecordService, clientService, medicService).displayMenu();
                    break;
                case 4:
                    new RegularAppointmentMenu(regularAppointmentService).displayMenu();
                    break;
                case 5:
                    new OneTimeAppointmentMenu(oneTimeAppointmentService).displayMenu();
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void printMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("1. Clients");
        System.out.println("2. Medics");
        System.out.println("3. Medical Records");
        System.out.println("4. Regular Appointments");
        System.out.println("5. One-Time Appointments");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
}
