package main.menus;

import main.entities.*;
import main.exceptions.AlreadyExistsException;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;
import main.exceptions.NotFoundException;
import main.services.AppointmentService;
import main.services.ClientService;
import main.services.MedicalRecordService;
import main.services.ScheduleService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientMenu extends EntityMenu<Client> {

    private final MedicalRecordService medicalRecordService;
    private final AppointmentService appointmentService;
    private final ScheduleService scheduleService;

    public ClientMenu(ClientService clientService, MedicalRecordService medicalRecordService, AppointmentService appointmentService, ScheduleService scheduleService) {
        super(clientService);
        this.medicalRecordService = medicalRecordService;
        this.appointmentService = appointmentService;
        this.scheduleService = scheduleService;
    }

    @Override
    protected void printMenuOptions() {
        System.out.println("\nClient Menu:");
        System.out.println("1. Display all Clients");
        System.out.println("2. Search Client by ID");
        System.out.println("3. Add Client");
        System.out.println("4. Update Client");
        System.out.println("5. Delete Client");
        System.out.println("6. Get Medical Records for Client");
        System.out.println("7. Get One-Time Appointments for Client");
        System.out.println("8. Get Regular Appointments for Client");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }
    @Override
    protected boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1:
                displayAll();
                break;
            case 2:
                searchById();
                break;
            case 3:
                add();
                break;
            case 4:
                update();
                break;
            case 5:
                delete();
                break;
            case 6:
                getMedicalRecordsForClient();
                break;
            case 7:
                getOneTimeAppointmentsForClient();
                break;
            case 8:
                getRegularAppointmentsForClient();
                break;
            case 0:
                return false; // Exit to main menu
            default:
                System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }
    @Override
    protected void displayAll() {
        List<Client> clients = service.getAll();
        for (Client client : clients) {
            System.out.println(client);

            int medicalRecordCount = medicalRecordService.getRecordsByClientId(client.getId()).size();
            int appointmentsCount = appointmentService.getAppointmentsByClientId(client.getId()).size();
            int regularAppointmentsCount = scheduleService.getAppointmentsByClientId(client.getId()).size();

            System.out.printf("Medical Records: %d\n", medicalRecordCount);
            System.out.printf("One-Time Appointments: %d\n", appointmentsCount);
            System.out.printf("Regular Appointments: %d\n", regularAppointmentsCount);
            System.out.println("\n");
        }
    }

    @Override
    protected void searchById() {
        System.out.print("Enter Client ID: ");
        String id = scanner.nextLine();
        Optional<Client> client = service.getById(id);
        client.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Client not found.")
        );
    }

    @Override
    protected void add() {
        System.out.print("Enter Client Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Client Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Client Email: ");
        String email = scanner.nextLine();

        try {
            Client client = new Client(name, phone, email);
            service.add(client);
            System.out.println("Client added successfully.");
        } catch (InvalidPhoneNumberException | InvalidEmailFormatException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (AlreadyExistsException e) {
            System.out.println("Error: Client already exists.");
        }
    }
    private Client chooseClient() {
        List<Client> clients = service.getAll();
        if (clients.isEmpty()) {
            System.out.println("No clients available.");
            waitForUserInput();
            return null;
        }

        for (int i = 0; i < clients.size(); i++) {
            System.out.printf("%d. %s : %s\n", i + 1, clients.get(i).getName(), clients.get(i).getEmail());
        }

        System.out.print("Select a client (enter the number): ");
        int clientIndex;
        try {
            clientIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (clientIndex < 0 || clientIndex >= clients.size()) {
                System.out.println("Invalid selection. Please try again.");
                waitForUserInput();
                return null;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            waitForUserInput();
            return null;
        }

        return clients.get(clientIndex);
    }
    @Override
    protected void update() {
        Client client = chooseClient();
        if (client == null) {
            return;
        }

        System.out.println("Current details: " + client);

        System.out.print("Enter new Client Name (leave blank to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            client.setName(name);
        }

        System.out.print("Enter new Client Phone (leave blank to keep current): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            try {
                client.setPhone(phone);
            } catch (InvalidPhoneNumberException e) {
                System.out.println("Error: " + e.getMessage());
                waitForUserInput();
                return;
            }
        }

        System.out.print("Enter new Client Email (leave blank to keep current): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            try {
                client.setEmail(email);
            } catch (InvalidEmailFormatException e) {
                System.out.println("Error: " + e.getMessage());
                waitForUserInput();
                return;
            }
        }

        try {
            service.update(client);
            System.out.println("Client updated successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: Client not found.");
        }

        waitForUserInput();
    }

    @Override
    protected void delete() {
        Client client = chooseClient();
        if (client == null) {
            return;
        }

        try {
            service.delete(client.getId());
            System.out.println("Client deleted successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: Client not found.");
        }

        waitForUserInput();
    }

    private void getMedicalRecordsForClient() {
        Client client = chooseClient();
        if (client == null) {
            return;
        }

        List<MedicalRecord> records = medicalRecordService.getRecordsByClientId(client.getId());
        if (records.isEmpty()) {
            System.out.println("No medical records found for this client.");
        } else {
            System.out.println("Medical Records:");
            for (MedicalRecord record : records) {
                System.out.println(record);
            }
        }
        waitForUserInput();
    }

    private void getOneTimeAppointmentsForClient() {
        Client client = chooseClient();
        if (client == null) {
            return;
        }

        List<OneTimeAppointment> appointments = appointmentService.getAppointmentsByClientId(client.getId());
        if (appointments.isEmpty()) {
            System.out.println("No one-time appointments found for this client.");
        } else {
            System.out.println("One-Time Appointments:");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
        waitForUserInput();
    }

    private void getRegularAppointmentsForClient() {
        Client client = chooseClient();
        if (client == null) {
            return;
        }

        List<RegularAppointment> appointments = scheduleService.getAppointmentsByClientId(client.getId());
        if (appointments.isEmpty()) {
            System.out.println("No regular appointments found for this client.");
        } else {
            System.out.println("Regular Appointments:");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
        waitForUserInput();
    }
}
