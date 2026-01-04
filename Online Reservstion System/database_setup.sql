-- Online Reservation System Database Setup
-- Create Database
CREATE DATABASE IF NOT EXISTS reservation_system;
USE reservation_system;

-- Users table for login authentication
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trains table
CREATE TABLE IF NOT EXISTS trains (
    train_number VARCHAR(20) PRIMARY KEY,
    train_name VARCHAR(100) NOT NULL,
    source_station VARCHAR(100) NOT NULL,
    destination_station VARCHAR(100) NOT NULL,
    total_seats INT DEFAULT 100,
    available_seats INT DEFAULT 100
);

-- Reservations table
CREATE TABLE IF NOT EXISTS reservations (
    pnr_number VARCHAR(20) PRIMARY KEY,
    user_id INT,
    passenger_name VARCHAR(100) NOT NULL,
    passenger_age INT NOT NULL,
    passenger_gender VARCHAR(10) NOT NULL,
    passenger_phone VARCHAR(15),
    passenger_email VARCHAR(100),
    train_number VARCHAR(20) NOT NULL,
    train_name VARCHAR(100) NOT NULL,
    class_type VARCHAR(20) NOT NULL,
    journey_date DATE NOT NULL,
    from_station VARCHAR(100) NOT NULL,
    to_station VARCHAR(100) NOT NULL,
    seat_number VARCHAR(10),
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'CONFIRMED',
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (train_number) REFERENCES trains(train_number) ON DELETE CASCADE
);

-- Insert sample users
INSERT INTO users (username, password, full_name, email) VALUES
('admin', 'admin123', 'Administrator', 'admin@reservation.com'),
('user1', 'user123', 'John Doe', 'john@example.com'),
('user2', 'user123', 'Jane Smith', 'jane@example.com');

-- Insert sample trains
INSERT INTO trains (train_number, train_name, source_station, destination_station, total_seats, available_seats) VALUES
('12345', 'Express Train', 'New York', 'Boston', 100, 85),
('12346', 'Fast Train', 'Boston', 'New York', 100, 90),
('12347', 'Super Express', 'Chicago', 'Detroit', 100, 75),
('12348', 'Metro Train', 'Detroit', 'Chicago', 100, 95),
('12349', 'City Express', 'Los Angeles', 'San Francisco', 100, 80);

