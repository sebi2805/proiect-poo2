package main.menus;

import main.entities.Appointment;
import main.entities.Client;
import main.entities.Medic;
import main.entities.RegularAppointment;
import main.enums.AppointmentFrequency;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.services.ClientService;
import main.services.MedicService;
import main.services.ScheduleService;
import main.services.ServiceManager;
import main.util.Option;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class RegularAppointmentMenu extends EntityMenu<RegularAppointment> {
    private final ClientService clientService;
    private final MedicService medicService;
    public RegularAppointmentMenu(ScheduleService service, ClientService clientService, MedicService medicService) {
        super(service);
        this.clientService = clientService;
        this.medicService = medicService;
    }

    @Override
    protected void printMenuOptions() {
        System.out.println("\nRegular Appointment Menu:");
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
        if (appointments.isEmpty()) {
            System.out.println("No regular appointments found.");
        } else {
            for (RegularAppointment appointment : appointments) {
                System.out.println(appointment);
                System.out.println("\n");
            }
        }
        waitForUserInput();
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
        waitForUserInput();
    }
    private AppointmentFrequency getFrequency() {
        AppointmentFrequency[] frequencies = AppointmentFrequency.values();
        System.out.println("Enter Appointment Frequency (choose one): ");
        for (int i = 0; i < frequencies.length; i++) {
            System.out.printf("%d. %s\n", i + 1, frequencies[i]);
        }

        int index = -1;
        while (index < 1 || index > frequencies.length) {
            System.out.print("Select the frequency (enter the number): ");
            try {
                index = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        return frequencies[index - 1];
    }

    @Override
    protected void add() {
        try {
            String clientId = getUserChoice(ServiceManager.getClientService().getOptions(), "Select Client:");
            String medicId = getUserChoice(ServiceManager.getMedicService().getOptions(), "Select Medic:");
            LocalDateTime appointmentDate = getAppointmentDate();

            AppointmentFrequency frequency = getFrequency();
            if (frequency == null) {
                System.out.println("Invalid frequency. Regular Appointment not added.");
                return;
            }

            RegularAppointment appointment = new RegularAppointment(clientId, medicId, appointmentDate, frequency);
            try {
                service.add(appointment);
                System.out.println("Regular Appointment added successfully.");
            } catch (AlreadyExistsException e) {
                System.out.println("Error: Regular Appointment already exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: Invalid input. Please try again.");
        } finally {
            waitForUserInput();
        }
    }

    @Override
    protected void update() {
        RegularAppointment appointment = chooseRegularAppointment();
        if (appointment == null) {
            return;
        }

        System.out.println("Current details: " + appointment);

        System.out.print("Enter new Appointment Date and Time (leave blank to keep current): ");
        String appointmentDateInput = scanner.nextLine();
        if (!appointmentDateInput.isEmpty()) {
            try {
                LocalDateTime appointmentDate = LocalDateTime.parse(appointmentDateInput);
                appointment.setAppointmentDate(appointmentDate);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date and time format.");
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

        System.out.print("Enter new Client ID (leave blank to keep current): ");
        String newClientId = getUserChoice(ServiceManager.getClientService().getOptions(), "Select new Client:");
        if (newClientId != null) {
            appointment.setClientId(newClientId);
        }

        System.out.print("Enter new Medic ID (leave blank to keep current): ");
        String newMedicId = getUserChoice(ServiceManager.getMedicService().getOptions(), "Select new Medic:");
        if (newMedicId != null) {
            appointment.setMedicId(newMedicId);
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
        RegularAppointment appointment = chooseRegularAppointment();
        if (appointment == null) {
            return;
        }

        try {
            service.delete(appointment.getId());
            System.out.println("Regular Appointment deleted successfully.");
        } catch (NotFoundException e) {
            System.out.println("Error: Regular Appointment not found.");
        }

        waitForUserInput();
    }

    private LocalDateTime getAppointmentDate() {
        System.out.print("Enter Appointment Date and Time (YYYY-MM-DDTHH:MM): ");
        return LocalDateTime.parse(scanner.nextLine());
    }

    private RegularAppointment chooseRegularAppointment() {
        List<RegularAppointment> appointments = service.getAll();
        if (appointments.isEmpty()) {
            System.out.println("No regular appointments available.");
            waitForUserInput();
            return null;
        }
        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            System.out.printf("%d. %s | %s | %s\n",
                    i + 1,
                    appointment.getAppointmentDate(),
                    clientService.getById(appointment.getClientId()).map(Client::getName).orElse("Unknown Client"),
                    medicService.getById(appointment.getMedicId()).map(Medic::getName).orElse("Unknown Medic")
            );
        }

        int appointmentIndex = getUserIndexInput(appointments.size(), "Select a regular appointment (enter the number): ");
        if (appointmentIndex == -1) {
            return null;
        }

        return appointments.get(appointmentIndex);
    }
}
