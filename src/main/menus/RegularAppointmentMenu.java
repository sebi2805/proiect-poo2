package main.menus;

import main.entities.Client;
import main.entities.Medic;
import main.entities.RegularAppointment;
import main.enums.AppointmentFrequency;
import main.enums.AppointmentStatus;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.services.ScheduleService;
import main.services.ServiceManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class RegularAppointmentMenu extends EntityMenu<RegularAppointment> {

    public RegularAppointmentMenu(ScheduleService service) {
        super(service);
    }

    @Override
    protected void printMenuOptions() {
        System.out.println("Regular Appointment Menu:");
        System.out.println("1. Display all Regular Appointments");
        System.out.println("2. Search Regular Appointment by ID");
        System.out.println("3. Add Regular Appointment");
        System.out.println("4. Update Regular Appointment");
        System.out.println("5. Delete Regular Appointment");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    @Override
    protected void displayAll() {
        List<RegularAppointment> appointments = service.getAll();
        for (RegularAppointment appointment : appointments) {
            System.out.println(appointment);
            System.out.println("\n");
        }
    }

    @Override
    protected void searchById() {
        System.out.print("Enter Regular Appointment ID: ");
        String id = scanner.nextLine();
        Optional<RegularAppointment> appointment = service.getById(id);
        appointment.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("Regular Appointment not found.")
        );
    }

    @Override
    protected void add() {
        System.out.print("Enter Client ID: ");
        String clientId = scanner.nextLine();
        Optional<Client> client = ServiceManager.getClientService().getById(clientId);
        if (client.isEmpty()) {
            System.out.println("Error: Client not found.");
            return;
        }

        System.out.print("Enter Medic ID: ");
        String medicId = scanner.nextLine();
        Optional<Medic> medic = ServiceManager.getMedicService().getById(medicId);
        if (medic.isEmpty()) {
            System.out.println("Error: Medic not found.");
            return;
        }

        System.out.print("Enter Appointment Date and Time (YYYY-MM-DDTHH:MM): ");
        LocalDateTime appointmentDate = LocalDateTime.parse(scanner.nextLine());

        System.out.print("Enter Appointment Status (SCHEDULED, CONFIRMED, CANCELLED): ");
        AppointmentStatus status;
        try {
            status = AppointmentStatus.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status. Regular Appointment not added.");
            return;
        }

        System.out.print("Enter Appointment Frequency (DAILY, WEEKLY, MONTHLY): ");
        AppointmentFrequency frequency;
        try {
            frequency = AppointmentFrequency.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid frequency. Regular Appointment not added.");
            return;
        }

        RegularAppointment appointment = new RegularAppointment(client.get().getId(), medic.get().getId(), appointmentDate, status, frequency);
        try {
            service.add(appointment);
            System.out.println("Regular Appointment added successfully.");
        } catch (AlreadyExistsException e) {
            System.out.println("Error: Regular Appointment already exists.");
        }
    }

    @Override
    protected void update() {
        List<RegularAppointment> appointments = service.getAll();
        if (appointments.isEmpty()) {
            System.out.println("No regular appointments available to update.");
            waitForUserInput();
            return;
        }

        // Display all regular appointments
        for (int i = 0; i < appointments.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, appointments.get(i));
        }

        // Let the user select a regular appointment by its index
        System.out.print("Select a regular appointment to update (enter the number): ");
        int appointmentIndex;
        try {
            appointmentIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (appointmentIndex < 0 || appointmentIndex >= appointments.size()) {
                System.out.println("Invalid selection. Please try again.");
                waitForUserInput();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            waitForUserInput();
            return;
        }

        RegularAppointment appointment = appointments.get(appointmentIndex);
        System.out.println("Current details: " + appointment);

        System.out.print("Enter new Appointment Date and Time (leave blank to keep current): ");
        String appointmentDateInput = scanner.nextLine();
        if (!appointmentDateInput.isEmpty()) {
            LocalDateTime appointmentDate = LocalDateTime.parse(appointmentDateInput);
            appointment.setAppointmentDate(appointmentDate);
        }

        System.out.print("Enter new Appointment Status (leave blank to keep current): ");
        String statusInput = scanner.nextLine();
        if (!statusInput.isEmpty()) {
            try {
                AppointmentStatus status = AppointmentStatus.valueOf(statusInput.toUpperCase());
                appointment.setStatus(status);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Regular Appointment not updated.");
                waitForUserInput();
                return;
            }
        }

        System.out.print("Enter new Appointment Frequency (leave blank to keep current): ");
        String frequencyInput = scanner.nextLine();
        if (!frequencyInput.isEmpty()) {
            try {
                AppointmentFrequency frequency = AppointmentFrequency.valueOf(frequencyInput.toUpperCase());
                appointment.setFrequency(frequency);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid frequency. Regular Appointment not updated.");
                waitForUserInput();
                return;
            }
        }

        try {
            service.update(appointment);
            System.out.println("Regular Appointment updated successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: Regular Appointment not found.");
        }

        waitForUserInput();
    }


    @Override
    protected void delete() {
        List<RegularAppointment> appointments = service.getAll();
        if (appointments.isEmpty()) {
            System.out.println("No regular appointments available to delete.");
            waitForUserInput();
            return;
        }

        // Display all regular appointments
        for (int i = 0; i < appointments.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, appointments.get(i));
        }

        // Let the user select a regular appointment by its index
        System.out.print("Select a regular appointment to delete (enter the number): ");
        int appointmentIndex;
        try {
            appointmentIndex = Integer.parseInt(scanner.nextLine()) - 1;
            if (appointmentIndex < 0 || appointmentIndex >= appointments.size()) {
                System.out.println("Invalid selection. Please try again.");
                waitForUserInput();
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            waitForUserInput();
            return;
        }

        RegularAppointment appointment = appointments.get(appointmentIndex);
        try {
            service.delete(appointment.getId());
            System.out.println("Regular Appointment deleted successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: Regular Appointment not found.");
        }

        waitForUserInput();
    }

}
