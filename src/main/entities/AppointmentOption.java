package main.entities;

import java.time.LocalDateTime;

// this class is used to compute if there are any empty spaces
public class AppointmentOption {
    private final Medic medic;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public AppointmentOption(Medic medic, LocalDateTime startTime) {
        this.medic = medic;
        this.startTime = startTime;
        this.endTime = startTime.plusHours(1); // any appointment has maximum 1 hour
    }

    public Medic getMedic() {
        return medic;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "AppointmentOption{" +
                "medic=" + medic.getName() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
