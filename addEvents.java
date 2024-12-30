package org.example;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class addEvents {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/events_mangement";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "events_pro";



    public static void openNewPage(String title) {
        JFrame frame = new JFrame(title);

        frame.setSize(800, 700);

        // Labels and TextFields
        JLabel nameLabel = new JLabel("Event Name:");
        JTextField nameField = new JTextField(15);

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField(15);

        JLabel timeLabel = new JLabel("Time (HH:MM):");
        JTextField timeField = new JTextField(15);

        JLabel typeLabel = new JLabel("Type of Event:");
        JTextField typeField = new JTextField(15);

        JLabel organizerLabel = new JLabel("Organizer ID:");
        JTextField organizerField = new JTextField(15);

        JLabel customerLabel = new JLabel("Customer ID:");
        JTextField customerField = new JTextField(15);

        JLabel paymentLabel = new JLabel("Payment:");
        JTextField paymentField = new JTextField(15);

        JLabel statusLabel = new JLabel("Status:");
        JTextField statusField = new JTextField(15);

        JButton addButton = new JButton("Add Event");
        JButton viewButton = new JButton("View Events");

        JTextArea outputArea = new JTextArea(15, 50);
        outputArea.setEditable(false);

        // Layout setup
        JPanel inputPanel = new JPanel(new GridLayout(9, 2, 5, 5));
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(timeLabel);
        inputPanel.add(timeField);
        inputPanel.add(typeLabel);
        inputPanel.add(typeField);
        inputPanel.add(organizerLabel);
        inputPanel.add(organizerField);
        inputPanel.add(customerLabel);
        inputPanel.add(customerField);
        inputPanel.add(paymentLabel);
        inputPanel.add(paymentField);
        inputPanel.add(statusLabel);
        inputPanel.add(statusField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);

        JPanel outputPanel = new JPanel();
        outputPanel.add(new JScrollPane(outputArea));

        frame.setLayout(new BorderLayout());
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(outputPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // Action Listeners
        addButton.addActionListener(e -> {
            String name = nameField.getText();
            String date = dateField.getText();
            String time = timeField.getText();
            String type = typeField.getText();
            int organizer = Integer.parseInt(organizerField.getText());
            int customer = Integer.parseInt(customerField.getText());
            int payment = Integer.parseInt(paymentField.getText());
            String status = statusField.getText();

            addRecord(name, date, time, type, organizer, customer, payment, status, outputArea);
        });

        viewButton.addActionListener(e -> viewRecords(outputArea));
    }

    private static void addRecord(String name, String date, String time, String type, int organizer, int customer, int payment, String status, JTextArea outputArea) {
        String insertQuery = "INSERT INTO events (Name, Date, Time, Type_Of_Event, Organizer_ID, Customer_ID, Payment, Status) VALUES (?, TO_DATE(?, 'YYYY-MM-DD'), TO_TIMESTAMP(?, ' HH24:MI:SS'), ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {

            stmt.setString(1, name);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, type);
            stmt.setInt(5, organizer);
            stmt.setInt(6, customer);
            stmt.setInt(7, payment);
            stmt.setString(8, status);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                outputArea.setText("Event added successfully!\n");
            }
        } catch (SQLException e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }

    private static void viewRecords(JTextArea outputArea) {
        String selectQuery = "SELECT Event_ID, Name, Date, Time FROM events";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectQuery)) {

            StringBuilder records = new StringBuilder("Event_ID\tName\tDate\tTime\n");
            while (rs.next()) {
                records.append(rs.getInt("Event_ID")).append("\t")
                        .append(rs.getString("Name")).append("\t")
                        .append(rs.getDate("Date")).append("\t")
                        .append(rs.getTime("Time")).append("\n");
            }
            outputArea.setText(records.toString());
        } catch (SQLException e) {
            outputArea.setText("Error: " + e.getMessage());
        }
    }
}
