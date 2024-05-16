package main.entities;

import main.enums.MedicalSpecialty;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

import java.time.LocalTime;

public class GeneralPractitioner extends Medic {
    public GeneralPractitioner(String name, String phone, String email, String availableHours,
                               LocalTime workingHoursStart, LocalTime workingHoursEnd)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(name, phone, email, MedicalSpecialty.GENERAL_PRACTITIONER, workingHoursStart, workingHoursEnd);
    }

    @Override
    public String toString() {
        String parentString = super.toString();
        String roleInfo = "Role: General Practitioner";
        return parentString + "\n" + roleInfo;
    }

    public void provideGeneralCare() {
        System.out.println("Providing general care...");
    }
}
