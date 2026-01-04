package com.reservation.dao;

import com.reservation.database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data Access Object for Train operations
 */
public class TrainDAO {
    private Connection connection;

    public TrainDAO() {
        connection = DatabaseConnection.getConnection();
    }

    /**
     * Get train name by train number
     * @param trainNumber
     * @return Train name or null if not found
     */
    public String getTrainName(String trainNumber) {
        String trainName = null;
        String query = "SELECT train_name FROM trains WHERE train_number = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, trainNumber);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                trainName = rs.getString("train_name");
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error getting train name: " + e.getMessage());
            e.printStackTrace();
        }
        return trainName;
    }

    /**
     * Check if train exists
     * @param trainNumber
     * @return true if train exists, false otherwise
     */
    public boolean trainExists(String trainNumber) {
        boolean exists = false;
        String query = "SELECT COUNT(*) FROM trains WHERE train_number = ?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, trainNumber);
            
            ResultSet rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            System.err.println("Error checking train existence: " + e.getMessage());
            e.printStackTrace();
        }
        return exists;
    }
}

