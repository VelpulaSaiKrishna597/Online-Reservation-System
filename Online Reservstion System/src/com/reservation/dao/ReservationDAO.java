package com.reservation.dao;

import com.reservation.database.DatabaseConnection;
import com.reservation.model.Reservation;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

/**
 * Data Access Object for Reservation operations
 */
public class ReservationDAO {
    private Connection connection;

    public ReservationDAO() {
        connection = DatabaseConnection.getConnection();
    }

    /**
     * Generate unique PNR number
     * @return PNR number
     */
    private String generatePNR() {
        Random random = new Random();
        String pnr = "PNR" + String.format("%06d", random.nextInt(999999));
        
        // Ensure PNR is unique
        while (pnrExists(pnr)) {
            pnr = "PNR" + String.format("%06d", random.nextInt(999999));
        }
        return pnr;
    }

    /**
     * Check if PNR exists
     * @param pnr
     * @return true if exists, false otherwise
     */
    private boolean pnrExists(String pnr) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM reservations WHERE pnr_number = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pnr);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error checking PNR: " + e.getMessage());
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * Create new reservation
     * @param reservation
     * @return Generated PNR number
     */
    public String createReservation(Reservation reservation) {
        String pnr = generatePNR();
        String query = "INSERT INTO reservations (pnr_number, user_id, passenger_name, passenger_age, " +
                      "passenger_gender, passenger_phone, passenger_email, train_number, train_name, " +
                      "class_type, journey_date, from_station, to_station, seat_number, status) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pnr);
            ps.setInt(2, reservation.getUserId());
            ps.setString(3, reservation.getPassengerName());
            ps.setInt(4, reservation.getPassengerAge());
            ps.setString(5, reservation.getPassengerGender());
            ps.setString(6, reservation.getPassengerPhone());
            ps.setString(7, reservation.getPassengerEmail());
            ps.setString(8, reservation.getTrainNumber());
            ps.setString(9, reservation.getTrainName());
            ps.setString(10, reservation.getClassType());
            ps.setDate(11, reservation.getJourneyDate());
            ps.setString(12, reservation.getFromStation());
            ps.setString(13, reservation.getToStation());
            ps.setString(14, "S" + (int)(Math.random() * 100));
            ps.setString(15, "CONFIRMED");
            
            int result = ps.executeUpdate();
            ps.close();
            
            if (result > 0) {
                return pnr;
            }
        } catch (SQLException e) {
            System.err.println("Error creating reservation: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get reservation by PNR number
     * @param pnrNumber
     * @return Reservation object or null if not found
     */
    public Reservation getReservationByPNR(String pnrNumber) {
        Reservation reservation = null;
        String query = "SELECT * FROM reservations WHERE pnr_number = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pnrNumber);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                reservation = new Reservation();
                reservation.setPnrNumber(rs.getString("pnr_number"));
                reservation.setUserId(rs.getInt("user_id"));
                reservation.setPassengerName(rs.getString("passenger_name"));
                reservation.setPassengerAge(rs.getInt("passenger_age"));
                reservation.setPassengerGender(rs.getString("passenger_gender"));
                reservation.setPassengerPhone(rs.getString("passenger_phone"));
                reservation.setPassengerEmail(rs.getString("passenger_email"));
                reservation.setTrainNumber(rs.getString("train_number"));
                reservation.setTrainName(rs.getString("train_name"));
                reservation.setClassType(rs.getString("class_type"));
                reservation.setJourneyDate(rs.getDate("journey_date"));
                reservation.setFromStation(rs.getString("from_station"));
                reservation.setToStation(rs.getString("to_station"));
                reservation.setSeatNumber(rs.getString("seat_number"));
                reservation.setStatus(rs.getString("status"));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error getting reservation: " + e.getMessage());
            e.printStackTrace();
        }
        return reservation;
    }

    /**
     * Cancel reservation
     * @param pnrNumber
     * @return true if cancelled successfully, false otherwise
     */
    public boolean cancelReservation(String pnrNumber) {
        String query = "UPDATE reservations SET status = 'CANCELLED' WHERE pnr_number = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, pnrNumber);
            
            int result = ps.executeUpdate();
            ps.close();
            
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error cancelling reservation: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}

