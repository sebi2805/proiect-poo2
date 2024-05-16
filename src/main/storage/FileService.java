package main.storage;


import main.entities.*;

public class FileService
{
    private static FileService instance;
    private final Entity<Client> clientManager;
    private final Entity<Medic> medicManager;
    private final Entity<OneTimeAppointment> oneTimeAppointmentManager;
    private final Entity<RegularAppointment> regularAppointmentManager;
    private final Entity<MedicalRecord> medicalRecordManager;
    private FileService(){
        clientManager = new Entity<>("clients", Client.class);
        medicManager = new Entity<>("medics", Medic.class);
        oneTimeAppointmentManager = new Entity<>("oneTimeAppointments", OneTimeAppointment.class);
        regularAppointmentManager = new Entity<>("regularAppointments", RegularAppointment.class);
        medicalRecordManager = new Entity<>("medicalRecords", MedicalRecord.class);
    }

    public static synchronized FileService getInstance() {
        if(instance==null){
            instance = new FileService();
        }
        return instance;
    }
    public Entity<Client> getClientManager() {
        return clientManager;
    }
    public Entity<Medic> getMedicManager(){return medicManager;}
    public Entity<OneTimeAppointment> getOneTimeAppointmentManager() {
        return oneTimeAppointmentManager;
    }
    public Entity<RegularAppointment> getRegularAppointmentManager() {
        return regularAppointmentManager;
    }
    public Entity<MedicalRecord> getMedicalRecordManager() {
        return medicalRecordManager;
    }

}
