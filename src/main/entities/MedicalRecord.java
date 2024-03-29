package main.entities;

import java.time.LocalDate;

public class MedicalRecord {
    private String recordID;
    private String clientID;
    private LocalDate visitDate;
    private String notes;

    // Constructor
    public MedicalRecord(String recordID, String clientID, LocalDate visitDate, String notes) {
        this.recordID = recordID;
        this.clientID = clientID;
        this.visitDate = visitDate;
        this.notes = notes;
    }

    // Getters and Setters
    public String getRecordID() {
        return recordID;
    }

    public void setRecordID(String recordID) {
        this.recordID = recordID;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) {
        this.visitDate = visitDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // Method to display the details of the medical record
    public void displayDetails() {
        System.out.println("Medical Record ID: " + recordID);
        System.out.println("Client ID: " + clientID);
        System.out.println("Visit Date: " + visitDate);
        System.out.println("Notes: " + notes);
    }
}
