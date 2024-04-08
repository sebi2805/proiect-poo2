package main.entities;

import com.opencsv.bean.CsvBindByPosition;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

import java.util.Arrays;
import java.util.List;

public class Client extends Person {
    @CsvBindByPosition(position = 7)
    private String appointmentIdsSerialized = "";
    @CsvBindByPosition(position = 8)
    private String medicalRecordIdsSerialized = "";

    public Client( String name, String phone, String email,
                  String appointmentIdsSerialized, String medicalRecordIdsSerialized)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(name, phone, email);
        this.appointmentIdsSerialized = appointmentIdsSerialized;
        this.medicalRecordIdsSerialized = medicalRecordIdsSerialized;
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


    public void addMedicalRecord(MedicalRecord medicalRecord) {
        if (!medicalRecordIdsSerialized.isEmpty()) {
            medicalRecordIdsSerialized += ",";
        }
        medicalRecordIdsSerialized += medicalRecord.getId();
    }
    public void addAppointment(OneTimeAppointment appointment) {
        if (!appointmentIdsSerialized.isEmpty()) {
            appointmentIdsSerialized += ",";
        }
        appointmentIdsSerialized += appointment.getId();
    }

    @Override
    public void displayDetails() {
        System.out.println("Client ID: " + getId());
        System.out.println("Name: " + getName());
        System.out.println("Phone: " + getPhone());
        System.out.println("Email: " + getEmail());
//        System.out.println("Appointments:");
//        for (OneTimeAppointment appointment : appointments) {
//            System.out.println("- " + appointment);
//        }
//        System.out.println("Medical Records:");
//        for (MedicalRecord record : medicalRecords) {
//            System.out.println("- " + record);
//        }
    }
}
