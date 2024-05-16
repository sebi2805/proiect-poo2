package main.services;

import main.entities.Medic;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.storage.FileService;
import main.util.Option;
import main.util.SearchCriteriaPerson;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MedicService implements BaseService<Medic> {
    protected final FileService fileService = FileService.getInstance();
    private static MedicService instance;

    private MedicService() {
        super();
    }

    public synchronized static MedicService getInstance() {
        if (instance == null) {
            instance = new MedicService();
        }
        return instance;
    }

    @Override
    public void add(Medic medic) throws AlreadyExistsException {
        if (fileService.getMedicManager().findById(medic.getId()).isPresent()) {
            throw new AlreadyExistsException("Medic already exists.");
        }
        fileService.getMedicManager().add(medic);
    }

    @Override
    public Optional<Medic> getById(String medicId) {
        return fileService.getMedicManager().findById(medicId);
    }

    @Override
    public void update(Medic medic) throws NotFoundException {
        if (!fileService.getMedicManager().findById(medic.getId()).isPresent()) {
            throw new NotFoundException("Medic not found.");
        }
        fileService.getMedicManager().update(medic);
    }

    @Override
    public void delete(String medicId) throws NotFoundException {
        if (!fileService.getMedicManager().findById(medicId).isPresent()) {
            throw new NotFoundException("Medic not found.");
        }
        fileService.getMedicManager().delete(medicId);
    }

    @Override
    public List<Medic> getAll() {
        return fileService.getMedicManager().findAll();
    }

    public List<Medic> searchMedics(SearchCriteriaPerson criteria) {
        return getAll().stream()
                .filter(medic -> matchesCriteria(medic, criteria))
                .collect(Collectors.toList());
    }

    private boolean matchesCriteria(Medic medic, SearchCriteriaPerson criteria) {
        boolean matchesName = criteria.getName() == null || medic.getName().toLowerCase().contains(criteria.getName().toLowerCase());
        boolean matchesEmail = criteria.getEmail() == null || medic.getEmail().toLowerCase().equals(criteria.getEmail().toLowerCase());
        boolean matchesPhone = criteria.getPhone() == null || medic.getPhone().equals(criteria.getPhone());

        return matchesName && matchesEmail && matchesPhone;
    }
    public List<Option> getOptions() {
        return getAll().stream()
                .map(client -> new Option(client.getId(), client.getName()))
                .collect(Collectors.toList());
    }
}
