package view;

import controller.OrderController;
import model.Order;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class OrderForm extends JFrame {
    private JTextField nameField, contactField, modelField, problemField, costField;
    private JComboBox<String> statusBox;
    private JTable orderTable;
    private DefaultTableModel tableModel;

    public OrderForm() {
        setTitle("Customer Order Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top form
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        contactField = new JTextField();
        modelField = new JTextField();
        problemField = new JTextField();
        costField = new JTextField();
        statusBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Ready"});

        formPanel.add(new JLabel("Customer Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Contact:")); formPanel.add(contactField);
        formPanel.add(new JLabel("Vehicle Model:")); formPanel.add(modelField);
        formPanel.add(new JLabel("Problem Description:")); formPanel.add(problemField);
        formPanel.add(new JLabel("Total Cost:")); formPanel.add(costField);
        formPanel.add(new JLabel("Order Status:")); formPanel.add(statusBox);

        JButton addButton = new JButton("Add Order");
        JButton updateButton = new JButton("Update Order");
        JButton deleteButton = new JButton("Delete Order");

        addButton.addActionListener(this::addOrder);
        updateButton.addActionListener(this::updateOrder);
        deleteButton.addActionListener(this::deleteOrder);

        formPanel.add(addButton);
        formPanel.add(updateButton);
        formPanel.add(deleteButton);

        add(formPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{
                "ID", "Customer", "Contact", "Vehicle", "Problem", "Cost", "Status"
        }, 0);
        orderTable = new JTable(tableModel);
        add(new JScrollPane(orderTable), BorderLayout.CENTER);

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JComboBox<String> filterBox = new JComboBox<>(new String[]{"All", "Pending", "In Progress", "Ready"});
        filterBox.addActionListener(e -> filterOrders((String) filterBox.getSelectedItem()));
        filterPanel.add(new JLabel("Filter by Status:"));
        filterPanel.add(filterBox);
        add(filterPanel, BorderLayout.SOUTH);

        // Table selection → fill form
        orderTable.getSelectionModel().addListSelectionListener(e -> {
            int row = orderTable.getSelectedRow();
            if (row >= 0) {
                nameField.setText((String) tableModel.getValueAt(row, 1));
                contactField.setText((String) tableModel.getValueAt(row, 2));
                modelField.setText((String) tableModel.getValueAt(row, 3));
                problemField.setText((String) tableModel.getValueAt(row, 4));
                costField.setText(String.valueOf(tableModel.getValueAt(row, 5)));
                statusBox.setSelectedItem((String) tableModel.getValueAt(row, 6));
            }
        });

        loadOrders();
    }

    private void addOrder(ActionEvent e) {
        String name = nameField.getText();
        String contact = contactField.getText();
        String vehicle = modelField.getText();
        String problem = problemField.getText();
        String costStr = costField.getText();
        String status = (String) statusBox.getSelectedItem();

        if (name.isEmpty() || contact.isEmpty() || vehicle.isEmpty() || costStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields.");
            return;
        }

        try {
            double cost = Double.parseDouble(costStr);
            Order order = new Order(name, contact, vehicle, problem, cost, status);

            if (OrderController.addOrder(order)) {
                JOptionPane.showMessageDialog(this, "✅ Order added successfully!");
                loadOrders();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to add order.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cost must be a number.");
        }
    }

    private void updateOrder(ActionEvent e) {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an order to update.");
            return;
        }

        try {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String name = nameField.getText();
            String contact = contactField.getText();
            String vehicle = modelField.getText();
            String problem = problemField.getText();
            double cost = Double.parseDouble(costField.getText());
            String status = (String) statusBox.getSelectedItem();

            Order order = new Order(id, name, contact, vehicle, problem, cost, status);

            if (OrderController.updateOrder(order)) {
                JOptionPane.showMessageDialog(this, "✅ Order updated.");
                loadOrders();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Update failed.");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input.");
        }
    }

    private void deleteOrder(ActionEvent e) {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Select an order to delete.");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        int id = (int) tableModel.getValueAt(selectedRow, 0);
        if (OrderController.deleteOrder(id)) {
            JOptionPane.showMessageDialog(this, "✅ Order deleted.");
            loadOrders();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Delete failed.");
        }
    }

    private void loadOrders() {
        tableModel.setRowCount(0);
        List<Order> orders = OrderController.getAllOrders();
        for (Order o : orders) {
            tableModel.addRow(new Object[]{
                    o.getId(), o.getCustomerName(), o.getContact(),
                    o.getVehicleModel(), o.getProblem(), o.getCost(), o.getStatus()
            });
        }
    }

    private void filterOrders(String status) {
        tableModel.setRowCount(0);
        List<Order> orders = OrderController.getAllOrders();

        for (Order o : orders) {
            if (status.equals("All") || o.getStatus().equalsIgnoreCase(status)) {
                tableModel.addRow(new Object[]{
                        o.getId(), o.getCustomerName(), o.getContact(),
                        o.getVehicleModel(), o.getProblem(), o.getCost(), o.getStatus()
                });
            }
        }
    }

    private void clearForm() {
        nameField.setText("");
        contactField.setText("");
        modelField.setText("");
        problemField.setText("");
        costField.setText("");
        statusBox.setSelectedIndex(0);
    }
}
