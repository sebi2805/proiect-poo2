package main.menus;

import main.entities.MedicalRecord;
import main.entities.Client;
import main.entities.Medic;
import main.exceptions.AlreadyExistsException;
import main.exceptions.FutureDateException;
import main.exceptions.NotFoundException;
import main.services.MedicalRecordService;
import main.services.ClientService;
import main.services.MedicService;
import main.util.Option;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MedicalRecordMenu extends EntityMenu<MedicalRecord> {
    private final ClientService clientService;
    private final MedicService medicService;

    public MedicalRecordMenu(MedicalRecordService service, ClientService clientService, MedicService medicService) {
        super(service);
        this.clientService = clientService;
        this.medicService = medicService;
    }

    @Override
    protected void printMenuOptions() {
        System.out.println("Medical Record Menu:");
        System.out.println("1. Display all Medical Records");
        System.out.println("2. Search Medical Record by ID");
        System.out.println("3. Add Medical Record");
        System.out.println("4. Update Medical Record");
        System.out.println("5. Delete Medical Record");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    @Override
    protected void displayAll() {
        List<MedicalRecord> records = service.getAll();
        for (MedicalRecord record : records) {
            System.out.println(getDetailedMedicalRecord(record));
        }
        waitForUserInput();
    }

    @Override
    protected void searchById() {
        System.out.print("Enter Medical Record ID: ");
        String id = scanner.nextLine();
        Optional<MedicalRecord> record = service.getById(id);
        record.ifPresentOrElse(
                r -> System.out.println(getDetailedMedicalRecord(r)),
                () -> System.out.println("Medical Record not found.")
        );
        waitForUserInput();
    }

    @Override
    protected void add() {
        try {
            // Display client options
            List<Option> clientOptions = clientService.getOptions();
            System.out.println("Select Client:");
            for (int i = 0; i < clientOptions.size(); i++) {
                System.out.println((i + 1) + ". " + clientOptions.get(i).getLabel());
            }
            System.out.print("Enter your choice: ");
            int clientChoice = Integer.parseInt(scanner.nextLine());
            String clientId = clientOptions.get(clientChoice - 1).getValue();

            // Display medic options
            List<Option> medicOptions = medicService.getOptions();
            System.out.println("Select Medic:");
            for (int i = 0; i < medicOptions.size(); i++) {
                System.out.println((i + 1) + ". " + medicOptions.get(i).getLabel());
            }
            System.out.print("Enter your choice: ");
            int medicChoice = Integer.parseInt(scanner.nextLine());
            String medicId = medicOptions.get(medicChoice - 1).getValue();

            // Input visit date and notes
            System.out.print("Enter Visit Date (YYYY-MM-DD): ");
            LocalDate visitDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter Notes: ");
            String notes = scanner.nextLine();

            // Create and add medical record
            MedicalRecord record = new MedicalRecord(clientId, medicId, visitDate, notes);
            service.add(record);
            System.out.println("Medical Record added successfully.");
        } catch (FutureDateException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (AlreadyExistsException e) {
            System.out.println("Error: Medical Record already exists.");
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
        } finally {
            waitForUserInput();
        }
    }

    @Override
    protected void update() {
        System.out.print("Enter Medical Record ID: ");
        String id = scanner.nextLine();
        Optional<MedicalRecord> optionalRecord = service.getById(id);

        if (optionalRecord.isPresent()) {
            MedicalRecord record = optionalRecord.get();
            System.out.println("Current details: " + getDetailedMedicalRecord(record));

            System.out.print("Enter new Client ID (leave blank to keep current): ");
            String clientId = scanner.nextLine();
            if (!clientId.isEmpty()) record.setClientId(clientId);

            System.out.print("Enter new Medic ID (leave blank to keep current): ");
            String medicId = scanner.nextLine();
            if (!medicId.isEmpty()) record.setMedicId(medicId);

            System.out.print("Enter new Visit Date (leave blank to keep current): ");
            String visitDateInput = scanner.nextLine();
            if (!visitDateInput.isEmpty()) {
                try {
                    record.setVisitDate(LocalDate.parse(visitDateInput));
                } catch (FutureDateException e) {
                    System.out.println("Error: " + e.getMessage());
                    return;
                }
            }

            System.out.print("Enter new Notes (leave blank to keep current): ");
            String notes = scanner.nextLine();
            if (!notes.isEmpty()) record.setNotes(notes);

            try {
                service.update(record);
                System.out.println("Medical Record updated successfully.");
            } catch (NotFoundException e) {
                System.out.println("Error: Medical Record not found.");
            }
        } else {
            System.out.println("Medical Record not found.");
        }
        waitForUserInput();
    }

    @Override
    protected void delete() {
        System.out.print("Enter Medical Record ID: ");
        String id = scanner.nextLine();
        try {
            service.delete(id);
            System.out.println("Medical Record deleted successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: Medical Record not found.");
        }
        waitForUserInput();
    }

    private String getDetailedMedicalRecord(MedicalRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append("Medical Record ID: ").append(record.getId()).append("\n");

        // Fetching client details
        Optional<Client> client = clientService.getById(record.getClientId());
        if (client.isPresent()) {
            sb.append("Client: ").append(client.get().getName()).append(" (ID: ").append(client.get().getId()).append(")\n");
        } else {
            sb.append("Client ID: ").append(record.getClientId()).append(" (Details not found)\n");
        }

        // Fetching medic details
        Optional<Medic> medic = medicService.getById(record.getMedicId());
        if (medic.isPresent()) {
            sb.append("Medic: ").append(medic.get().getName()).append(" (ID: ").append(medic.get().getId()).append(")\n");
        } else {
            sb.append("Medic ID: ").append(record.getMedicId()).append(" (Details not found)\n");
        }

        sb.append("Visit Date: ").append(record.getVisitDate()).append("\n");
        sb.append("Notes: ").append(record.getNotes());
        return sb.toString();
    }

    private void waitForUserInput() {
        System.out.println("Press Enter to continue...");
        scanner.nextLine();
    }
}
