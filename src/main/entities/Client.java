package main.entities;

import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;


public class Client extends Person {

    public Client( String name, String phone, String email,
                  String appointmentIdsSerialized, String medicalRecordIdsSerialized)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(name, phone, email);
    }

    public Client(String name, String phone, String email)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super(name, phone, email);
    }

    public Client() {
        super();
    }

    @Override
    public String toString() {
        return "Client ID: " + getId() + "\n" +
                "Name: " + getName() + "\n" +
                "Phone: " + getPhone() + "\n" +
                "Email: " + getEmail() + "\n";
    }
}
