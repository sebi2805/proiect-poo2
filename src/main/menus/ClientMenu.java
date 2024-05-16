package main.menus;

import main.entities.Client;
import main.exceptions.AlreadyExistsException;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;
import main.exceptions.NotFoundException;
import main.services.ClientService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientMenu extends EntityMenu<Client> {

    public ClientMenu(ClientService service) {
        super(service);
    }

    @Override
    protected void printMenuOptions() {
        System.out.println("\nClient Menu:");
        System.out.println("1. Display all Clients");
        System.out.println("2. Search Client by ID");
        System.out.println("3. Add Client");
        System.out.println("4. Update Client");
        System.out.println("5. Delete Client");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    @Override
    protected void displayAll() {
        List<Client> clients = service.getAll();
        for (Client client : clients) {
            System.out.println(client);
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
        // Initializing appointmentIdsSerialized and medicalRecordIdsSerialized as empty strings
        String appointmentIdsSerialized = "";
        String medicalRecordIdsSerialized = "";

        try {
            Client client = new Client(name, phone, email, appointmentIdsSerialized, medicalRecordIdsSerialized);
            service.add(client);
            System.out.println("Client added successfully.");
        } catch (InvalidPhoneNumberException | InvalidEmailFormatException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (AlreadyExistsException e) {
            System.out.println("Error: Client already exists.");
        }
    }

    @Override
    protected void update() {
        System.out.print("Enter Client ID: ");
        String id = scanner.nextLine();
        Optional<Client> optionalClient = service.getById(id);
        if (optionalClient.isPresent()) {
            Client client = optionalClient.get();
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
                    return;
                }
            }
            try {
                service.update(client);
                System.out.println("Client updated successfully.");
            } catch (NotFoundException e) {
                System.out.println("Error: Client not found.");
            }
        } else {
            System.out.println("Client not found.");
        }
    }

    @Override
    protected void delete() {
        System.out.print("Enter Client ID: ");
        String id = scanner.nextLine();
        try {
            service.delete(id);
            System.out.println("Client deleted successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: Client not found.");
        }
    }
}
