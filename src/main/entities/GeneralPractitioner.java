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
    public String toString() {
        String parentString = super.toString();
        String roleInfo = "Role: General Practitioner";
        return parentString + "\n" + roleInfo;
    }

    // General Practitioner-specific method
    public void provideGeneralCare() {
        System.out.println("Providing general care...");
        // Implementation of general care procedures could go here
    }
}
