package main.entities;

import java.time.LocalDateTime;

import main.entities.enums.AppointmentFrequency;
import main.entities.enums.AppointmentStatus;

public class RegularAppointment extends Appointment {
    private AppointmentFrequency frequency; // Use enum type

    // Constructor
    public RegularAppointment(String ID, Client client, Medic medic, LocalDateTime appointmentDate, AppointmentStatus status, AppointmentFrequency frequency) {
        super(ID, client, medic, appointmentDate, status); // Pass enum for status
        this.frequency = frequency;
    }

    // Getter and Setter for frequency
    public AppointmentFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(AppointmentFrequency frequency) {
        this.frequency = frequency;
    }

    // Implementing abstract methods from Appointment
    @Override
    public void confirmAppointment() {
        System.out.println("Regular Appointment confirmed for " + appointmentDate + " with frequency " + frequency);
        // Additional logic specific to confirming a regular appointment can be added here
    }

    @Override
    public void cancelAppointment() {
        System.out.println("Regular Appointment on " + appointmentDate + " cancelled.");
        // Additional logic specific to cancelling a regular appointment can be added here
    }
}
