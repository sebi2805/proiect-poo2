package main.entities;

import java.time.LocalDateTime;
import main.entities.enums.AppointmentStatus; // Import the enum if it's in a different package

public abstract class Appointment {
    protected String ID;
    protected Client client; // Reference to the Client object
    protected Medic medic; // Reference to the Medic object
    protected LocalDateTime appointmentDate;
    protected AppointmentStatus status; // Use the enum type

    // Constructor
    public Appointment(String ID, Client client, Medic medic, LocalDateTime appointmentDate, AppointmentStatus status) {
        this.ID = ID;
        this.client = client;
        this.medic = medic;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }

    // Getters and Setters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
    public LocalDateTime getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDateTime appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Medic getMedic() {
        return medic;
    }

    public void setMedic(Medic medic) {
        this.medic = medic;
    }
    // Abstract methods
    public abstract void confirmAppointment();
    public abstract void cancelAppointment();
}
