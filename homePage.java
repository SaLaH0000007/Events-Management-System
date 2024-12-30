package org.example;

import javax.swing.*;
import java.awt.*;

public class homePage {
    public homePage() {

        JFrame frame = new JFrame("Event Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(30, 144, 255));

        JLabel header = new JLabel("Event Management System", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 28));
        header.setForeground(Color.WHITE);
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));

        JButton eventButton = new JButton("Events");
        JButton participantsButton = new JButton("Participants");
        JButton employeesButton = new JButton("Employees");
        JButton manageButton = new JButton("Manage");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(eventButton);
        buttonPanel.add(participantsButton);
        buttonPanel.add(employeesButton);
        buttonPanel.add(manageButton);
        buttonPanel.add(exitButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setVisible(true);

        // Add action listeners
        exitButton.addActionListener(e -> System.exit(0));
        eventButton.addActionListener(e -> new secondPage("Events Page"));



    }
}
