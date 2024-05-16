package main.services;

import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.storage.FileService;
import main.util.SearchCriteriaPerson;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class BaseService<T> {
    protected final FileService fileService = FileService.getInstance();

    public abstract void add(T entity) throws AlreadyExistsException;

    public abstract Optional<T> getById(String id);

    public abstract void update(T entity) throws NotFoundException;

    public abstract void delete(String id) throws NotFoundException;

    public abstract List<T> getAll();

}
