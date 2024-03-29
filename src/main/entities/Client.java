package main.entities;

import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

import java.util.ArrayList;
import java.util.List;

public class Client extends Person {
    private List<String> medicalHistory;
    private List<Appointment> appointments; // List to keep track of appointments

    // Constructor
    public Client(String ID, String name, String phone, String email, List<String> medicalHistory)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(ID, name, phone, email); // Call to the superclass (Person) constructor
        this.medicalHistory = new ArrayList<>(medicalHistory); // Initialize medical history with a copy of the provided list
    }

    // Getters and Setters
    public List<String> getMedicalHistory() {
        return new ArrayList<>(medicalHistory); // Return a copy to maintain encapsulation
    }

    public void setMedicalHistory(List<String> medicalHistory) {
        this.medicalHistory = new ArrayList<>(medicalHistory); // Set a copy to maintain encapsulation
    }

    public void addMedicalHistory(String historyEntry) {
        medicalHistory.add(historyEntry); // Add a new entry to the medical history
    }

    // Method to add an appointment to the list
    public void addAppointment(Appointment appointment) {
        if (appointments == null) {
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
    }

    // Method to get all appointments
    public List<Appointment> getAppointments() {
        return appointments;
    }

    // Implementing the abstract method from Person class
    @Override
    public void displayDetails() {
        System.out.println("Client ID: " + getID());
        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Medical History: ");
        for (String entry : medicalHistory) {
            System.out.println("- " + entry);
        }
    }
}
