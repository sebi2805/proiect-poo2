package main.services;

import main.entities.GeneralPractitioner;
import main.entities.Medic;
import main.entities.Surgeon;
import main.enums.MedicalSpecialty;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.storage.FileService;
import main.util.SearchCriteriaPerson;

import java.util.List;
import java.util.stream.Collectors;

public class MedicService {
    private final FileService fileService = FileService.getInstance();
    private static MedicService instance;
    private MedicService(){}
    public synchronized static MedicService getInstance(){
        if(instance==null){
            instance = new MedicService();
        }
        return instance;
    }

    public void addMedic(Medic medic) throws AlreadyExistsException {
        fileService.getMedicManager().add(medic);
    }
    public Medic getMedicById(String medicId){
        return fileService.getMedicManager().findById(medicId).orElse(null);
    }
    public void UpdateMedic(Medic medic) throws NotFoundException {
            fileService.getMedicManager().update((Surgeon)medic);
    }

    public void Delete(Medic medic) throws NotFoundException {
        fileService.getMedicManager().delete(medic.getId());
    }

    public List<Medic> searchMedics(SearchCriteriaPerson criteria) {
        return fileService.getMedicManager().findAll().stream()
                .filter(medic -> matchesCriteria(medic, criteria))
                .collect(Collectors.toList());
    }

    private boolean matchesCriteria(Medic medic, SearchCriteriaPerson criteria) {
        boolean matchesName = criteria.getName() == null || medic.getName().toLowerCase().contains(criteria.getName().toLowerCase());
        boolean matchesEmail = criteria.getEmail() == null || medic.getEmail().toLowerCase().equals(criteria.getEmail().toLowerCase());
        boolean matchesPhone = criteria.getPhone() == null || medic.getPhone().equals(criteria.getPhone());

        return matchesName && matchesEmail && matchesPhone;
    }

}
