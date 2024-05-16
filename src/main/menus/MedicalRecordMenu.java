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
import java.time.format.DateTimeParseException;
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
        System.out.println("\nMedical Record Menu:");
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
            String clientId = getUserChoice(clientService.getOptions(), "Select Client:");
            String medicId = getUserChoice(medicService.getOptions(), "Select Medic:");

            System.out.print("Enter Visit Date (YYYY-MM-DD): ");
            LocalDate visitDate = LocalDate.parse(scanner.nextLine());
            System.out.print("Enter Notes: ");
            String notes = scanner.nextLine();

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
    private LocalDate parseDate(String date) throws DateTimeParseException {
        return LocalDate.parse(date);
    }
    @Override
    protected void update() {
        MedicalRecord record = chooseMedicalRecord();
        if (record == null) {
            return;
        }

        System.out.println("Current details: " + getDetailedMedicalRecord(record));

        try {
            String newClientId = getUserChoice(clientService.getOptions(), "Select new Client ID (leave blank to keep current):");
            if (newClientId != null) {
                record.setClientId(newClientId);
            }

            String newMedicId = getUserChoice(medicService.getOptions(), "Select new Medic ID (leave blank to keep current):");
            if (newMedicId != null) {
                record.setMedicId(newMedicId);
            }

            System.out.print("Enter new Visit Date (leave blank to keep current): ");
            String visitDateInput = scanner.nextLine();
            if (!visitDateInput.isEmpty()) {
                record.setVisitDate(parseDate(visitDateInput));
            }

            System.out.print("Enter new Notes (leave blank to keep current): ");
            String notes = scanner.nextLine();
            if (!notes.isEmpty()) record.setNotes(notes);

            service.update(record);
            System.out.println("Medical Record updated successfully.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Please enter the date in YYYY-MM-DD format.");
        } catch (NotFoundException e) {
            System.out.println("Error: Medical Record not found.");
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
        }

        waitForUserInput();
    }

    @Override
    protected void delete() {
        MedicalRecord record = chooseMedicalRecord();
        if (record == null) {
            return;
        }

        try {
            service.delete(record.getId());
            System.out.println("Medical Record deleted successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: Medical Record not found.");
        }

        waitForUserInput();
    }


    private String getDetailedMedicalRecord(MedicalRecord record) {
        StringBuilder sb = new StringBuilder();
        sb.append("Medical Record ID: ").append(record.getId()).append("\n");

        Optional<Client> client = clientService.getById(record.getClientId());
        if (client.isPresent()) {
            sb.append("Client: ").append(client.get().getName()).append(" (ID: ").append(client.get().getId()).append(")\n");
        } else {
            sb.append("Client ID: ").append(record.getClientId()).append(" (Details not found)\n");
        }

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
    private MedicalRecord chooseMedicalRecord() {
        List<MedicalRecord> records = service.getAll();
        if (records.isEmpty()) {
            System.out.println("No medical records available.");
            waitForUserInput();
            return null;
        }

        for (int i = 0; i < records.size(); i++) {
            MedicalRecord record = records.get(i);
            System.out.printf("%d. %s | %s | %s\n",
                    i + 1,
                    record.getVisitDate(),
                    clientService.getById(record.getClientId()).map(Client::getName).orElse("Unknown Client"),
                    medicService.getById(record.getMedicId()).map(Medic::getName).orElse("Unknown Medic"));
        }

        int recordIndex = getUserIndexInput(records.size(), "Select a medical record (enter the number): ");
        if (recordIndex == -1) {
            return null;
        }

        return records.get(recordIndex);
    }
}
