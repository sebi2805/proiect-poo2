package main.services;

import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;
import main.storage.FileService;
import main.util.SearchCriteriaPerson;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface BaseService<T> {
    void add(T entity) throws AlreadyExistsException;

    Optional<T> getById(String id);

    void update(T entity) throws NotFoundException;

    void delete(String id) throws NotFoundException;

    List<T> getAll();
}
