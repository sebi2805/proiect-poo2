package main.entities;

import com.opencsv.bean.CsvBindByPosition;
import main.enums.AppointmentStatus;
import java.time.LocalDateTime;

public class OneTimeAppointment extends Appointment {

    @CsvBindByPosition(position = 7)
    protected AppointmentStatus status;
    public OneTimeAppointment(String clientId, String medicId, LocalDateTime appointmentDate, AppointmentStatus status) {
        super(clientId, medicId, appointmentDate);
        this.status = status;

    }
    public OneTimeAppointment() {
        super();
    }
    public void confirmAppointment() {
        System.out.println("One-Time Appointment confirmed for " + appointmentDate);
        this.status = AppointmentStatus.CONFIRMED;
    }

    public void cancelAppointment() {
        System.out.println("One-Time Appointment on " + appointmentDate + " cancelled.");
        this.status = AppointmentStatus.CANCELED;
    }
    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }
    @Override
    public String toString() {
        return super.toString() +
                "Status: " + status + "\n";
    }
}
