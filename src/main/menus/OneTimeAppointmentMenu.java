package main.menus;
import main.entities.Appointment;
import main.entities.Client;
import main.entities.Medic;
import main.entities.OneTimeAppointment;
import main.enums.AppointmentStatus;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.services.AppointmentService;
import main.services.ServiceManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class OneTimeAppointmentMenu extends EntityMenu<Appointment> {

    public OneTimeAppointmentMenu(AppointmentService service) {
        super(service);
    }

    @Override
    protected void printMenuOptions() {
        System.out.println("One-Time Appointment Menu:");
        System.out.println("1. Display all One-Time Appointments");
        System.out.println("2. Search One-Time Appointment by ID");
        System.out.println("3. Add One-Time Appointment");
        System.out.println("4. Update One-Time Appointment");
        System.out.println("5. Delete One-Time Appointment");
        System.out.println("0. Back to Main Menu");
        System.out.print("Enter your choice: ");
    }

    @Override
    protected void displayAll() {
        List<Appointment> appointments = service.getAll();
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
            System.out.println("\n");
        }
    }

    @Override
    protected void searchById() {
        System.out.print("Enter One-Time Appointment ID: ");
        String id = scanner.nextLine();
        Optional<Appointment> appointment = service.getById(id);
        appointment.ifPresentOrElse(
                System.out::println,
                () -> System.out.println("One-Time Appointment not found.")
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
            System.out.println("Invalid status. One-Time Appointment not added.");
            return;
        }

        OneTimeAppointment appointment = new OneTimeAppointment(client.get(), medic.get(), appointmentDate, status);
        try {
            service.add(appointment);
            System.out.println("One-Time Appointment added successfully.");
        } catch (AlreadyExistsException e) {
            System.out.println("Error: One-Time Appointment already exists.");
        }
    }

    @Override
    protected void update() {
        System.out.print("Enter One-Time Appointment ID: ");
        String id = scanner.nextLine();
        Optional<Appointment> optionalAppointment = service.getById(id);
        if (optionalAppointment.isPresent()) {
            Appointment appointment = optionalAppointment.get();
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
                    System.out.println("Invalid status. One-Time Appointment not updated.");
                    return;
                }
            }

            try {
                service.update(appointment);
                System.out.println("One-Time Appointment updated successfully.");
            } catch (NotFoundException e) {
                System.out.println("Error: One-Time Appointment not found.");
            }
        } else {
            System.out.println("One-Time Appointment not found.");
        }
    }

    @Override
    protected void delete() {
        System.out.print("Enter One-Time Appointment ID: ");
        String id = scanner.nextLine();
        try {
            service.delete(id);
            System.out.println("One-Time Appointment deleted successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: One-Time Appointment not found.");
        }
    }
}
