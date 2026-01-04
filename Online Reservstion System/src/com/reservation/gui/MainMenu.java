package com.reservation.gui;

import com.reservation.gui.LoginForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Main Menu after successful login
 */
public class MainMenu extends JFrame {
    private JButton reservationButton, cancellationButton, logoutButton;

    public MainMenu() {
        initializeComponents();
        setupLayout();
        setupEventHandlers();
    }

    private void initializeComponents() {
        setTitle("Online Reservation System - Main Menu");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        reservationButton = new JButton("Reservation System");
        cancellationButton = new JButton("Cancellation Form");
        logoutButton = new JButton("Logout");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel welcomeLabel = new JLabel("Welcome to Online Reservation System");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(welcomeLabel);

        // Main content panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Reservation button
        reservationButton.setFont(new Font("Arial", Font.PLAIN, 16));
        reservationButton.setPreferredSize(new Dimension(300, 50));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(reservationButton, gbc);

        // Cancellation button
        cancellationButton.setFont(new Font("Arial", Font.PLAIN, 16));
        cancellationButton.setPreferredSize(new Dimension(300, 50));
        gbc.gridy = 1;
        mainPanel.add(cancellationButton, gbc);

        // Logout button
        JPanel logoutPanel = new JPanel(new FlowLayout());
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));
        logoutPanel.add(logoutButton);

        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(logoutPanel, BorderLayout.SOUTH);
    }

    private void setupEventHandlers() {
        reservationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ReservationForm().setVisible(true);
            }
        });

        cancellationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CancellationForm().setVisible(true);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int confirm = JOptionPane.showConfirmDialog(MainMenu.this, 
                    "Are you sure you want to logout?", "Logout", 
                    JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    MainMenu.this.dispose();
                    new LoginForm().setVisible(true);
                }
            }
        });
    }
}

