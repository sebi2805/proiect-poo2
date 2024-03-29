package main.entities;

import main.entities.enums.MedicalSpecialty;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

public class GeneralPractitioner extends Medic {
    // If there are no new attributes unique to GeneralPractitioner, no additional fields are needed

    // Constructor
    public GeneralPractitioner(String ID, String name, String phone, String email, String availableHours)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(ID, name, phone, email, MedicalSpecialty.GENERAL_PRACTITIONER, availableHours); // Assuming "General Medicine" as the fixed specialty
    }

    // Override the displayDetails method to customize the output for General Practitioners
    @Override
    public void displayDetails() {
        // Call the superclass method to display general Medic info
        super.displayDetails();
        // Additional or customized details for a General Practitioner could be added here
        System.out.println("Role: General Practitioner");
    }

    // General Practitioner-specific method
    public void provideGeneralCare() {
        System.out.println("Providing general care...");
        // Implementation of general care procedures could go here
    }
}
