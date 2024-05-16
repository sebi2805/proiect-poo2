package main.entities;

import main.enums.MedicalSpecialty;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Medic extends Person {
    private MedicalSpecialty specialty;
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;
    private List<Appointment> appointments;

    public Medic(String name, String phone, String email, MedicalSpecialty specialty,
                 LocalTime workingHoursStart, LocalTime workingHoursEnd)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(name, phone, email);
        this.workingHoursStart = workingHoursStart;
        this.workingHoursEnd = workingHoursEnd;
        this.specialty = specialty;
        this.appointments = new ArrayList<>();
    }
    public Medic(){
        super();
    }

    public void addAppointment(Appointment appointment) {
        if (appointment != null) {
            this.appointments.add(appointment);
        }
    }

    public void removeAppointment(Appointment appointment) {
        if (appointment != null) {
            this.appointments.remove(appointment);
        }
    }

    public MedicalSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(MedicalSpecialty specialty) {
        this.specialty = specialty;
    }

    public List<Appointment> getAppointments() {
        return appointments;
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
