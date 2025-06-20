package view;

import controller.SupplierController;
import model.Supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class SupplierForm extends JFrame {
    private JTextField nameField, phoneField, emailField, itemField;
    private JTable table;
    private DefaultTableModel model;

    public SupplierForm() {
        setTitle("🏷️ Supplier Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        itemField = new JTextField();

        formPanel.add(new JLabel("Supplier Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Phone:"));
        formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Item Supplied:"));
        formPanel.add(itemField);

        JButton addBtn = new JButton("➕ Add");
        JButton updateBtn = new JButton("✏️ Update");
        JButton deleteBtn = new JButton("🗑️ Delete");

        addBtn.addActionListener(this::addSupplier);
        updateBtn.addActionListener(this::updateSupplier);
        deleteBtn.addActionListener(this::deleteSupplier);

        formPanel.add(addBtn);
        formPanel.add(updateBtn);

        add(formPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Email", "Item"}, 0);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> loadSelectedRow());

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(deleteBtn, BorderLayout.SOUTH);

        loadSuppliers();
    }

    private void addSupplier(ActionEvent e) {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String item = itemField.getText();

        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Phone are required!");
            return;
        }

        Supplier s = new Supplier(name, phone, email, item);
        if (SupplierController.addSupplier(s)) {
            JOptionPane.showMessageDialog(this, "✅ Supplier added!");
            loadSuppliers();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Failed to add supplier.");
        }
    }

    private void updateSupplier(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int id = (int) model.getValueAt(row, 0);
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String item = itemField.getText();

        Supplier s = new Supplier(id, name, phone, email, item);
        if (SupplierController.updateSupplier(s)) {
            JOptionPane.showMessageDialog(this, "✏️ Supplier updated!");
            loadSuppliers();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Update failed.");
        }
    }

    private void deleteSupplier(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row == -1) return;

        int confirm = JOptionPane.showConfirmDialog(this, "Delete this supplier?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) model.getValueAt(row, 0);
            if (SupplierController.deleteSupplier(id)) {
                JOptionPane.showMessageDialog(this, "🗑️ Supplier deleted.");
                loadSuppliers();
                clearForm();
            }
        }
    }

    private void loadSuppliers() {
        model.setRowCount(0);
        List<Supplier> list = SupplierController.getAllSuppliers();
        for (Supplier s : list) {
            model.addRow(new Object[]{
                    s.getId(), s.getName(), s.getPhone(), s.getEmail(), s.getItemSupplied()
            });
        }
    }

    private void clearForm() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        itemField.setText("");
    }

    private void loadSelectedRow() {
        int row = table.getSelectedRow();
        if (row != -1) {
            nameField.setText((String) model.getValueAt(row, 1));
            phoneField.setText((String) model.getValueAt(row, 2));
            emailField.setText((String) model.getValueAt(row, 3));
            itemField.setText((String) model.getValueAt(row, 4));
        }
    }
}
