package com.reservation.gui;

import com.reservation.dao.ReservationDAO;
import com.reservation.model.Reservation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

/**
 * Cancellation Form for cancelling tickets
 */
public class CancellationForm extends JFrame {
    private JTextField pnrField;
    private JTextArea detailsArea;
    private JButton searchButton, okButton, cancelButton;
    private ReservationDAO reservationDAO;
    private Reservation currentReservation;

    public CancellationForm() {
        reservationDAO = new ReservationDAO();
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        setTitle("Online Reservation System - Cancellation Form");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        pnrField = new JTextField(20);
        detailsArea = new JTextArea(15, 40);
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        detailsArea.setBorder(BorderFactory.createTitledBorder("Reservation Details"));

        searchButton = new JButton("Search");
        okButton = new JButton("OK (Confirm Cancellation)");
        okButton.setEnabled(false);
        cancelButton = new JButton("Cancel");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Title
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Cancellation Form");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // PNR input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Enter PNR Number:"));
        inputPanel.add(pnrField);
        inputPanel.add(searchButton);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Details panel
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        JScrollPane scrollPane = new JScrollPane(detailsArea);
        detailsPanel.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Combine input and details in center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(detailsPanel, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchReservation();
            }
        });

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmCancellation();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CancellationForm.this.dispose();
            }
        });

        // Enter key on PNR field triggers search
        pnrField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchReservation();
            }
        });
    }

    private void searchReservation() {
        String pnr = pnrField.getText().trim().toUpperCase();
        
        if (pnr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a PNR number!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        currentReservation = reservationDAO.getReservationByPNR(pnr);
        
        if (currentReservation != null) {
            displayReservationDetails(currentReservation);
            okButton.setEnabled(true);
        } else {
            detailsArea.setText("No reservation found with PNR: " + pnr);
            okButton.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Reservation not found!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayReservationDetails(Reservation reservation) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder details = new StringBuilder();
        
        details.append("═══════════════════════════════════════════════════\n");
        details.append("           RESERVATION DETAILS\n");
        details.append("═══════════════════════════════════════════════════\n\n");
        details.append("PNR Number      : ").append(reservation.getPnrNumber()).append("\n");
        details.append("Status          : ").append(reservation.getStatus()).append("\n\n");
        details.append("Passenger Details:\n");
        details.append("───────────────────────────────────────────────────\n");
        details.append("Name            : ").append(reservation.getPassengerName()).append("\n");
        details.append("Age             : ").append(reservation.getPassengerAge()).append("\n");
        details.append("Gender          : ").append(reservation.getPassengerGender()).append("\n");
        details.append("Phone           : ").append(reservation.getPassengerPhone()).append("\n");
        details.append("Email           : ").append(reservation.getPassengerEmail()).append("\n\n");
        details.append("Journey Details:\n");
        details.append("───────────────────────────────────────────────────\n");
        details.append("Train Number    : ").append(reservation.getTrainNumber()).append("\n");
        details.append("Train Name      : ").append(reservation.getTrainName()).append("\n");
        details.append("Class Type      : ").append(reservation.getClassType()).append("\n");
        details.append("Journey Date    : ").append(dateFormat.format(reservation.getJourneyDate())).append("\n");
        details.append("From            : ").append(reservation.getFromStation()).append("\n");
        details.append("To              : ").append(reservation.getToStation()).append("\n");
        details.append("Seat Number     : ").append(reservation.getSeatNumber()).append("\n");
        details.append("═══════════════════════════════════════════════════\n");
        
        detailsArea.setText(details.toString());
    }

    private void confirmCancellation() {
        if (currentReservation == null) {
            JOptionPane.showMessageDialog(this, "Please search for a reservation first!", 
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if ("CANCELLED".equals(currentReservation.getStatus())) {
            JOptionPane.showMessageDialog(this, "This reservation is already cancelled!", 
                                        "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to cancel this reservation?\nPNR: " + currentReservation.getPnrNumber(), 
            "Confirm Cancellation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = reservationDAO.cancelReservation(currentReservation.getPnrNumber());
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Reservation cancelled successfully!\nPNR: " + currentReservation.getPnrNumber(), 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                currentReservation.setStatus("CANCELLED");
                displayReservationDetails(currentReservation);
                okButton.setEnabled(false);
                pnrField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Cancellation failed! Please try again.", 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

