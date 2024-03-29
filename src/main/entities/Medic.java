package main.entities;

import main.entities.enums.MedicalSpecialty;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

import java.util.ArrayList;
import java.util.List;

public class Medic extends Person {
    private MedicalSpecialty specialty;
    private String availableHours;
    private final List<Appointment> appointments; // List to track appointments

    // Constructor
    public Medic(String ID, String name, String phone, String email, MedicalSpecialty specialty, String availableHours)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(ID, name, phone, email); // Call to the superclass (Person) constructor
        this.specialty = specialty;
        this.availableHours = availableHours;
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

    public String getAvailableHours() {
        return availableHours;
    }

    public void setAvailableHours(String availableHours) {
        this.availableHours = availableHours;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    // Implementing the abstract method from Person class
    @Override
    public void displayDetails() {
        System.out.println("Medic ID: " + getID());
        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Specialty: " + specialty);
        System.out.println("Available Hours: " + availableHours);
        // Optionally display the appointment details
        for (Appointment appointment : appointments) {
            System.out.println("Appointment ID: " + appointment.getID());
            // More details can be printed out here if needed
        }
    }
}
