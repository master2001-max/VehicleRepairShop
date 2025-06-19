package view;

import controller.EmployeeController;
import model.Employee;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class EmployeeForm extends JFrame {
    private JTextField nameField, phoneField, emailField;
    private JComboBox<String> roleBox;
    private JTable table;
    private DefaultTableModel model;

    public EmployeeForm() {
        setTitle("👨‍🔧 Employee Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        roleBox = new JComboBox<>(new String[]{"Mechanic", "Receptionist", "Manager"});
        phoneField = new JTextField();
        emailField = new JTextField();

        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleBox);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);

        JButton addBtn = new JButton("➕ Add");
        JButton updateBtn = new JButton("✏️ Update");
        JButton deleteBtn = new JButton("🗑️ Delete");

        addBtn.addActionListener(this::addEmployee);
        updateBtn.addActionListener(this::updateEmployee);
        deleteBtn.addActionListener(this::deleteEmployee);

        formPanel.add(addBtn);
        formPanel.add(updateBtn);

        add(formPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Role", "Phone", "Email"}, 0);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> loadSelectedRow());

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(deleteBtn, BorderLayout.SOUTH);

        loadEmployees();
    }

    private void addEmployee(ActionEvent e) {
        String name = nameField.getText();
        String role = (String) roleBox.getSelectedItem();
        String phone = phoneField.getText();
        String email = emailField.getText();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and phone are required!");
            return;
        }

        Employee emp = new Employee(name, role, phone, email);
        if (EmployeeController.addEmployee(emp)) {
            JOptionPane.showMessageDialog(this, "✅ Employee added!");
            loadEmployees();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Failed to add employee.");
        }
    }

    private void updateEmployee(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int id = (int) model.getValueAt(row, 0);
        String name = nameField.getText();
        String role = (String) roleBox.getSelectedItem();
        String phone = phoneField.getText();
        String email = emailField.getText();

        Employee emp = new Employee(id, name, role, phone, email);
        if (EmployeeController.updateEmployee(emp)) {
            JOptionPane.showMessageDialog(this, "✏️ Employee updated!");
            loadEmployees();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Update failed.");
        }
    }

    private void deleteEmployee(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this employee?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) model.getValueAt(row, 0);
            if (EmployeeController.deleteEmployee(id)) {
                JOptionPane.showMessageDialog(this, "🗑️ Employee deleted.");
                loadEmployees();
                clearForm();
            }
        }
    }

    private void loadEmployees() {
        model.setRowCount(0);
        List<Employee> list = EmployeeController.getAllEmployees();
        for (Employee e : list) {
            model.addRow(new Object[]{
                    e.getId(), e.getName(), e.getRole(), e.getPhone(), e.getEmail()
            });
        }
    }

    private void clearForm() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        roleBox.setSelectedIndex(0);
    }

    private void loadSelectedRow() {
        int row = table.getSelectedRow();
        if (row != -1) {
            nameField.setText((String) model.getValueAt(row, 1));
            roleBox.setSelectedItem(model.getValueAt(row, 2));
            phoneField.setText((String) model.getValueAt(row, 3));
            emailField.setText((String) model.getValueAt(row, 4));
        }
    }
}
