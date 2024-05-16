package main.entities;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByPosition;
import main.enums.AppointmentFrequency;
import main.enums.AppointmentStatus;

public class RegularAppointment extends Appointment {

    @CsvBindByPosition(position = 8)
    private AppointmentFrequency frequency;

    // Constructor
    public RegularAppointment(String clientId, String medicId, LocalDateTime appointmentDate, AppointmentFrequency frequency) {
        super(clientId, medicId, appointmentDate);
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
