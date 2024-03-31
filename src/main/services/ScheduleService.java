package main.services;

import main.entities.AppointmentOption;
import main.entities.Medic;
import main.entities.RegularAppointment;
import main.enums.AppointmentStatus;
import main.storage.FileService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// TODO MAKE IT SINGLETON
public class ScheduleService {
    private final FileService fileService = FileService.getInstance();

    private boolean overlapsWithRegularAppointment(LocalDateTime appointmentStart, List<RegularAppointment> regularAppointments) {
        for (RegularAppointment regularAppointment : regularAppointments) {
            LocalDateTime appointmentDate = regularAppointment.getAppointmentDate();
            long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(appointmentDate.toLocalDate(), appointmentStart.toLocalDate());

            switch (regularAppointment.getFrequency()) {
                case WEEKLY:
                    if (daysBetween % 7 == 0 && appointmentDate.toLocalTime().equals(appointmentStart.toLocalTime())) return true;
                    break;
                case BIWEEKLY:
                    if (daysBetween % 14 == 0 && appointmentDate.toLocalTime().equals(appointmentStart.toLocalTime())) return true;
                    break;
                case MONTHLY:
                    if (appointmentDate.getDayOfMonth() == appointmentStart.getDayOfMonth() && appointmentDate.toLocalTime().equals(appointmentStart.toLocalTime())) return true;
                    break;
            }
        }
        return false;
    }
    public List<AppointmentOption> findAvailableAppointmentsBySpecialty(String specialty, LocalDate desiredDate) {
        List<AppointmentOption> availableOptions = new ArrayList<>();
        List<Medic> medicsWithSpecialty = fileService.getMedicManager().findAll().stream()
                .filter(medic -> medic.getSpecialty().equals(specialty))
                .collect(Collectors.toList());

        // Presupunem că regularAppointments a fost deja definit și populat
        List<RegularAppointment> regularAppointments = fileService.getRegularAppointmentManager().findAll().stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.SCHEDULED)
                .map(appointment -> (RegularAppointment)appointment)
                .collect(Collectors.toList());

        for (Medic medic : medicsWithSpecialty) {
            LocalDateTime startOfWork = LocalDateTime.of(desiredDate, medic.getWorkingHoursStart());
            LocalDateTime endOfWork = LocalDateTime.of(desiredDate, medic.getWorkingHoursEnd());

            for (LocalDateTime start = startOfWork; start.isBefore(endOfWork); start = start.plusHours(1)) {
                LocalDateTime end = start.plusHours(1);
                final LocalDateTime startTime = start;

                boolean isScheduledAppointmentOverlap = fileService.getRegularAppointmentManager().findAll().stream()
                        .filter(appointment -> appointment.getMedic().getId().equals(medic.getId()) && appointment.getStatus() == AppointmentStatus.SCHEDULED)
                        .anyMatch(appointment -> appointment.getAppointmentDate().isEqual(startTime) || (appointment.getAppointmentDate().isAfter(startTime) && appointment.getAppointmentDate().isBefore(end)));

                List<RegularAppointment> medicRegularAppointments = regularAppointments.stream()
                        .filter(ra -> ra.getMedic().getId().equals(medic.getId()))
                        .collect(Collectors.toList());

                boolean isRegularAppointmentOverlap = overlapsWithRegularAppointment(start, medicRegularAppointments);

                if (!isScheduledAppointmentOverlap && !isRegularAppointmentOverlap) {
                    availableOptions.add(new AppointmentOption(medic, start));
                }
            }
        }

        return availableOptions;
    }


}
