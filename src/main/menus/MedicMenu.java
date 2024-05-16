package main.menus;

import main.entities.Medic;
import main.enums.MedicalSpecialty;
import main.exceptions.AlreadyExistsException;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;
import main.exceptions.NotFoundException;
import main.services.MedicService;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MedicMenu extends EntityMenu<Medic> {

    public MedicMenu(MedicService service) {
        super(service);
    }

    @Override
    protected void printMenuOptions() {
        System.out.println("Medic Menu:");
        System.out.println("1. Display all Medics");
        System.out.println("2. Search Medic by ID");
        System.out.println("3. Add Medic");
        System.out.println("4. Update Medic");
        System.out.println("5. Delete Medic");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    @Override
    protected void displayAll() {
        List<Medic> medics = service.getAll();
        if(medics.isEmpty())
            System.out.println("No clients available.");
        for (Medic medic : medics) {
            System.out.println(medic);
            System.out.println("\n");
        }
    }

    @Override
    protected void searchById() {
        System.out.print("Enter Medic ID: ");
        String id = scanner.nextLine();
        Optional<Medic> medic = service.getById(id);
        medic.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Medic not found.")
        );
    }

    @Override
    protected void add() {
        System.out.print("Enter Medic Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Medic Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter Medic Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Medic Specialty (GENERAL_PRACTITIONER or SURGEON): ");
        MedicalSpecialty specialty;
        try {
            specialty = MedicalSpecialty.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid specialty. Medic not added.");
            return;
        }
        System.out.print("Enter Working Hours Start (HH:MM): ");
        LocalTime workingHoursStart = LocalTime.parse(scanner.nextLine());
        System.out.print("Enter Working Hours End (HH:MM): ");
        LocalTime workingHoursEnd = LocalTime.parse(scanner.nextLine());

        try {
            Medic medic = new Medic(name, phone, email, specialty, workingHoursStart, workingHoursEnd);
            service.add(medic);
            System.out.println("Medic added successfully.");
        } catch (InvalidPhoneNumberException | InvalidEmailFormatException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (AlreadyExistsException e) {
            System.out.println("Error: Medic already exists.");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    protected void update() {
        List<Medic> medics = service.getAll();
        if (medics.isEmpty()) {
            System.out.println("No medics available to update.");
            waitForUserInput();
            return;
        }

        // Display all medics
        for (int i = 0; i < medics.size(); i++) {
            System.out.printf("%d. %s : %s\n", i + 1, medics.get(i).getName(), medics.get(i).getEmail());
        }

        // Let the user select a medic by their index
        System.out.print("Select a medic to update (enter the number): ");
        int medicIndex;
        try {
            medicIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (medicIndex < 0 || medicIndex >= medics.size()) {
                System.out.println("Invalid selection. Please try again.");
                waitForUserInput();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            waitForUserInput();
            return;
        }

        Medic medic = medics.get(medicIndex);
        System.out.println("Current details: " + medic);

        System.out.print("Enter new Medic Name (leave blank to keep current): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            medic.setName(name);
        }

        System.out.print("Enter new Medic Phone (leave blank to keep current): ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            try {
                medic.setPhone(phone);
            } catch (InvalidPhoneNumberException e) {
                System.out.println("Error: " + e.getMessage());
                waitForUserInput();
                return;
            }
        }

        System.out.print("Enter new Medic Email (leave blank to keep current): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            try {
                medic.setEmail(email);
            } catch (InvalidEmailFormatException e) {
                System.out.println("Error: " + e.getMessage());
                waitForUserInput();
                return;
            }
        }

        System.out.print("Enter new Medic Specialty (leave blank to keep current): ");
        String specialtyInput = scanner.nextLine();
        if (!specialtyInput.isEmpty()) {
            try {
                MedicalSpecialty specialty = MedicalSpecialty.valueOf(specialtyInput.toUpperCase());
                medic.setSpecialty(specialty);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid specialty. Medic not updated.");
                waitForUserInput();
                return;
            }
        }

        System.out.print("Enter new Working Hours Start (leave blank to keep current): ");
        String workingHoursStartInput = scanner.nextLine();
        if (!workingHoursStartInput.isEmpty()) {
            LocalTime workingHoursStart = LocalTime.parse(workingHoursStartInput);
            medic.setWorkingHoursStart(workingHoursStart);
        }

        System.out.print("Enter new Working Hours End (leave blank to keep current): ");
        String workingHoursEndInput = scanner.nextLine();
        if (!workingHoursEndInput.isEmpty()) {
            LocalTime workingHoursEnd = LocalTime.parse(workingHoursEndInput);
            medic.setWorkingHoursEnd(workingHoursEnd);
        }

        try {
            service.update(medic);
            System.out.println("Medic updated successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: Medic not found.");
        }

        waitForUserInput();
    }
    @Override
    protected void delete() {
        List<Medic> medics = service.getAll();
        if (medics.isEmpty()) {
            System.out.println("No medics available to delete.");
            waitForUserInput();
            return;
        }

        // Display all medics
        for (int i = 0; i < medics.size(); i++) {
            System.out.printf("%d. %s : %s\n", i + 1, medics.get(i).getName(), medics.get(i).getEmail());
        }

        // Let the user select a medic by their index
        System.out.print("Select a medic to delete (enter the number): ");
        int medicIndex;
        try {
            medicIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (medicIndex < 0 || medicIndex >= medics.size()) {
                System.out.println("Invalid selection. Please try again.");
                waitForUserInput();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            waitForUserInput();
            return;
        }

        Medic medic = medics.get(medicIndex);
        try {
            service.delete(medic.getId());
            System.out.println("Medic deleted successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: Medic not found.");
        }

        waitForUserInput();
    }

}
