package main.entities;

import com.opencsv.bean.CsvBindByPosition;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;
import main.storage.FileService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Client extends Person {
    @CsvBindByPosition(position = 7)
    private String appointmentIdsSerialized = "";
    @CsvBindByPosition(position = 8)
    private String medicalRecordIdsSerialized = "";
    private final List<MedicalRecord> medicalRecords = new ArrayList<>();
    private final List<OneTimeAppointment> appointments = new ArrayList<>();
    private final FileService fileService = FileService.getInstance();

    public Client( String name, String phone, String email,
                  String appointmentIdsSerialized, String medicalRecordIdsSerialized)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(name, phone, email);
        this.appointmentIdsSerialized = appointmentIdsSerialized;
        this.medicalRecordIdsSerialized = medicalRecordIdsSerialized;
        initializeAppointmentsAndRecords();
    }

    public Client(String name, String phone, String email)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(name, phone, email);
        // Appointments and MedicalRecords are initialized with empty lists.
    }

    public Client() {
        super();
        // Default constructor with initialized lists.
    }

    private void initializeAppointmentsAndRecords() {
        if (!appointmentIdsSerialized.isEmpty()) {
            setAppointments();
        }
        if (!medicalRecordIdsSerialized.isEmpty()) {
            setMedicalHistory();
        }
    }

    public List<String> getAppointmentIds() {
        return Arrays.asList(appointmentIdsSerialized.split(","));
    }

    public List<String> getMedicalRecordIds() {
        return Arrays.asList(medicalRecordIdsSerialized.split(","));
    }

    public void setAppointmentIds(List<String> appointmentIds) {
        this.appointmentIdsSerialized = String.join(",", appointmentIds);
    }

    public void setMedicalRecordIds(List<String> medicalRecordIds) {
        this.medicalRecordIdsSerialized = String.join(",", medicalRecordIds);
    }

    private void setAppointments() {
        this.appointments.clear();
        for (String id : getAppointmentIds()) {
            Optional<OneTimeAppointment> appointmentOpt = fileService.getOneTimeAppointmentManager().findById(id);
            appointmentOpt.ifPresent(appointments::add);
        }
    }

    private void setMedicalHistory() {
        this.medicalRecords.clear();
        for (String id : getMedicalRecordIds()) {
            Optional<MedicalRecord> recordOpt = fileService.getMedicalRecordManager().findById(id);
            recordOpt.ifPresent(medicalRecords::add);
        }
    }

    public List<MedicalRecord> getMedicalRecords() {
        return new ArrayList<>(medicalRecords);
    }

    public void addMedicalRecord(MedicalRecord medicalRecord) {
        if (!medicalRecordIdsSerialized.isEmpty()) {
            medicalRecordIdsSerialized += ",";
        }
        medicalRecordIdsSerialized += medicalRecord.getId();
        medicalRecords.add(medicalRecord);
    }

    public List<OneTimeAppointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public void addAppointment(OneTimeAppointment appointment) {
        if (!appointmentIdsSerialized.isEmpty()) {
            appointmentIdsSerialized += ",";
        }
        appointmentIdsSerialized += appointment.getId();
        appointments.add(appointment);
    }

    @Override
    public void displayDetails() {
        System.out.println("Client ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Appointments:");
        for (OneTimeAppointment appointment : appointments) {
            System.out.println("- " + appointment);
        }
        System.out.println("Medical Records:");
        for (MedicalRecord record : medicalRecords) {
            System.out.println("- " + record);
        }
    }
}
