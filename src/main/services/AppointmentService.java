package main.services;

import main.entities.Appointment;
import main.entities.MedicalRecord;
import main.entities.OneTimeAppointment;
import main.entities.RegularAppointment;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.storage.FileService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AppointmentService extends BaseService<OneTimeAppointment> {

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
    public void add(OneTimeAppointment appointment) throws AlreadyExistsException {
            if (fileService.getOneTimeAppointmentManager().findById(appointment.getId()).isPresent()) {
                throw new AlreadyExistsException("One-time appointment already exists.");
            }
            fileService.getOneTimeAppointmentManager().add((OneTimeAppointment) appointment);
    }

    @Override
    public Optional<OneTimeAppointment> getById(String appointmentId) {
        Optional<OneTimeAppointment> appointment = fileService.getOneTimeAppointmentManager().findById(appointmentId);

        return appointment;
    }

    @Override
    public void update(OneTimeAppointment appointment) throws NotFoundException {
        if (!fileService.getOneTimeAppointmentManager().findById(appointment.getId()).isPresent()) {
            throw new NotFoundException("One-time appointment not found.");
        }
        fileService.getOneTimeAppointmentManager().update((OneTimeAppointment) appointment);
    }

    @Override
    public void delete(String appointmentId) throws NotFoundException {
        try {
            fileService.getOneTimeAppointmentManager().delete(appointmentId);
        } catch (NotFoundException e) {
            System.out.println("The appointment was not found.");
        }
    }

    @Override
    public List<OneTimeAppointment> getAll() {
        List<OneTimeAppointment> oneTimeAppointments = fileService.getOneTimeAppointmentManager().findAll();
        return oneTimeAppointments;
    }

    public List<OneTimeAppointment> getAppointmentsByClientId(String clientId) {
        List<OneTimeAppointment> oneTimeAppointments = fileService.getOneTimeAppointmentManager().findAll().stream()
                .filter(appointment -> appointment.getClientId().equals(clientId))
                .collect(Collectors.toList());
        return oneTimeAppointments;
    }

    public List<OneTimeAppointment> getAppointmentsByMedicId(String medicId) {
        List<OneTimeAppointment> oneTimeAppointments = fileService.getOneTimeAppointmentManager().findAll().stream()
                .filter(appointment -> appointment.getMedicId().equals(medicId))
                .collect(Collectors.toList());

        return oneTimeAppointments;
    }
}
