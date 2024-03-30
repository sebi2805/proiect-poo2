package main.services;

import main.entities.GeneralPractitioner;
import main.entities.Medic;
import main.entities.Surgeon;
import main.enums.MedicalSpecialty;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.storage.FileService;

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

    //    TODO MAYBE REFACTOR IT A LITTLE BIT
    public void addMedic(Medic medic) throws AlreadyExistsException {
        if(medic.getSpecialty()==MedicalSpecialty.SURGEON){
            Surgeon surgeon = (Surgeon)medic;
            surgeon.setSpecialty(medic.getSpecialty());
            fileService.getSurgeonManager().add(surgeon);
        } else if(medic.getSpecialty()==MedicalSpecialty.GENERAL_PRACTITIONER){
            GeneralPractitioner generalPractitioner = (GeneralPractitioner)medic;
            generalPractitioner.setSpecialty(medic.getSpecialty());
            fileService.getGeneralPractitionerManager().add(generalPractitioner);
        }
    }
    public Medic getMedicById(String medicId){
        Medic medic = fileService.getSurgeonManager().findById(medicId).orElse(null);
        if(medic != null){
            medic = fileService.getGeneralPractitionerManager().findById(medicId).orElse(null);
        }
        return medic;
    }
    public void UpdateMedic(Medic medic) throws NotFoundException {
        if(medic.getSpecialty() == MedicalSpecialty.SURGEON){
            fileService.getSurgeonManager().update((Surgeon)medic);
        } else if(medic.getSpecialty() == MedicalSpecialty.GENERAL_PRACTITIONER){
            fileService.getGeneralPractitionerManager().update((GeneralPractitioner)medic);
        }
    }

    public void Delete(Medic medic) throws NotFoundException {
        if(medic.getSpecialty() == MedicalSpecialty.SURGEON){
            fileService.getSurgeonManager().delete(medic.getId());
        } else if(medic.getSpecialty() == MedicalSpecialty.GENERAL_PRACTITIONER){
            fileService.getGeneralPractitionerManager().delete(medic.getId());
        }
    }

}
