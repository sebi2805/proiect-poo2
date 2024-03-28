package main.entities;

import java.time.LocalDateTime;

public class OneTimeAppointment extends Appointment {
    // Constructor
    public OneTimeAppointment(String ID, String clientID, String medicID, LocalDateTime appointmentDate, String status) {
        super(ID, clientID, medicID, appointmentDate, status);
    }

    // Implementing abstract methods from Appointment
    @Override
    public void confirmAppointment() {
        System.out.println("One-Time Appointment confirmed for " + appointmentDate);
        // Additional logic specific to confirming a one-time appointment can be added here
    }

    @Override
    public void cancelAppointment() {
        System.out.println("One-Time Appointment on " + appointmentDate + " cancelled.");
        // Additional logic specific to cancelling a one-time appointment can be added here
    }
}
