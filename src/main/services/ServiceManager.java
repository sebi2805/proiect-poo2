package main.services;

public class ServiceManager {

    private static final ClientService clientService = ClientService.getInstance();
    private static final AppointmentService appointmentService = AppointmentService.getInstance();
    private static final MedicService medicService = MedicService.getInstance();
    private static final ScheduleService scheduleService = ScheduleService.getInstance();
    private static final MedicalRecordService medicalRecordService = MedicalRecordService.getInstance();

    private static ServiceManager instance;
    private ServiceManager() {}
    public synchronized static ServiceManager getInstance(){
        if(instance==null){
            instance = new ServiceManager();
        }
        return instance;
    }
    public static ClientService getClientService() {
        return clientService;
    }
    public static MedicalRecordService getMedicalRecordService() {
        return medicalRecordService;
    }

    public static AppointmentService getAppointmentService() { return appointmentService; }
    public static MedicService getMedicService() {
        return medicService;
    }
    public static ScheduleService getScheduleService() {
        return scheduleService;
    }


}
