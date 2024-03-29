package main.entities;

import main.enums.MedicalSpecialty;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

public class Surgeon extends Medic {
    // If there are no new attributes, we don't need to add additional fields here

    // Constructor
    public Surgeon(String ID, String name, String phone, String email, String availableHours)  throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(ID, name, phone, email, MedicalSpecialty.SURGEON, availableHours); // Assuming "Surgery" as the fixed specialty for all Surgeons
    }

    // Override the displayDetails method if we want to add or change the information displayed for Surgeons
    @Override
    public void displayDetails() {
        // Call the superclass method to display general Medic info
        super.displayDetails();
        // Add any Surgeon-specific details here, for now, we just add a print line to differentiate
        System.out.println("Role: Surgeon");
    }

    // Example of a Surgeon-specific method
    public void performSurgery() {
        System.out.println("Performing surgery...");
        // Implementation of surgery procedure could go here
    }
}
