package main.services;

import main.entities.MedicalRecord;
import main.exceptions.AlreadyExistsException;
import main.storage.FileService;

public class MedicalRecordService {
    private static MedicalRecordService instance;
    private final FileService fileService;

    private MedicalRecordService(){
        fileService = FileService.getInstance();
    }
    public static MedicalRecordService getInstance(){
        if(instance == null)
        {
            instance = new MedicalRecordService();
        }
        return instance;
    }
    public void addMedicalRecord(MedicalRecord medicalRecord) throws AlreadyExistsException {
        fileService.getMedicalRecordManager().add(medicalRecord);
    }
}
