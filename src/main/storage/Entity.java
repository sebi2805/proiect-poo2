package main.storage;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import main.entities.BaseEntity;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class Entity<T extends BaseEntity> {
    private static final String BASE_PATH = "data/csv/directory/";
    private final String csvFilePath;
    private List<T> entities;
    private Class<T> entityClass;
    private Map<String, T> entitiesMap;

    public Entity(String className, Class<T> entityClass) {
        this.entityClass = entityClass;
        this.csvFilePath = BASE_PATH + className + ".csv";
        this.entities = new ArrayList<>();
        loadFromCSV();
    }

    // Load objects from the CSV file into the entities list
    private void loadFromCSV() {
        try (Reader reader = new FileReader(csvFilePath)) {
            ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(entityClass); // Assuming you keep a reference to entityClass

            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withMappingStrategy(strategy)
                    .build();

            entities = new ArrayList<>(csvToBean.parse());
            for( T entity : entities) {
                entitiesMap.put(entity.getId(), entity);
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
    public void add(T entity) {
        entities.add(entity);
        saveToCSV();
    }


    // Update an existing object and save changes to CSV
    public void update(T updatedEntity) {
        for (int i = 0; i < entities.size(); i++) {
            T entity = entities.get(i);
            if (entity.getId().equals(updatedEntity.getId())) {
                entities.set(i, updatedEntity);
                break;
            }
        }
        saveToCSV();
    }

    // Delete an object and save changes to CSV
    public void delete(T entityToRemove) {
        entities.removeIf(entity -> entity.getId().equals(entityToRemove.getId()));
        saveToCSV();
    }

    // Retrieve all objects
    public List<T> findAll() {
        return new ArrayList<>(entities);
    }
}
