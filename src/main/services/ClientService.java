package main.services;

import main.entities.Client;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.storage.FileService;

import java.util.Optional;

public class ClientService {

    private static ClientService instance;
    private final FileService fileService;

    // Private constructor to prevent instantiation
    private ClientService() {
        this.fileService = FileService.getInstance();
    }

    // Public method to get the singleton instance
    public static synchronized ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    // Method to add a new client
    public void addClient(Client client) throws AlreadyExistsException {
        Optional<Client> existingClient = fileService.getClientManager().findById(client.getId());
        if (existingClient.isPresent()) {
            System.err.println("Client with ID " + client.getId() + " already exists.");
            // Optionally, throw a custom exception or handle this case as needed
            return; // Prevent adding the new client
        }
        fileService.getClientManager().add(client);
        System.out.println("Client successfully added.");
    }

    // Method to retrieve a client by ID
    public Client getClientById(String clientId) {
        return fileService.getClientManager().findById(clientId).orElse(null);
    }
    public void updateClient(Client client) throws NotFoundException {
            fileService.getClientManager().update(client);
            System.out.println("Client successfully updated.");
    }

    public void deleteClient(String clientId) throws NotFoundException {
        Optional<Client> clientOptional = fileService.getClientManager().findById(clientId);
        if (clientOptional.isPresent()) {
            fileService.getClientManager().delete(clientId);
            System.out.println("Client with ID " + clientId + " successfully deleted.");
        } else {
            System.err.println("Client with ID " + clientId + " not found.");
            // Handle the case where the client doesn't exist as needed
        }
    }

}
