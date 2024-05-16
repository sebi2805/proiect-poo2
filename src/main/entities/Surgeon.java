package main.entities;

import main.enums.MedicalSpecialty;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Surgeon extends Medic {
    public Surgeon(String name, String phone, String email, LocalTime workingHoursStart, LocalTime workingHoursEnd)  throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(name, phone, email, MedicalSpecialty.SURGEON, workingHoursStart, workingHoursEnd); // Assuming "Surgery" as the fixed specialty for all Surgeons
    }

    @Override
    public String toString() {
        String parentString = super.toString();
        String roleInfo = "Role: Surgeon";
        return parentString + "\n" + roleInfo;
    }


    public void performSurgery() {
        System.out.println("Performing surgery...");
    }
}
