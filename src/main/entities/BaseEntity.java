package main.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseEntity {
    protected String id;
    protected LocalDateTime createdAt;
    protected LocalDateTime updatedAt;
    protected LocalDateTime deletedAt;

    // Constructor
    public BaseEntity(String id) {
        this.id = id;
        // updatedAt and deletedAt are not initialized until the entity is updated or deleted
    }
    public BaseEntity() {
        this.id = UUID.randomUUID().toString(); // Generează și atribuie un ID unic
        // updatedAt și deletedAt nu sunt inițializate până când entitatea nu este actualizată sau ștearsă
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
    // createdAt does not have a setter, as it should be set once upon creation

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

    // Utility methods
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
