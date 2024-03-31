package main.services;

import main.entities.Appointment;

import java.util.List;

public class AppointmentService {

    // Constructor
    public AppointmentService() {
        // Initializarea necesară
    }

    // Programarea unei noi întâlniri
    public Appointment scheduleAppointment(AppointmentDetails details) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Actualizarea unei întâlniri existente
    public Appointment updateAppointment(String appointmentId, AppointmentUpdateDetails updateDetails) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Anularea unei întâlniri
    public void cancelAppointment(String appointmentId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Găsirea tuturor programărilor pentru un client
    public List<Appointment> getAppointmentsForClient(String clientId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Găsirea tuturor programărilor pentru un medic
    public List<Appointment> getAppointmentsForMedic(String medicId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Găsirea unei întâlniri după ID
    public Appointment getAppointmentById(String appointmentId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    // Metoda confirmAppointment este omisă intenționat pe baza solicitării tale
}
