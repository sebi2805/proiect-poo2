package main.entities;

import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

public abstract class Person {
    protected String ID;
    protected String name;
    protected String phone;
    protected String email;

    // Constructor
    public Person(String ID, String name, String phone, String email)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        this.ID = ID;
        this.name = name;
        setPhone(phone); // Use setter to validate, might throw InvalidPhoneNumberException
        setEmail(email); // Use setter to validate, might throw InvalidEmailFormatException
    }

    // Getters
    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) throws InvalidPhoneNumberException {
        if(phone.matches("\\+?[0-9. ()-]{10,20}")) {
            this.phone = phone;
        } else {
            throw new InvalidPhoneNumberException("Invalid phone format");
        }
    }

    public void setEmail(String email) throws InvalidEmailFormatException {
        if(email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            this.email = email;
        } else {
            throw new InvalidEmailFormatException("Invalid email format");
        }
    }


    // Since this class is abstract, you might also define some abstract methods that must be implemented by subclasses.
    // For example, an abstract method for displaying person's details.
    public abstract void displayDetails();
}
