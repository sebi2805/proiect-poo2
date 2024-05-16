package main.menus;

import main.entities.Appointment;
import main.entities.Client;
import main.entities.Medic;
import main.entities.OneTimeAppointment;
import main.enums.AppointmentStatus;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.services.AppointmentService;
import main.services.ClientService;
import main.services.MedicService;
import main.util.Option;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class OneTimeAppointmentMenu extends EntityMenu<Appointment> {
    private final ClientService clientService;
    private final MedicService medicService;
    private final Scanner scanner = new Scanner(System.in);

    public OneTimeAppointmentMenu(AppointmentService service, ClientService clientService, MedicService medicService) {
        super(service);
        this.clientService = clientService;
        this.medicService = medicService;
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
        if(appointments.isEmpty())
            System.out.println("No appointments are present.\n");
        for (Appointment appointment : appointments) {
            System.out.println(getDetailedAppointment(appointment));
            System.out.println("\n");
        }
        waitForUserInput();
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
        waitForUserInput();
    }

    @Override
    protected void add() {
        try {
            String clientId = getUserChoice(clientService.getOptions(), "Select Client:");
            String medicId = getUserChoice(medicService.getOptions(), "Select Medic:");
            LocalDateTime appointmentDate = getAppointmentDate();

            AppointmentStatus status = AppointmentStatus.SCHEDULED;

            OneTimeAppointment appointment = new OneTimeAppointment(clientId, medicId, appointmentDate, status);
            try {
                service.add(appointment);
                System.out.println("One-Time Appointment added successfully.");
            } catch (AlreadyExistsException e) {
                System.out.println("Error: One-Time Appointment already exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
        } finally {
            waitForUserInput();
        }
    }

    @Override
    protected void update() {
        List<Appointment> appointments = service.getAll();
        if (appointments.isEmpty()) {
            System.out.println("No appointments available to update.");
            waitForUserInput();
            return;
        }

        // Display all appointments with details
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            String appointmentDetails = String.format("%d. %s | %s | %s",
                    i + 1,
                    appointment.getAppointmentDate(),
                    clientService.getById(appointment.getClientId()).map(Client::getName).orElse("Unknown Client"),
                    medicService.getById(appointment.getMedicId()).map(Medic::getName).orElse("Unknown Medic")
            );
            System.out.println(appointmentDetails);
        }

        // Let the user select an appointment by its index
        System.out.print("Select an appointment to update (enter the number): ");
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

        Appointment appointment = appointments.get(appointmentIndex);
        System.out.println("Current details: " + getDetailedAppointment(appointment));

        try {
            updateAppointmentDetails(appointment);
            service.update(appointment);
            System.out.println("One-Time Appointment updated successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: One-Time Appointment not found.");
        }

        waitForUserInput();
    }

    @Override
    protected void delete() {
        List<Appointment> appointments = service.getAll();
        if (appointments.isEmpty()) {
            System.out.println("No appointments available to delete.");
            waitForUserInput();
            return;
        }

        // Display all appointments with details
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            String appointmentDetails = String.format("%d. %s | %s | %s",
                    i + 1,
                    appointment.getAppointmentDate(),
                    clientService.getById(appointment.getClientId()).map(Client::getName).orElse("Unknown Client"),
                    medicService.getById(appointment.getMedicId()).map(Medic::getName).orElse("Unknown Medic")
            );
            System.out.println(appointmentDetails);
        }

        // Let the user select an appointment by its index
        System.out.print("Select an appointment to delete (enter the number): ");
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

        Appointment appointment = appointments.get(appointmentIndex);
        try {
            service.delete(appointment.getId());
            System.out.println("One-Time Appointment deleted successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: One-Time Appointment not found.");
        }

        waitForUserInput();
    }




    private LocalDateTime getAppointmentDate() {
        System.out.print("Enter Appointment Date and Time (YYYY-MM-DDTHH:MM): ");
        return LocalDateTime.parse(scanner.nextLine());
    }

    private void updateAppointmentDetails(Appointment appointment) {
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
            }
        }
    }

    private String getDetailedAppointment(Appointment appointment) {
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment ID: ").append(appointment.getId()).append("\n");

        // Fetching client details
        Optional<Client> client = clientService.getById(appointment.getClientId());
        client.ifPresentOrElse(
                value -> sb.append("Client: ").append(value.getName()).append(" (ID: ").append(value.getId()).append(")\n"),
                () -> sb.append("Client ID: ").append(appointment.getClientId()).append(" (Details not found)\n")
        );

        // Fetching medic details
        Optional<Medic> medic = medicService.getById(appointment.getMedicId());
        medic.ifPresentOrElse(
                value -> sb.append("Medic: ").append(value.getName()).append(" (ID: ").append(value.getId()).append(")\n"),
                () -> sb.append("Medic ID: ").append(appointment.getMedicId()).append(" (Details not found)\n")
        );

        sb.append("Appointment Date: ").append(appointment.getAppointmentDate()).append("\n");
        sb.append("Status: ").append(appointment.getStatus());
        return sb.toString();
    }
}
