package org.example;

import javax.swing.*;
import java.awt.*;

import static org.example.addEvents.openNewPage;
public class secondPage {
public secondPage(String title) {

    JFrame frame = new JFrame("Event Management");

    frame.setSize(800, 600);

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setBackground(new Color(30, 144, 255));

    JLabel header = new JLabel("Event Management", SwingConstants.CENTER);
    header.setFont(new Font("Arial",Font.BOLD, 28));
    header.setForeground(Color.WHITE);
    mainPanel.add(header, BorderLayout.NORTH);

    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));
    buttonPanel.setBackground(new Color(240, 248, 255));

    JButton addEvents = new JButton("Add Events");
    JButton showEvents = new JButton("Show Events");
    JButton editEvents = new JButton("Edit Events");
    JButton deleteEvents = new JButton("delete Events");
    JButton backButton = new JButton("Back");

    buttonPanel.add(addEvents);
    buttonPanel.add(showEvents);
    buttonPanel.add(editEvents);
    buttonPanel.add(deleteEvents);
    buttonPanel.add(backButton);

    mainPanel.add(buttonPanel, BorderLayout.CENTER);

    frame.add(mainPanel);
    frame.setVisible(true);

    // Add action listeners

    backButton.addActionListener(e -> new homePage());
    addEvents.addActionListener(e -> openNewPage("Events Page"));


}
}
