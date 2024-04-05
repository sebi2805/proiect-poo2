package main.storage;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import main.entities.BaseEntity;
import main.exceptions.AlreadyExistsException;
import main.exceptions.NotFoundException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDateTime;
import java.util.*;


public class Entity<T extends BaseEntity> {
    private static final String BASE_PATH = "data/csv/";
    private final String csvFilePath;
    private List<T> entities;
    private final Class<T> entityClass;
    private Map<String, T> entitiesMap;

    public Entity(String className, Class<T> entityClass) {
        this.entityClass = entityClass;
        this.csvFilePath = BASE_PATH + className + ".csv";
        this.entities = new ArrayList<>();
        this.entitiesMap = new HashMap<>();
        loadFromCSV();
    }

    // Load objects from the CSV file into the entities list
    private void loadFromCSV() {
        try (Reader reader = new FileReader(csvFilePath)) {
            ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(entityClass); // Asigură-te că aceasta corespunde structurii clasei T

            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withMappingStrategy(strategy)
                    .build();

            entities = new ArrayList<>(); // Asigură-te că lista este curățată sau reinițializată aici
            entitiesMap = new HashMap<>(); // De asemenea, reinițializează map-ul, dacă este utilizat

            for (T entity : csvToBean.parse()) {
                entities.add(entity);
                entitiesMap.put(entity.getId(), entity); // Atenție la ID-uri duplicate
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save objects from the entities list back into the CSV file
    public void saveToCSV() {
        try (Writer writer = new FileWriter(csvFilePath)) {
            StatefulBeanToCsv<T> beanToCsv = new StatefulBeanToCsvBuilder<T>(writer).build();
            beanToCsv.write(entities);
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            e.printStackTrace();
        }
    }
    // Find an object by ID (if the object has an ID)
//    public Optional<T> findById(String id) {
//        for( T entity : entities){
//            if(entity.getId().equals(id)){
//                return Optional.of(entity);
//            }
//        }
//        return Optional.empty();
//    }
    public Optional<T> findById(String id) {
        return Optional.ofNullable(entitiesMap.get(id));
    }
    // Add a new object to the in-memory list and save to CSV
    public void add(T entity) throws AlreadyExistsException {
        if(findById(entity.getId()).isEmpty()) {
            entity.setCreatedAt(LocalDateTime.now());
            entities.add(entity);
            saveToCSV();
        } else {
            throw new AlreadyExistsException("Entity already exists with this id");
        }
    }


    // Update an existing object and save changes to CSV
    public void update(T updatedEntity) throws NotFoundException {
        boolean found = false;
        for (int i = 0; i < entities.size(); i++) {
            T entity = entities.get(i);
            if (entity.getId().equals(updatedEntity.getId())) {
                updatedEntity.setCreatedAt(LocalDateTime.now());
                entities.set(i, updatedEntity);
                found = true;
                entitiesMap.put(entity.getId(), entity);
                break;
            }
        }
        if(found){
            saveToCSV();
        } else { throw new NotFoundException("The entity does not exists");}
    }

    // Delete an object and save changes to CSV
    public void delete(String entityId) throws NotFoundException {
        boolean found = entities.removeIf(entity -> entity.getId().equals(entityId));
        if (found) {
            // Also update the map if you maintain entities in both a list and a map
            entitiesMap.remove(entityId);
            saveToCSV();
        } else {
            throw new NotFoundException("Entity with ID " + entityId + " does not exist.");
        }
    }

    // Retrieve all objects
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }
}
