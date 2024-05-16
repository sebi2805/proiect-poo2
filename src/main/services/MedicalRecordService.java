package main.services;

import main.entities.MedicalRecord;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;

import java.util.List;
import java.util.Optional;

public class MedicalRecordService extends BaseService<MedicalRecord> {

    private static MedicalRecordService instance;

    private MedicalRecordService() {
        super();
    }

    public static synchronized MedicalRecordService getInstance() {
        if (instance == null) {
            instance = new MedicalRecordService();
        }
        return instance;
    }

    @Override
    public void add(MedicalRecord medicalRecord) throws AlreadyExistsException {
        if (fileService.getMedicalRecordManager().findById(medicalRecord.getId()).isPresent()) {
            throw new AlreadyExistsException("Medical record already exists.");
        }
        fileService.getMedicalRecordManager().add(medicalRecord);
    }

    @Override
    public Optional<MedicalRecord> getById(String id) {
        return fileService.getMedicalRecordManager().findById(id);
    }

    @Override
    public void update(MedicalRecord medicalRecord) throws NotFoundException {
        if (!fileService.getMedicalRecordManager().findById(medicalRecord.getId()).isPresent()) {
            throw new NotFoundException("Medical record not found.");
        }
        fileService.getMedicalRecordManager().update(medicalRecord);
    }

    @Override
    public void delete(String id) throws NotFoundException {
        if (!fileService.getMedicalRecordManager().findById(id).isPresent()) {
            throw new NotFoundException("Medical record not found.");
        }
        fileService.getMedicalRecordManager().delete(id);
    }

    @Override
    public List<MedicalRecord> getAll() {
        return fileService.getMedicalRecordManager().findAll();
    }
}
