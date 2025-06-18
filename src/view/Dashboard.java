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

        JButton orderBtn = new JButton("🧾 Manage Customer Orders");
        JButton empBtn = new JButton("👨‍🔧 Manage Employees");
        JButton supplierBtn = new JButton("🏷️ Manage Suppliers");
        JButton inventoryBtn = new JButton("📦 Manage Inventory");
        JButton reportBtn = new JButton("📊 View Reports");
        JButton logoutBtn = new JButton("🚪 Logout");

        add(orderBtn);
        add(empBtn);
        add(supplierBtn);
        add(inventoryBtn);
        add(reportBtn);
        add(logoutBtn);

        // Button Actions
        orderBtn.addActionListener(e -> new OrderForm().setVisible(true));
        supplierBtn.addActionListener(e -> new SupplierForm().setVisible(true));

        // Placeholder actions
        empBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Employee Management coming soon!"));
        inventoryBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Inventory module coming soon!"));
        reportBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Report module coming soon!"));

        logoutBtn.addActionListener(e -> {
            dispose(); // close dashboard
            new LoginForm().setVisible(true); // go back to login
        });
    }
}
