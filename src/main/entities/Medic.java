package main.entities;

import main.enums.MedicalSpecialty;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Medic extends Person {
    private MedicalSpecialty specialty;
    private LocalTime workingHoursStart;
    private LocalTime workingHoursEnd;
    private List<Appointment> appointments;

    // Constructor
    public Medic(String name, String phone, String email, MedicalSpecialty specialty,
                 LocalTime workingHoursStart, LocalTime workingHoursEnd)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(name, phone, email); // Call to the superclass (Person) constructor
        this.workingHoursStart = workingHoursStart;
        this.workingHoursEnd = workingHoursEnd;
        this.specialty = specialty;
        this.appointments = new ArrayList<>(); // Initialize the appointments list
    }
    public Medic(){
        super();
    }

    // Method to add an appointment to the list
    public void addAppointment(Appointment appointment) {
        if (appointment != null) {
            this.appointments.add(appointment);
        }
    }

    // Method to remove an appointment from the list
    public void removeAppointment(Appointment appointment) {
        if (appointment != null) {
            this.appointments.remove(appointment);
        }
    }

    // Getters and Setters for the new attributes
    public MedicalSpecialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(MedicalSpecialty specialty) {
        this.specialty = specialty;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    // Implementing the abstract method from Person class
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
