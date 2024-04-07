package main.entities;

import main.enums.MedicalSpecialty;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class GeneralPractitioner extends Medic {
    // Constructor
    public GeneralPractitioner(String name, String phone, String email, String availableHours,
                               LocalTime workingHoursStart, LocalTime workingHoursEnd)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(name, phone, email, MedicalSpecialty.GENERAL_PRACTITIONER, workingHoursStart, workingHoursEnd); // Assuming "General Medicine" as the fixed specialty
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
