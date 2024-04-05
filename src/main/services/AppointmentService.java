package main.services;

import main.entities.Appointment;
import main.entities.OneTimeAppointment;
import main.entities.RegularAppointment;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.storage.FileService;

import java.util.List;
import java.util.stream.Collectors;

public class AppointmentService {

    private static AppointmentService instance;
    private final FileService fileService;

    // Private constructor to prevent instantiation
    private AppointmentService() {
        this.fileService = FileService.getInstance();
    }

    // Public method to get the singleton instance
    public static synchronized AppointmentService getInstance() {
        if (instance == null) {
            instance = new AppointmentService();
        }
        return instance;
    }

    // Programarea unei noi întâlniri
    public Appointment scheduleAppointment(Appointment appointment) throws AlreadyExistsException {
        if (appointment instanceof OneTimeAppointment) {
            fileService.getOneTimeAppointmentManager().add((OneTimeAppointment) appointment);
        } else if (appointment instanceof RegularAppointment) {
            fileService.getRegularAppointmentManager().add((RegularAppointment) appointment);
        }
        return appointment;
    }

    // Actualizarea unei întâlniri existente
    public Appointment updateAppointment(Appointment updatedAppointment) throws NotFoundException {
        if (updatedAppointment instanceof OneTimeAppointment) {
            fileService.getOneTimeAppointmentManager().update((OneTimeAppointment) updatedAppointment);
        } else if (updatedAppointment instanceof RegularAppointment) {
            fileService.getRegularAppointmentManager().update((RegularAppointment) updatedAppointment);
        }
        return updatedAppointment;
    }

    // Anularea unei întâlniri
    public void cancelAppointment(String appointmentId) throws NotFoundException {
        // Nota: Necesită modificare pentru a suporta ambele tipuri de întâlniri
        try {
            fileService.getOneTimeAppointmentManager().delete(appointmentId);
        } catch (NotFoundException e) {
            fileService.getRegularAppointmentManager().delete(appointmentId);
        }
    }

    // Găsirea tuturor programărilor pentru un client
    public List<Appointment> getAppointmentsForClient(String clientId) {
        List<Appointment> oneTimeAppointments = fileService.getOneTimeAppointmentManager().findAll().stream().filter(appointment -> appointment.getClient().getId().equals(clientId)).collect(Collectors.toList());
        List<Appointment> regularAppointments = fileService.getRegularAppointmentManager().findAll().stream().filter(appointment -> appointment.getClient().getId().equals(clientId)).collect(Collectors.toList());
        oneTimeAppointments.addAll(regularAppointments);
        return oneTimeAppointments;
    }

    // Găsirea tuturor programărilor pentru un medic
    public List<Appointment> getAppointmentsForMedic(String medicId) {
        List<Appointment> oneTimeAppointments = fileService.getOneTimeAppointmentManager().findAll().stream().filter(appointment -> appointment.getMedic().getId().equals(medicId)).collect(Collectors.toList());
        List<Appointment> regularAppointments = fileService.getRegularAppointmentManager().findAll().stream().filter(appointment -> appointment.getMedic().getId().equals(medicId)).collect(Collectors.toList());
        oneTimeAppointments.addAll(regularAppointments);
        return oneTimeAppointments;
    }

    // Găsirea unei întâlniri după ID
    public Appointment getAppointmentById(String appointmentId) throws NotFoundException {
        Appointment appointment;
        try {
            appointment = fileService.getOneTimeAppointmentManager().findById(appointmentId).orElseThrow(() -> new NotFoundException("Appointment with ID " + appointmentId + " not found."));
        } catch (NotFoundException e) {
            appointment = fileService.getRegularAppointmentManager().findById(appointmentId).orElseThrow(() -> new NotFoundException("Appointment with ID " + appointmentId + " not found."));
        }
        return appointment;
    }
}
