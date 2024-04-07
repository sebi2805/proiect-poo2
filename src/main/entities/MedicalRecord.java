package main.entities;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;
import main.exceptions.FutureDateException;
import main.exceptions.NotFoundException;
import main.storage.FileService; // Assuming FileService can fetch Medic and Client instances

import java.time.LocalDate;

public class MedicalRecord extends BaseEntity {
    @CsvBindByPosition(position = 4)
    private String clientId;
    @CsvBindByPosition(position = 5)
    private String medicId;
    @CsvBindByPosition(position = 6)
    @CsvDate("yyyy-MM-dd")
    private LocalDate visitDate;
    @CsvBindByPosition(position = 7)
    private String notes;
//    private Medic medic;
//    private Client client;

    private final transient FileService fileService = FileService.getInstance(); // Handling lazy loading

    // Constructor
    public MedicalRecord( String clientId, String medicId, LocalDate visitDate, String notes)
            throws FutureDateException {
        super();
        this.clientId = clientId;
        this.medicId = medicId;
        setVisitDate(visitDate);
        this.notes = notes;
    }

    public MedicalRecord() {
        super();
        System.out.println("creating");
    }

    // Getters and Setters with lazy loading for Medic and Client
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
//        this.client = null; // Reset client object to enforce lazy loading next time it's accessed
    }

//    public Client getClient() throws NotFoundException {
//        if (client == null && clientId != null) {
//            client = fileService.getClientManager()
//                    .findById(clientId)
//                    .orElseThrow(() -> new NotFoundException("Client not found"));
//        }
//        return client;
//    }

    public String getMedicId() {
        return medicId;
    }

    public void setMedicId(String medicId) {
        this.medicId = medicId;
//        this.medic = null; // Reset medic object to enforce lazy loading next time it's accessed
    }

//    public Medic getMedic() throws NotFoundException {
//        if (medic == null && medicId != null) {
//            medic = fileService.getMedicManager()
//                    .findById(medicId)
//                    .orElseThrow(() ->  new NotFoundException("Medic not found"));
//        }
//        return medic;
//    }

    public LocalDate getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(LocalDate visitDate) throws FutureDateException {
        if (visitDate.isAfter(LocalDate.now())) {
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
        System.out.println("Client ID: " + clientId);
        System.out.println("Medic ID: " + medicId);
        System.out.println("Visit Date: " + visitDate);
        System.out.println("Notes: " + notes);
    }
}
