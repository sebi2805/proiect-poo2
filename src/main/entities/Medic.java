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
    private final List<Appointment> appointments; // List to track appointments

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
    public void displayDetails() {
        System.out.println("Medic ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Specialty: " + specialty);
//        System.out.println("Available Hours: " + availableHours);
        // Optionally display the appointment details
        for (Appointment appointment : appointments) {
            System.out.println("Appointment ID: " + appointment.getId());
            // More details can be printed out here if needed
        }
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
