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
    private List<MedicalRecord> medicalRecords;;
    private List<OneTimeAppointment> appointments;
    @CsvBindByPosition(position = 6)
    private String appointmentIdsSerialized = "";
    @CsvBindByPosition(position = 7)
    private String medicalRecordIdsSerialized = "";
    private final FileService fileService = main.storage.FileService.getInstance();

    // Constructor
    public Client(String ID, String name, String phone, String email,
                  String appointmentIdsSerialized, String medicalRecordIdsSerialized)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(ID, name, phone, email);
        this.appointmentIdsSerialized = appointmentIdsSerialized;
        this.medicalRecordIdsSerialized = medicalRecordIdsSerialized;
    }
    public Client(String ID, String name, String phone, String email)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(ID, name, phone, email);
    }
    public void setAppointments() {
        this.appointments = new ArrayList<>();
        for (String id : getAppointmentIds()) {
            Optional<OneTimeAppointment> appointmentOpt = fileService.getOneTimeAppointmentManager().findById(id);
            appointmentOpt.ifPresent(appointments::add);
        }
    }
    public List<String> getAppointmentIds() {
        return Arrays.asList(appointmentIdsSerialized.split(","));
    }
    public List<String> getMedicalRecordIds() {
            return  Arrays.asList(medicalRecordIdsSerialized.split(","));
    }

    public void setMedicalRecordIds(List<String> medicalRecordIds) {
        this.medicalRecordIdsSerialized = String.join(",", medicalRecordIds);
    }
    public void setAppointmentIds(List<String> appointmentIds) {
        this.appointmentIdsSerialized = String.join(",", appointmentIds);
    }
    public void setMedicalHistory() {
        this.medicalRecords = new ArrayList<>();
        for (String id : getMedicalRecordIds()) {
            Optional<OneTimeAppointment> appointmentOpt = fileService.getOneTimeAppointmentManager().findById(id);
            appointmentOpt.ifPresent(appointments::add);
        }
    }
    public Client() {
        super();
    }

    // Getters and Setters
    public List<MedicalRecord> getMedicalRecords() {
        if(medicalRecords.isEmpty() && !medicalRecordIdsSerialized.isEmpty())
            setMedicalHistory();
        return new ArrayList<>(medicalRecords);
    }
    public void addMedicalRecord(MedicalRecord medicalRecord) {
        if(medicalRecords == null){
            medicalRecords = new ArrayList<>();
        }
        if (!medicalRecordIdsSerialized.isEmpty()) {
            medicalRecordIdsSerialized += ",";
        }
        medicalRecordIdsSerialized += medicalRecord.getId();
        medicalRecords.add(medicalRecord);

    }

    // Method to add an appointment to the list
    public void addAppointment(OneTimeAppointment appointment) {
        if (appointments == null) {
            appointments = new ArrayList<>();
        }
        if (!appointmentIdsSerialized.isEmpty()) {
            appointmentIdsSerialized += ",";
        }
        appointmentIdsSerialized += appointment.getId();
        appointments.add(appointment);
    }

    // Method to get all appointments
    public List<OneTimeAppointment> getAppointments() {
        if(appointments.isEmpty() && !appointmentIdsSerialized.isEmpty())
            setAppointments();
        return appointments;
    }

    // Implementing the abstract method from Person class
    @Override
    public void displayDetails() {
        System.out.println("Client ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
        System.out.println("Medical History: ");
        for (OneTimeAppointment entry : appointments) {
            System.out.println("- " + entry);
        }
        for (MedicalRecord entry : medicalRecords) {
            System.out.println("- " + entry);
        }
    }
}
