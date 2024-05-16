package main.entities;

import com.opencsv.bean.CsvBindByPosition;
import main.exceptions.InvalidEmailFormatException;
import main.exceptions.InvalidPhoneNumberException;

import javax.swing.text.html.parser.Entity;

public abstract class Person
        extends BaseEntity
        implements Comparable<Person> {
    @CsvBindByPosition(position = 4)
    protected String name;
    @CsvBindByPosition(position = 5)
    protected String phone;
    @CsvBindByPosition(position = 6)
    protected String email;

    public Person() {
        super();
        this.name = "";
        this.phone = "";
        this.email = "";
    }
    public Person(String name, String phone, String email)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super();
        this.name = name;
        setPhone(phone);
        setEmail(email);
    }

    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name);
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
}
