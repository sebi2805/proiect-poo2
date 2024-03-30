package main.entities;

import main.exceptions.FutureDateException;

import java.time.LocalDate;

public class MedicalRecord extends BaseEntity {
    private String clientID;
    private LocalDate visitDate;
    private String notes;

    // Constructor
    public MedicalRecord(String ID, String clientID, LocalDate visitDate, String notes)
            throws FutureDateException {
        super(ID);
        this.clientID = clientID;
        setVisitDate(visitDate);
        this.notes = notes;
    }

    // Getters and Setters
    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) throws FutureDateException {
        if(visitDate.isAfter(LocalDate.now())) {
            throw new FutureDateException("The visit date cannot be in the future.");
        }
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
        System.out.println("Medical Record ID: " + getId());
        System.out.println("Client ID: " + clientID);
        System.out.println("Visit Date: " + visitDate);
        System.out.println("Notes: " + notes);
    }
}
