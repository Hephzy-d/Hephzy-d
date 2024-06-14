package register;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountTypeSelection extends JFrame {

    private JRadioButton customerRadioButton;
    private JRadioButton adminRadioButton;

    public AccountTypeSelection() {
        setTitle("Account Type");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.LIGHT_GRAY);

        // Title label
        JLabel titleLabel = new JLabel("Select Account Type");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        // Radio buttons panel
        JPanel radioPanel = new JPanel(new GridLayout(2, 1));
        radioPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        radioPanel.setBackground(Color.LIGHT_GRAY);

        customerRadioButton = new JRadioButton("Customer");
        adminRadioButton = new JRadioButton("Admin");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(customerRadioButton);
        buttonGroup.add(adminRadioButton);

        radioPanel.add(customerRadioButton);
        radioPanel.add(adminRadioButton);

        add(radioPanel, BorderLayout.CENTER);

        // OK button
        JButton okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (customerRadioButton.isSelected()) {
                    setVisible(false);
                    new CustomerStoreLogin().setVisible(true);
                    JOptionPane.showMessageDialog(AccountTypeSelection.this, "Customer selected");
                } else if (adminRadioButton.isSelected()) {
                    setVisible(false);
                    new AdminLogin().setVisible(true);
                    JOptionPane.showMessageDialog(AccountTypeSelection.this, "Admin selected");
                } else {
                    JOptionPane.showMessageDialog(AccountTypeSelection.this, "Please select an account type");
                }
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(okButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AccountTypeSelection().setVisible(true);
            }
        });
    }
}
