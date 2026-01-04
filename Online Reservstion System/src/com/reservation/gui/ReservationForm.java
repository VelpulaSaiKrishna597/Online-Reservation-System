package com.reservation.gui;

import com.reservation.dao.ReservationDAO;
import com.reservation.dao.TrainDAO;
import com.reservation.model.Reservation;
import com.reservation.gui.LoginForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Reservation Form for booking tickets
 */
public class ReservationForm extends JFrame {
    private JTextField passengerNameField, passengerAgeField, passengerPhoneField, 
                       passengerEmailField, trainNumberField, trainNameField, 
                       fromStationField, toStationField;
    private JComboBox<String> classTypeCombo, genderCombo;
    private JFormattedTextField journeyDateField;
    private JButton insertButton, clearButton, cancelButton;
    private ReservationDAO reservationDAO;
    private TrainDAO trainDAO;

    public ReservationForm() {
        reservationDAO = new ReservationDAO();
        trainDAO = new TrainDAO();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        setTitle("Online Reservation System - Reservation Form");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        passengerNameField = new JTextField(20);
        passengerAgeField = new JTextField(20);
        passengerPhoneField = new JTextField(20);
        passengerEmailField = new JTextField(20);
        trainNumberField = new JTextField(20);
        trainNameField = new JTextField(20);
        trainNameField.setEditable(false);
        trainNameField.setBackground(Color.LIGHT_GRAY);
        fromStationField = new JTextField(20);
        toStationField = new JTextField(20);

        String[] classTypes = {"First Class", "Second Class", "Third Class", "Sleeper", "AC"};
        classTypeCombo = new JComboBox<>(classTypes);

        String[] genders = {"Male", "Female", "Other"};
        genderCombo = new JComboBox<>(genders);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        journeyDateField = new JFormattedTextField(dateFormat);
        journeyDateField.setColumns(20);

        insertButton = new JButton("Insert");
        clearButton = new JButton("Clear");
        cancelButton = new JButton("Cancel");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Reservation Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;

        // Passenger Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Passenger Name *:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passengerNameField, gbc);

        // Passenger Age
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Passenger Age *:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passengerAgeField, gbc);

        // Passenger Gender
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Gender *:"), gbc);
        gbc.gridx = 1;
        formPanel.add(genderCombo, gbc);

        // Passenger Phone
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passengerPhoneField, gbc);

        // Passenger Email
        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(passengerEmailField, gbc);

        // Train Number
        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(new JLabel("Train Number *:"), gbc);
        gbc.gridx = 1;
        formPanel.add(trainNumberField, gbc);

        // Train Name (auto-filled)
        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(new JLabel("Train Name:"), gbc);
        gbc.gridx = 1;
        formPanel.add(trainNameField, gbc);

        // Class Type
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(new JLabel("Class Type *:"), gbc);
        gbc.gridx = 1;
        formPanel.add(classTypeCombo, gbc);

        // Journey Date
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(new JLabel("Date of Journey *:"), gbc);
        gbc.gridx = 1;
        formPanel.add(journeyDateField, gbc);

        // From Station
        gbc.gridx = 0;
        gbc.gridy = 9;
        formPanel.add(new JLabel("From (Place) *:"), gbc);
        gbc.gridx = 1;
        formPanel.add(fromStationField, gbc);

        // To Station
        gbc.gridx = 0;
        gbc.gridy = 10;
        formPanel.add(new JLabel("To (Destination) *:"), gbc);
        gbc.gridx = 1;
        formPanel.add(toStationField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(insertButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(cancelButton);

        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        // Auto-fill train name when train number loses focus
        trainNumberField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                String trainNumber = trainNumberField.getText().trim();
                if (!trainNumber.isEmpty()) {
                    if (trainDAO.trainExists(trainNumber)) {
                        String trainName = trainDAO.getTrainName(trainNumber);
                        trainNameField.setText(trainName);
                    } else {
                        trainNameField.setText("");
                        JOptionPane.showMessageDialog(ReservationForm.this, 
                            "Train number not found!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performReservation();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReservationForm.this.dispose();
            }
        });
    }

    private void performReservation() {
        // Validate required fields
        if (passengerNameField.getText().trim().isEmpty() ||
            passengerAgeField.getText().trim().isEmpty() ||
            trainNumberField.getText().trim().isEmpty() ||
            trainNameField.getText().trim().isEmpty() ||
            journeyDateField.getText().trim().isEmpty() ||
            fromStationField.getText().trim().isEmpty() ||
            toStationField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate age
        int age;
        try {
            age = Integer.parseInt(passengerAgeField.getText().trim());
            if (age <= 0 || age > 120) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate date
        Date journeyDate;
        try {
            journeyDate = Date.valueOf(journeyDateField.getText().trim());
            if (journeyDate.before(new Date(System.currentTimeMillis()))) {
                JOptionPane.showMessageDialog(this, "Journey date cannot be in the past!", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid date (yyyy-mm-dd)!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create reservation object
        Reservation reservation = new Reservation();
        reservation.setUserId(LoginForm.getCurrentUser().getUserId());
        reservation.setPassengerName(passengerNameField.getText().trim());
        reservation.setPassengerAge(age);
        reservation.setPassengerGender(genderCombo.getSelectedItem().toString());
        reservation.setPassengerPhone(passengerPhoneField.getText().trim());
        reservation.setPassengerEmail(passengerEmailField.getText().trim());
        reservation.setTrainNumber(trainNumberField.getText().trim());
        reservation.setTrainName(trainNameField.getText().trim());
        reservation.setClassType(classTypeCombo.getSelectedItem().toString());
        reservation.setJourneyDate(journeyDate);
        reservation.setFromStation(fromStationField.getText().trim());
        reservation.setToStation(toStationField.getText().trim());

        // Insert reservation
        String pnr = reservationDAO.createReservation(reservation);
        if (pnr != null) {
            JOptionPane.showMessageDialog(this, 
                "Reservation successful!\nPNR Number: " + pnr, 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Reservation failed! Please try again.", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        passengerNameField.setText("");
        passengerAgeField.setText("");
        passengerPhoneField.setText("");
        passengerEmailField.setText("");
        trainNumberField.setText("");
        trainNameField.setText("");
        fromStationField.setText("");
        toStationField.setText("");
        journeyDateField.setText("");
        classTypeCombo.setSelectedIndex(0);
        genderCombo.setSelectedIndex(0);
    }
}

