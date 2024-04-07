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
import java.lang.reflect.Constructor;
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

    private void loadFromCSV() {
        try (Reader reader = new FileReader(csvFilePath)) {
            ColumnPositionMappingStrategy<T> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(entityClass);

            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(reader)
                    .withMappingStrategy(strategy)
                    .build();

            entities = new ArrayList<>(); // Asigură-te că lista este curățată sau reinițializată aici
            entitiesMap = new HashMap<>(); // De asemenea, reinițializează map-ul, dacă este utilizat

            System.out.println("load entities");
            for (T entity : csvToBean.parse()) {
                entities.add(entity);
                entitiesMap.put(entity.getId(), entity); // Atenție la ID-uri duplicate
            }
            System.out.println(entities.size());
        } catch (IOException e) {

            e.printStackTrace();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
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
    public void add(T entity) throws AlreadyExistsException {
        if(findById(entity.getId()).isEmpty()) {
            entity.setCreatedAt(LocalDateTime.now());
            entities.add(entity);
            saveToCSV();
        } else {
            throw new AlreadyExistsException("Entity already exists with this id");
        }
    }


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

    public void delete(String entityId) throws NotFoundException {
        boolean found = entities.removeIf(entity -> entity.getId().equals(entityId));
        if (found) {
            entitiesMap.remove(entityId);
            saveToCSV();
        } else {
            throw new NotFoundException("Entity with ID " + entityId + " does not exist.");
        }
    }

    public List<T> findAll() {
        return new ArrayList<>(entities);
    }
}
