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

    // Constructor
    public Person() {
        super(); // Apelează constructorul clasei de bază, dacă este necesar
        this.name = ""; // Inițializează cu un șir gol sau altă valoare implicată
        this.phone = ""; // Poți folosi valori implicite care sunt sigure
        this.email = ""; // Evită erorile de validare pentru valori nule
    }
    public Person(String name, String phone, String email)
            throws InvalidPhoneNumberException, InvalidEmailFormatException {
        super();
        this.name = name;
        setPhone(phone); // Use setter to validate, might throw InvalidPhoneNumberException
        setEmail(email); // Use setter to validate, might throw InvalidEmailFormatException
    }

    @Override
    public int compareTo(Person other) {
        return this.name.compareTo(other.name); // Compară numele alfabetic
    }
    // Getters
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
