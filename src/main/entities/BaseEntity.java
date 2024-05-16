package main.entities;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvDate;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEntity {
    @CsvBindByPosition(position = 0)
    protected String id;
    @CsvBindByPosition(position = 1)
    @CsvDate("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    protected LocalDateTime createdAt;
    @CsvBindByPosition(position = 2)
    @CsvDate("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    protected LocalDateTime updatedAt;
    @CsvBindByPosition(position = 3)
    @CsvDate("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS")
    protected LocalDateTime deletedAt;

    // Constructor
    public BaseEntity(String id) {
        this.id = id;
    }
    public BaseEntity() {
        this.id = UUID.randomUUID().toString(); 
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt=createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public void markUpdated() {
        setUpdatedAt(LocalDateTime.now());
    }

    public void markDeleted() {
        setDeletedAt(LocalDateTime.now());
    }
    public String getEntityTypeName() {
        return this.getClass().getSimpleName();
    }
}
