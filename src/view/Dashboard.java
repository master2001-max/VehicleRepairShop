package view;

import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dashboard extends JFrame {

    public Dashboard(User user) {
        setTitle("🚗 Vehicle Repair Shop - Dashboard");
        setSize(750, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Global UI settings
        UIManager.put("Button.focus", new Color(0, 0, 0, 0)); // Remove button focus border

        // Main container
        JPanel container = new JPanel(new BorderLayout(20, 20));
        container.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        container.setBackground(Color.WHITE);

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + user.getUsername() + " (" + user.getRole() + ")", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(new Color(33, 53, 85));
        container.add(welcomeLabel, BorderLayout.NORTH);

        // Center panel with grid
        JPanel gridPanel = new JPanel(new GridBagLayout());
        gridPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;

        // Create buttons
        String[] labels = {
                "Manage Customer Orders",
                "Manage Employees",
                "Manage Suppliers",
                "Manage Inventory",
                "View Reports",
                "Logout"
        };

        JButton[] buttons = new JButton[labels.length];
        for (int i = 0; i < labels.length; i++) {
            buttons[i] = createModernButton(labels[i]);
            gbc.gridx = i % 2;
            gbc.gridy = i / 2;
            gridPanel.add(buttons[i], gbc);
        }

        // Add center grid to container
        container.add(gridPanel, BorderLayout.CENTER);
        add(container);

        // Button actions
        buttons[0].addActionListener(e -> new OrderForm().setVisible(true));
        buttons[1].addActionListener(e -> new EmployeeForm().setVisible(true));
        buttons[2].addActionListener(e -> new SupplierForm().setVisible(true));
        buttons[3].addActionListener(e -> new InventoryForm().setVisible(true));
        buttons[4].addActionListener(e -> new ReportForm().setVisible(true));
        buttons[5].addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });
    }

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(50, 130, 184));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(280, 80));

        // Rounded look
        button.setContentAreaFilled(false);
        button.setOpaque(true);

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(30, 110, 160));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(50, 130, 184));
            }
        });

        return button;
    }
}
