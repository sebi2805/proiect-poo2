package main.entities;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import main.enums.AppointmentStatus; // Import the enum if it's in a different package
import main.util.LocalDateTimeConverter;

public abstract class Appointment extends BaseEntity implements Comparable<Appointment> {
    @CsvBindByPosition(position = 4)
    private String clientId;
    @CsvBindByPosition(position = 5)
    private String medicId;
    @CsvCustomBindByPosition(position = 6, converter = LocalDateTimeConverter.class)
    protected LocalDateTime appointmentDate;

    @CsvBindByPosition(position = 7)
    protected AppointmentStatus status;

    // Constructor
    public Appointment(String clientId, String medicId, LocalDateTime appointmentDate, AppointmentStatus status) {
        super();
        this.clientId = clientId;
        this.medicId = medicId;
        this.appointmentDate = appointmentDate;
        this.status = status;
    }
    public Appointment(){
        super();
    }
    @Override
    public int compareTo(Appointment other) {
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

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getMedicId() {
        return medicId;
    }

    public void setMedicId(String medicId) {
        this.medicId = medicId;
    }

    // Abstract methods
    public abstract void confirmAppointment();
    public abstract void cancelAppointment();

    // toString method
    @Override
    public String toString() {
        return "Appointment ID: " + getId() + "\n" +
                "Client ID: " + clientId + "\n" +
                "Medic ID: " + medicId + "\n" +
                "Appointment Date: " + appointmentDate + "\n" +
                "Status: " + status;
    }
}
