package main.services;

import main.entities.Client;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.storage.FileService;
import main.util.Option;
import main.util.SearchCriteriaPerson;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientService implements BaseService<Client> {
    protected final FileService fileService = FileService.getInstance();

    private static ClientService instance;

    private ClientService() {
        super();
    }

    public static synchronized ClientService getInstance() {
        if (instance == null) {
            instance = new ClientService();
        }
        return instance;
    }

    @Override
    public void add(Client client) throws AlreadyExistsException {
        Optional<Client> existingClient = getById(client.getId());
        if (existingClient.isPresent()) {
            throw new AlreadyExistsException("Client with ID " + client.getId() + " already exists.");
        }
        fileService.getClientManager().add(client);
        System.out.println("Client successfully added.");
    }

    @Override
    public Optional<Client> getById(String clientId) {
        return fileService.getClientManager().findById(clientId);
    }

    @Override
    public void update(Client client) throws NotFoundException {
        if (!getById(client.getId()).isPresent()) {
            throw new NotFoundException("Client with ID " + client.getId() + " not found.");
        }
        fileService.getClientManager().update(client);
        System.out.println("Client successfully updated.");
    }

    @Override
    public void delete(String clientId) throws NotFoundException {
        if (!getById(clientId).isPresent()) {
            throw new NotFoundException("Client with ID " + clientId + " not found.");
        }
        fileService.getClientManager().delete(clientId);
        System.out.println("Client with ID " + clientId + " successfully deleted.");
    }

    @Override
    public List<Client> getAll() {
        return fileService.getClientManager().findAll();
    }

    public List<Client> searchClients(SearchCriteriaPerson criteria) {
        return getAll().stream()
                .filter(client -> matchesCriteria(client, criteria))
                .collect(Collectors.toList());
    }

    private boolean matchesCriteria(Client client, SearchCriteriaPerson criteria) {
        boolean matchesName = criteria.getName() == null || client.getName().toLowerCase().contains(criteria.getName().toLowerCase());
        boolean matchesEmail = criteria.getEmail() == null || client.getEmail().equalsIgnoreCase(criteria.getEmail());
        boolean matchesPhone = criteria.getPhone() == null || client.getPhone().equals(criteria.getPhone());

        return matchesName && matchesEmail && matchesPhone;
    }
    public List<Option> getOptions() {
        return getAll().stream()
                .map(client -> new Option(client.getId(), client.getName()))
                .collect(Collectors.toList());
    }
}
