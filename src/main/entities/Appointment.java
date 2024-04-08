package main.entities;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByPosition;
import main.enums.AppointmentStatus; // Import the enum if it's in a different package

public abstract class Appointment
        extends BaseEntity
        implements Comparable<Appointment> {
    @CsvBindByPosition(position = 4)
    protected Client client;
    @CsvBindByPosition(position = 5)
    protected Medic medic;
    @CsvBindByPosition(position = 6)
    protected LocalDateTime appointmentDate;
    @CsvBindByPosition(position = 7)
    protected AppointmentStatus status;

    // Constructor
    public Appointment(Client client, Medic medic, LocalDateTime appointmentDate, AppointmentStatus status) {
        super();
        this.client = client;
        this.medic = medic;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }
    @Override
    public int compareTo(Appointment other){
        return this.appointmentDate.compareTo(other.appointmentDate);
    }
    // Getters and Setters
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
