package main;

import main.entities.Client;
import main.entities.MedicalRecord;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;
import main.services.ServiceManager;

import java.util.List;

public class Main {
    public static void main(String[] args) throws InvalidPhoneNumberException, InvalidEmailFormatException {
        ServiceManager serviceManager = ServiceManager.getInstance();

        // Creăm un client hardcodat
        Client newClient = new Client("1", "John Doe", "1234567890","john.doe@example.com");

        try {
            // Adăugăm clientul în sistem
            serviceManager.getClientService().addClient(newClient);
            System.out.println("Clientul a fost adăugat cu succes în sistem.");
        } catch (Exception e) {
            // Afișăm un mesaj în caz de eroare
            System.err.println("A apărut o eroare la adăugarea clientului: " + e.getMessage());
        }
    }
}
