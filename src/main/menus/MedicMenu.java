package main.menus;

import main.entities.*;
import main.enums.MedicalSpecialty;
import main.exceptions.AlreadyExistsException;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;
import main.exceptions.NotFoundException;
import main.services.*;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class MedicMenu extends EntityMenu<Medic> {

    private final MedicalRecordService medicalRecordService;
    private final AppointmentService appointmentService;
    private final ScheduleService scheduleService;

    public MedicMenu(MedicService medicService, MedicalRecordService medicalRecordService, AppointmentService appointmentService, ScheduleService scheduleService) {
        super(medicService);
        this.medicalRecordService = medicalRecordService;
        this.appointmentService = appointmentService;
        this.scheduleService = scheduleService;
    }

    @Override
    protected void printMenuOptions() {
        System.out.println("\nMedic Menu:");
        System.out.println("1. Display all Medics");
        System.out.println("2. Search Medic by ID");
        System.out.println("3. Add Medic");
        System.out.println("4. Update Medic");
        System.out.println("5. Delete Medic");
        System.out.println("6. Get Schedule for Medic");
        System.out.println("7. Get One-Time Appointments for Medic");
        System.out.println("8. Get Regular Appointments for Medic");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    @Override
    protected boolean handleMenuChoice(int choice) {
        switch (choice) {
            case 1 -> displayAll();
            case 2 -> searchById();
            case 3 -> add();
            case 4 -> update();
            case 5 -> delete();
            case 6 -> getScheduleForMedic();
            case 7 -> getOneTimeAppointmentsForMedic();
            case 8 -> getRegularAppointmentsForMedic();
            case 0 -> {
                return false; // Exit to main menu
            }
            default -> System.out.println("Invalid choice. Please try again.");
        }
        return true;
    }

    @Override
    protected void displayAll() {
        List<Medic> medics = service.getAll();
        if (medics.isEmpty()) {
            System.out.println("No medics available.");
        } else {
            for (Medic medic : medics) {
                System.out.println(medic);
                printMedicDetails(medic);
            }
        }
    }

    private void printMedicDetails(Medic medic) {
        int medicalRecordCount = medicalRecordService.getRecordsByMedicId(medic.getId()).size();
        int oneTimeAppointmentsCount = appointmentService.getAppointmentsByMedicId(medic.getId()).size();
        int regularAppointmentsCount = scheduleService.getAppointmentsByMedicId(medic.getId()).size();

        System.out.printf("Medical Records: %d\n", medicalRecordCount);
        System.out.printf("One-Time Appointments: %d\n", oneTimeAppointmentsCount);
        System.out.printf("Regular Appointments: %d\n", regularAppointmentsCount);
        System.out.println("\n");
    }

    @Override
    protected void searchById() {
        String id = promptUser("Enter Medic ID: ");
        Optional<Medic> medic = service.getById(id);
        medic.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Medic not found.")
        );
    }

    @Override
    protected void add() {
        String name = promptUser("Enter Medic Name: ");
        String phone = promptUser("Enter Medic Phone: ");
        String email = promptUser("Enter Medic Email: ");
        MedicalSpecialty specialty;
        try {
            specialty = MedicalSpecialty.valueOf(promptUser("Enter Medic Specialty (GENERAL_PRACTITIONER or SURGEON): ").toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid specialty. Medic not added.");
            return;
        }
        LocalTime workingHoursStart = LocalTime.parse(promptUser("Enter Working Hours Start (HH:MM): "));
        LocalTime workingHoursEnd = LocalTime.parse(promptUser("Enter Working Hours End (HH:MM): "));

        try {
            Medic medic = new Medic(name, phone, email, specialty, workingHoursStart, workingHoursEnd);
            service.add(medic);
            System.out.println("Medic added successfully.");
        } catch (InvalidPhoneNumberException | InvalidEmailFormatException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (AlreadyExistsException e) {
            System.out.println("Error: Medic already exists.");
        }
    }

    @Override
    protected void update() {
        Medic medic = chooseMedic();
        if (medic == null) {
            return;
        }

        System.out.println("Current details: " + medic);

        String name = promptUser("Enter new Medic Name (leave blank to keep current): ");
        if (!name.isEmpty()) {
            medic.setName(name);
        }

        String phone = promptUser("Enter new Medic Phone (leave blank to keep current): ");
        if (!phone.isEmpty()) {
            try {
                medic.setPhone(phone);
            } catch (InvalidPhoneNumberException e) {
                System.out.println("Error: " + e.getMessage());
                waitForUserInput();
                return;
            }
        }

        String email = promptUser("Enter new Medic Email (leave blank to keep current): ");
        if (!email.isEmpty()) {
            try {
                medic.setEmail(email);
            } catch (InvalidEmailFormatException e) {
                System.out.println("Error: " + e.getMessage());
                waitForUserInput();
                return;
            }
        }

        String specialtyInput = promptUser("Enter new Medic Specialty (leave blank to keep current): ");
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

        String workingHoursStartInput = promptUser("Enter new Working Hours Start (leave blank to keep current): ");
        if (!workingHoursStartInput.isEmpty()) {
            LocalTime workingHoursStart = LocalTime.parse(workingHoursStartInput);
            medic.setWorkingHoursStart(workingHoursStart);
        }

        String workingHoursEndInput = promptUser("Enter new Working Hours End (leave blank to keep current): ");
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
        Medic medic = chooseMedic();
        if (medic == null) {
            return;
        }

        try {
            service.delete(medic.getId());
            System.out.println("Medic deleted successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: Medic not found.");
        }

        waitForUserInput();
    }

    private void getScheduleForMedic() {
        Medic medic = chooseMedic();
        if (medic == null) {
            return;
        }

        List<RegularAppointment> schedules = scheduleService.getAppointmentsByMedicId(medic.getId());
        if (schedules.isEmpty()) {
            System.out.println("No schedules found for this medic.");
        } else {
            System.out.println("Schedules:");
            for (RegularAppointment schedule : schedules) {
                System.out.println(schedule);
            }
        }
        waitForUserInput();
    }

    private void getOneTimeAppointmentsForMedic() {
        Medic medic = chooseMedic();
        if (medic == null) {
            return;
        }

        List<OneTimeAppointment> appointments = appointmentService.getAppointmentsByMedicId(medic.getId());
        if (appointments.isEmpty()) {
            System.out.println("No one-time appointments found for this medic.");
        } else {
            System.out.println("One-Time Appointments:");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
        waitForUserInput();
    }

    private void getRegularAppointmentsForMedic() {
        Medic medic = chooseMedic();
        if (medic == null) {
            return;
        }

        List<RegularAppointment> appointments = scheduleService.getAppointmentsByMedicId(medic.getId());
        if (appointments.isEmpty()) {
            System.out.println("No regular appointments found for this medic.");
        } else {
            System.out.println("Regular Appointments:");
            for (Appointment appointment : appointments) {
                System.out.println(appointment);
            }
        }
        waitForUserInput();
    }

    private Medic chooseMedic() {
        List<Medic> medics = service.getAll();
        if (medics.isEmpty()) {
            System.out.println("No medics available.");
            waitForUserInput();
            return null;
        }

        for (int i = 0; i < medics.size(); i++) {
            System.out.printf("%d. %s : %s\n", i + 1, medics.get(i).getName(), medics.get(i).getEmail());
        }

        int medicIndex = promptUserForInt("Select a medic (enter the number): ", 1, medics.size());
        if (medicIndex == -1) return null;

        return medics.get(medicIndex - 1);
    }

    private String promptUser(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    private int promptUserForInt(String message, int min, int max) {
        while (true) {
            try {
                System.out.print(message);
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    return choice;
                }
                System.out.println("Invalid selection. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }
}
