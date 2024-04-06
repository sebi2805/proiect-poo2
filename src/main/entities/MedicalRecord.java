package main.entities;

import com.opencsv.bean.CsvBindByPosition;
import main.exceptions.FutureDateException;

import java.time.LocalDate;

public class MedicalRecord extends BaseEntity {
    @CsvBindByPosition(position = 4)
    private String clientID;
    @CsvBindByPosition(position = 5)
    private LocalDate visitDate;
    @CsvBindByPosition(position = 6)
    private String notes;

    // Constructor
    public MedicalRecord(String ID, String clientID, LocalDate visitDate, String notes)
            throws FutureDateException {
        super(ID);
        this.clientID = clientID;
        setVisitDate(visitDate);
        this.notes = notes;
    }
    public MedicalRecord(){
        super();
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
