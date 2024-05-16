package main.entities;

import main.enums.AppointmentStatus;
import java.time.LocalDateTime;

public class OneTimeAppointment extends Appointment {
    // Constructor
    public OneTimeAppointment(String clientId, String medicId, LocalDateTime appointmentDate, AppointmentStatus status) {
        super(clientId, medicId, appointmentDate, status);
    }
    public OneTimeAppointment() {
        super();
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
    @Override
    public String toString(){
        return super.toString();
    }
}
