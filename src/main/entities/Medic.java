package main.entities;

import com.opencsv.bean.CsvCustomBindByPosition;
import main.enums.MedicalSpecialty;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;
import main.util.LocalTimeConverter;
import main.util.MedicalSpecialtyConverter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Medic extends Person {
    @CsvCustomBindByPosition(position = 7, converter = MedicalSpecialtyConverter.class)
    private MedicalSpecialty specialty;

    @CsvCustomBindByPosition(position = 8, converter = LocalTimeConverter.class)
    private LocalTime workingHoursStart;

    @CsvCustomBindByPosition(position = 9, converter = LocalTimeConverter.class)
    private LocalTime workingHoursEnd;

    public Medic(String name, String phone, String email, MedicalSpecialty specialty,
                 LocalTime workingHoursStart, LocalTime workingHoursEnd)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(name, phone, email);
        this.workingHoursStart = workingHoursStart;
        this.workingHoursEnd = workingHoursEnd;
        this.specialty = specialty;
    }
    public Medic(){
        super();
    }

    public MedicalSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(MedicalSpecialty specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return "Medic ID: " + getId() + "\n" +
                "Name: " + getName() + "\n" +
                "Phone: " + getPhone() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Specialty: " + specialty + "\n" +
                "Working Hours Start: " + workingHoursStart + "\n" +
                "Working Hours End: " + workingHoursEnd;
    }

    public LocalTime getWorkingHoursStart() {
        return workingHoursStart;
    }

    public void setWorkingHoursStart(LocalTime workingHoursStart) {
        this.workingHoursStart = workingHoursStart;
    }

    public LocalTime getWorkingHoursEnd() {
        return workingHoursEnd;
    }

    public void setWorkingHoursEnd(LocalTime workingHoursEnd) {
        this.workingHoursEnd = workingHoursEnd;
    }
}
