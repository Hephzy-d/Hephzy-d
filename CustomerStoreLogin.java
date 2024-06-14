package register;

import javax.swing.*;

import database.LocalDatabase;
import customer.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerStoreLogin extends JFrame {
    LocalDatabase local = new LocalDatabase();

    private JTextField emailField;
    private JPasswordField passwordField;

    public CustomerStoreLogin() {
        setTitle("Customer Store Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome to Customer Store", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(welcomeLabel, BorderLayout.NORTH);

        // Form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        formPanel.setBackground(Color.LIGHT_GRAY);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        formPanel.add(emailLabel);

        emailField = new JTextField();
        formPanel.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        formPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        JButton loginButton = new JButton("Login");
        formPanel.add(loginButton);

        JButton createButton = new JButton("Create Account");
        formPanel.add(createButton);

        add(formPanel, BorderLayout.CENTER);

        // Forgotten password button
        JButton forgottenPasswordButton = new JButton("Forgotten Password?");
        forgottenPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Implement forgotten password functionality
                JOptionPane.showMessageDialog(CustomerStoreLogin.this,
                        "Forgotten Password functionality not implemented yet.");
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(forgottenPasswordButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());

                if (isValidCustomer(email, password)) {
                    JOptionPane.showMessageDialog(CustomerStoreLogin.this, "Login Successful.");
                    setVisible(false);
                    new ProductGridView().setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(CustomerStoreLogin.this,
                            "Illegal Entry. Please check your credentials.");
                    clearFields();
                }
            }
        });

        createButton.addActionListener(new ActionListener() {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            @Override
            public void actionPerformed(ActionEvent e) {
                local.createTable("customer", "id INTEGER PRIMARY KEY, email TEXT, password TEXT");
                local.insert("customer", "email, password", "'" + email + "', '" + password + "'");
                setVisible(false);
                new ProductGridView().setVisible(true);
                JOptionPane.showMessageDialog(CustomerStoreLogin.this,
                        "Create Account functionality not implemented yet.");
            }
        });
    }

    private boolean isValidCustomer(String email, String password) {
        String url = "jdbc:sqlite:./bin/local_db.sqlite";
        String query = "SELECT * FROM customer WHERE email = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void clearFields() {
        emailField.setText("");
        passwordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CustomerStoreLogin().setVisible(true);
            }
        });
    }
}
