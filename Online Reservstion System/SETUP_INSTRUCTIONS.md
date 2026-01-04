# Setup Instructions for Online Reservation System

## Step 1: Install Prerequisites

### 1.1 Install Java JDK
- Download and install JDK 8 or higher from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- Verify installation:
  ```bash
  java -version
  javac -version
  ```

### 1.2 Install MySQL
- Download and install MySQL Server from [MySQL Downloads](https://dev.mysql.com/downloads/mysql/)
- During installation, remember the root password you set
- Verify installation:
  ```bash
  mysql --version
  ```

### 1.3 Download MySQL Connector/J
- Download MySQL Connector/J from [MySQL Connector Downloads](https://dev.mysql.com/downloads/connector/j/)
- Extract the JAR file (e.g., `mysql-connector-java-8.0.33.jar`)
- Create a `lib` folder in the project root
- Copy the JAR file to the `lib` folder

## Step 2: Database Setup

### 2.1 Start MySQL Server
- Start MySQL service (varies by OS):
  - Windows: Services → MySQL → Start
  - Linux: `sudo systemctl start mysql`
  - Mac: `brew services start mysql`

### 2.2 Create Database
1. Open MySQL command line or MySQL Workbench
2. Connect as root user
3. Run the SQL script:
   ```bash
   mysql -u root -p < database_setup.sql
   ```
   
   Or manually:
   ```sql
   source database_setup.sql;
   ```

### 2.3 Verify Database
```sql
USE reservation_system;
SHOW TABLES;
SELECT * FROM users;
SELECT * FROM trains;
```

## Step 3: Configure Database Connection

Edit `src/com/reservation/database/DatabaseConnection.java`:

```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/reservation_system";
private static final String DB_USERNAME = "root";
private static final String DB_PASSWORD = "your_mysql_password"; // Change this!
```

## Step 4: Compile the Project

### Option A: Using build.bat (Windows)
```bash
build.bat
```

### Option B: Manual Compilation
```bash
# Create bin directory
mkdir bin

# Compile (adjust JAR filename if different)
javac -d bin -cp "lib/mysql-connector-java-8.0.33.jar" src/com/reservation/**/*.java
```

### Option C: Using IDE
1. Open project in Eclipse/IntelliJ IDEA
2. Add MySQL Connector/J JAR to project classpath
3. Build the project

## Step 5: Run the Application

### Option A: Using run.bat (Windows)
```bash
run.bat
```

### Option B: Manual Execution
```bash
java -cp "bin;lib/mysql-connector-java-8.0.33.jar" com.reservation.gui.LoginForm
```

### Option C: Using IDE
- Run `LoginForm.java` as the main class

## Step 6: Test the Application

1. **Login Test:**
   - Username: `admin`
   - Password: `admin123`

2. **Reservation Test:**
   - Train Number: `12345`
   - Fill all required fields
   - Click Insert

3. **Cancellation Test:**
   - Use the PNR number from a successful reservation
   - Search and cancel

## Troubleshooting

### Database Connection Error
- Verify MySQL server is running
- Check username and password in `DatabaseConnection.java`
- Ensure database `reservation_system` exists
- Check MySQL port (default: 3306)

### ClassNotFoundException: com.mysql.cj.jdbc.Driver
- Ensure MySQL Connector/J JAR is in the `lib` folder
- Verify JAR is in the classpath when compiling and running

### Compilation Errors
- Ensure JDK is properly installed
- Check all source files are in correct package structure
- Verify classpath includes MySQL Connector/J

### GUI Not Displaying
- Ensure you're running on a system with GUI support
- Check Java version compatibility

## Project Structure After Setup

```
Online Reservation System/
├── database_setup.sql
├── src/
│   └── com/reservation/...
├── lib/
│   └── mysql-connector-java-8.0.33.jar
├── bin/ (created after compilation)
├── build.bat
├── run.bat
└── README.md
```

## Default Credentials

**Users:**
- admin / admin123
- user1 / user123
- user2 / user123

**Sample Trains:**
- 12345 - Express Train
- 12346 - Fast Train
- 12347 - Super Express
- 12348 - Metro Train
- 12349 - City Express

