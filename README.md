## Classes and Their Responsibilities

### Entities

1. **Person (abstract)**
    - Attributes: ID, name, phone, email
    - Description: Base class for individuals involved in the medical office system.

2. **Medic (extends Person)**
    - Attributes: specialty, available hours
    - Subclasses: `GeneralPractitioner`, `Surgeon`
    - CSV Storage: Yes
    - Description: Represents medical professionals. Specializations handle specific medical fields.

3. **Client (extends Person)**
    - Attributes: medicalHistory
    - CSV Storage: Yes
    - Description: Individuals seeking medical services.

4. **Appointment (abstract)**
    - Attributes: ID, clientID, medicID, appointmentDate, status
    - Subclasses: `RegularAppointment`, `OneTimeAppointment`
    - CSV Storage: Yes
    - Description: Meetings scheduled between a client and a medic. Can be either regular or one-time.

5. **Specialty**
    - Attributes: specialtyID, name, description
    - CSV Storage: No
    - Description: Medical specialties available in the office.

6. **Schedule**
    - Attributes: medicID, workingDays, workingHours
    - CSV Storage: Yes
    - Description: Defines the availability of medics.

7. **MedicalRecord**
    - Attributes: recordID, clientID, visitDate, notes
    - CSV Storage: Yes
    - Description: Records of client visits and treatments.

### Specialized Classes

#### Medic Subclasses

- **General Practitioner**
    - Description: Focuses on general healthcare, preventive care, and diagnosing and treating common illnesses.

- **Surgeon**
    - Description: Specializes in performing surgical operations.

#### Appointment Subclasses

- **Regular Appointment**
    - Additional Attributes: frequency
    - Description: Appointments that occur on a regular basis, such as follow-up checks or ongoing treatment sessions.

- **OneTime Appointment**
    - Description: A single, standalone appointment for consultation, diagnosis, or treatment.

## Storage

- **CSV File Integration**: The system utilizes CSV files for persistent storage, particularly for entities like `Medic`, `Client`, `Appointment`, and `Schedule`. The design considers the subclass specifics where applicable for `Medic` and `Appointment` types.
## Service Architecture

This section outlines the design and function of the services within the medical office scheduling system. These services are responsible for the core business logic of the system, managing interactions between the system's entities and external resources, such as CSV files for data persistence.

### Core Services

The system includes several key services, each dedicated to managing a specific aspect of the system:

- **ClientService**: Manages operations related to clients, including registration, updating, and retrieving client information.
- **MedicService**: Handles medic profiles, including adding new medics, updating existing profiles, and managing medic specialties.
- **AppointmentService**: Oversees the scheduling, updating, and cancellation of appointments. This service works with both regular and one-time appointments.
- **ScheduleService**: Manages medic schedules, ensuring that appointments are booked based on availability.

### Singleton Services

To ensure efficient management of shared resources and centralize business logic, certain services are implemented as singletons. This means that only one instance of each service exists during the application's lifecycle, providing a global point of access.

#### FileService: A Singleton Example

`FileService` is implemented as a generic singleton service for reading from and writing to CSV files. This service is critical for data persistence, loading data at startup, and saving updates as they occur.
