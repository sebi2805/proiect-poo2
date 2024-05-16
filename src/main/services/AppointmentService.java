package main.services;

import main.entities.Appointment;
import main.entities.OneTimeAppointment;
import main.entities.RegularAppointment;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.storage.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentService extends BaseService<Appointment> {

    private static AppointmentService instance;
    private final FileService fileService;

    private AppointmentService() {
        super();
        this.fileService = FileService.getInstance();
    }

    public synchronized static AppointmentService getInstance() {
        if (instance == null) {
            instance = new AppointmentService();
        }
        return instance;
    }

    @Override
    public void add(Appointment appointment) throws AlreadyExistsException {
        if (appointment instanceof OneTimeAppointment) {
            if (fileService.getOneTimeAppointmentManager().findById(appointment.getId()).isPresent()) {
                throw new AlreadyExistsException("One-time appointment already exists.");
            }
            fileService.getOneTimeAppointmentManager().add((OneTimeAppointment) appointment);
        } else if (appointment instanceof RegularAppointment) {
            if (fileService.getRegularAppointmentManager().findById(appointment.getId()).isPresent()) {
                throw new AlreadyExistsException("Regular appointment already exists.");
            }
            fileService.getRegularAppointmentManager().add((RegularAppointment) appointment);
        }
    }

    @Override
    public Optional<Appointment> getById(String appointmentId) {
        Optional<Appointment> appointment = castToAppointment(fileService.getOneTimeAppointmentManager().findById(appointmentId));

        if (appointment.isEmpty()) {
            appointment = castToAppointment(fileService.getRegularAppointmentManager().findById(appointmentId));
        }

        return appointment;
    }

    private Optional<Appointment> castToAppointment(Optional<? extends Appointment> opt) {
        return opt.map(appointment -> (Appointment) appointment);
    }

    @Override
    public void update(Appointment appointment) throws NotFoundException {
        if (appointment instanceof OneTimeAppointment) {
            if (!fileService.getOneTimeAppointmentManager().findById(appointment.getId()).isPresent()) {
                throw new NotFoundException("One-time appointment not found.");
            }
            fileService.getOneTimeAppointmentManager().update((OneTimeAppointment) appointment);
        } else if (appointment instanceof RegularAppointment) {
            if (!fileService.getRegularAppointmentManager().findById(appointment.getId()).isPresent()) {
                throw new NotFoundException("Regular appointment not found.");
            }
            fileService.getRegularAppointmentManager().update((RegularAppointment) appointment);
        }
    }

    @Override
    public void delete(String appointmentId) throws NotFoundException {
        try {
            fileService.getOneTimeAppointmentManager().delete(appointmentId);
        } catch (NotFoundException e) {
            fileService.getRegularAppointmentManager().delete(appointmentId);
        }
    }

    @Override
    public List<Appointment> getAll() {
        List<? extends Appointment> oneTimeAppointments = fileService.getOneTimeAppointmentManager().findAll();
        List<? extends Appointment> regularAppointments = fileService.getRegularAppointmentManager().findAll();
        List<Appointment> allAppointments = new ArrayList<>();
        allAppointments.addAll(oneTimeAppointments);
        allAppointments.addAll(regularAppointments);
        return allAppointments;
    }

    public List<Appointment> getAppointmentsForClient(String clientId) {
        List<Appointment> oneTimeAppointments = fileService.getOneTimeAppointmentManager().findAll().stream()
                .filter(appointment -> appointment.getClient().getId().equals(clientId))
                .collect(Collectors.toList());
        List<Appointment> regularAppointments = fileService.getRegularAppointmentManager().findAll().stream()
                .filter(appointment -> appointment.getClient().getId().equals(clientId))
                .collect(Collectors.toList());
        oneTimeAppointments.addAll(regularAppointments);
        return oneTimeAppointments;
    }

    public List<Appointment> getAppointmentsForMedic(String medicId) {
        List<Appointment> oneTimeAppointments = fileService.getOneTimeAppointmentManager().findAll().stream()
                .filter(appointment -> appointment.getMedic().getId().equals(medicId))
                .collect(Collectors.toList());
        List<Appointment> regularAppointments = fileService.getRegularAppointmentManager().findAll().stream()
                .filter(appointment -> appointment.getMedic().getId().equals(medicId))
                .collect(Collectors.toList());
        oneTimeAppointments.addAll(regularAppointments);
        return oneTimeAppointments;
    }
}
