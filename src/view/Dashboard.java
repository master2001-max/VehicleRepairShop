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

        // Add buttons to layout
        add(orderBtn);
        add(empBtn);
        add(supplierBtn);
        add(inventoryBtn);
        add(reportBtn);
        add(logoutBtn);

        // Action Listeners
        orderBtn.addActionListener(e -> new OrderForm().setVisible(true));
        empBtn.addActionListener(e -> new EmployeeForm().setVisible(true));
        supplierBtn.addActionListener(e -> new SupplierForm().setVisible(true));
        inventoryBtn.addActionListener(e -> new InventoryForm().setVisible(true));
        reportBtn.addActionListener(e -> new ReportForm().setVisible(true)); // ✅ Reports working

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginForm().setVisible(true);
        });
    }
}
