package main.entities;

import java.time.LocalDateTime;

public class AppointmentOption {
    private final Medic medic;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public AppointmentOption(Medic medic, LocalDateTime startTime) {
        this.medic = medic;
        this.startTime = startTime;
        this.endTime = startTime.plusHours(1); // O programare durează maxim o oră
    }

    // Getteri
    public Medic getMedic() {
        return medic;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    // toString() pentru a facilita afișarea opțiunilor
    @Override
    public String toString() {
        return "AppointmentOption{" +
                "medic=" + medic.getName() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
