package main.entities;

import java.time.LocalDateTime;

import com.opencsv.bean.CsvBindByPosition;
import main.enums.AppointmentFrequency;
import main.enums.AppointmentStatus;

public class RegularAppointment extends Appointment {

    @CsvBindByPosition(position = 8)
    private AppointmentFrequency frequency;

    public RegularAppointment(String clientId, String medicId, LocalDateTime appointmentDate, AppointmentFrequency frequency) {
        super(clientId, medicId, appointmentDate);
        this.frequency = frequency;
    }
    public RegularAppointment(){
        super();
    }
    public AppointmentFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(AppointmentFrequency frequency) {
        this.frequency = frequency;
    }
    @Override
    public String toString() {
        return super.toString() +
                "Frequency: " + frequency + "\n";
    }
}
