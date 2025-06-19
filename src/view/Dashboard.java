package view;

import model.User;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard(User user) {
        setTitle("Dashboard - Logged in as " + user.getUsername() + " (" + user.getRole() + ")");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        // Buttons
        JButton orderBtn = new JButton("🧾 Manage Customer Orders");
        JButton empBtn = new JButton("👨‍🔧 Manage Employees");
        JButton supplierBtn = new JButton("🏷️ Manage Suppliers");
        JButton inventoryBtn = new JButton("📦 Manage Inventory");
        JButton reportBtn = new JButton("📊 View Reports");
        JButton logoutBtn = new JButton("🚪 Logout");

        // Add buttons to window
        add(orderBtn);
        add(empBtn);
        add(supplierBtn);
        add(inventoryBtn);
        add(reportBtn);
        add(logoutBtn);

        // Functional actions
        orderBtn.addActionListener(e -> new OrderForm().setVisible(true));
        empBtn.addActionListener(e -> new EmployeeForm().setVisible(true));
        supplierBtn.addActionListener(e -> new SupplierForm().setVisible(true));

        // Coming soon placeholders
        inventoryBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "📦 Inventory module coming soon!"));
        reportBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "📊 Report module coming soon!"));

        // Logout
        logoutBtn.addActionListener(e -> {
            dispose(); // close dashboard
            new LoginForm().setVisible(true); // go back to login
        });
    }
}
