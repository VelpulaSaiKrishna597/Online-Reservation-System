# Online Reservation System

A complete Java-based Online Reservation System with login authentication, reservation booking, and ticket cancellation features.

## Features

### 1. Login Form
- Secure user authentication with username and password
- Validates credentials against database
- Redirects to main menu upon successful login

### 2. Reservation System
- Complete reservation form with all necessary fields:
  - Passenger basic details (Name, Age, Gender, Phone, Email)
  - Train Number (auto-fills Train Name)
  - Class Type selection
  - Date of Journey
  - From and To stations
- Generates unique PNR number upon successful booking
- Validates all required fields before submission

### 3. Cancellation Form
- Search reservation by PNR number
- Displays complete reservation details
- Confirms cancellation with OK button
- Updates reservation status to CANCELLED

## Technology Stack

- **Language**: Java
- **GUI Framework**: Java Swing
- **Database**: MySQL
- **JDBC**: MySQL Connector/J

## Prerequisites

1. **Java Development Kit (JDK)** 8 or higher
2. **MySQL Server** 5.7 or higher
3. **MySQL Connector/J** (JDBC driver)

## Database Setup

1. Install and start MySQL server
2. Open MySQL command line or MySQL Workbench
3. Run the `database_setup.sql` script to create the database and tables:

```bash
mysql -u root -p < database_setup.sql
```

Or manually execute the SQL commands in the `database_setup.sql` file.

## Configuration

Update the database connection settings in `src/com/reservation/database/DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/reservation_system";
private static final String DB_USERNAME = "root";
private static final String DB_PASSWORD = ""; // Your MySQL password
```

## Compilation and Execution

### Using Command Line:

1. **Compile the project:**
```bash
javac -cp "mysql-connector-java-8.0.33.jar:." src/com/reservation/**/*.java
```

2. **Run the application:**
```bash
java -cp "mysql-connector-java-8.0.33.jar:." com.reservation.gui.LoginForm
```

### Using IDE (Eclipse/IntelliJ IDEA):

1. Create a new Java project
2. Add MySQL Connector/J JAR file to the project's classpath
3. Import all source files from the `src` directory
4. Run `LoginForm.java` as the main class

## Default Login Credentials

The database setup script includes sample users:

- **Username**: `admin`, **Password**: `admin123`
- **Username**: `user1`, **Password**: `user123`
- **Username**: `user2`, **Password**: `user123`

## Sample Train Numbers

The database includes sample trains:

- `12345` - Express Train
- `12346` - Fast Train
- `12347` - Super Express
- `12348` - Metro Train
- `12349` - City Express

## Project Structure

```
Online Reservation System/
├── database_setup.sql          # Database schema and sample data
├── src/
│   └── com/
│       └── reservation/
│           ├── database/
│           │   └── DatabaseConnection.java
│           ├── model/
│           │   ├── User.java
│           │   └── Reservation.java
│           ├── dao/
│           │   ├── UserDAO.java
│           │   ├── TrainDAO.java
│           │   └── ReservationDAO.java
│           └── gui/
│               ├── LoginForm.java
│               ├── MainMenu.java
│               ├── ReservationForm.java
│               └── CancellationForm.java
└── README.md
```

## Usage

1. **Start the application** by running `LoginForm.java`
2. **Login** with valid credentials
3. **Access Main Menu** with options for:
   - Reservation System
   - Cancellation Form
4. **Book a ticket** by filling the reservation form
5. **Cancel a ticket** by entering the PNR number

## Features Implementation

- ✅ Login authentication
- ✅ Reservation booking with all required fields
- ✅ Auto-fill train name from train number
- ✅ PNR number generation
- ✅ Cancellation by PNR lookup
- ✅ Complete reservation details display
- ✅ Form validation
- ✅ User-friendly GUI

## Notes

- Make sure MySQL server is running before starting the application
- Ensure the MySQL Connector/J JAR file is in the classpath
- The application uses Swing for GUI, which is included in standard JDK
- All dates should be entered in `yyyy-mm-dd` format

## License

This project is created for educational purposes.

