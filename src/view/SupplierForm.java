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
    private JTable supplierTable;
    private DefaultTableModel tableModel;

    public SupplierForm() {
        setTitle("Supplier Management");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        nameField = new JTextField();
        phoneField = new JTextField();
        emailField = new JTextField();
        itemField = new JTextField();

        formPanel.add(new JLabel("Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Phone:")); formPanel.add(phoneField);
        formPanel.add(new JLabel("Email:")); formPanel.add(emailField);
        formPanel.add(new JLabel("Item Supplied:")); formPanel.add(itemField);

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");

        addBtn.addActionListener(this::addSupplier);
        updateBtn.addActionListener(this::updateSupplier);
        deleteBtn.addActionListener(this::deleteSupplier);

        formPanel.add(addBtn);
        formPanel.add(updateBtn);
        formPanel.add(deleteBtn);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Email", "Item"}, 0);
        supplierTable = new JTable(tableModel);
        supplierTable.getSelectionModel().addListSelectionListener(e -> fillForm());
        add(new JScrollPane(supplierTable), BorderLayout.CENTER);

        loadSuppliers();
    }

    private void addSupplier(ActionEvent e) {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String item = itemField.getText();

        if (name.isEmpty() || phone.isEmpty() || item.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields");
            return;
        }

        Supplier s = new Supplier(name, phone, email, item);
        if (SupplierController.addSupplier(s)) {
            JOptionPane.showMessageDialog(this, "Supplier added");
            loadSuppliers();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add supplier");
        }
    }

    private void updateSupplier(ActionEvent e) {
        int row = supplierTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to update");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        Supplier s = new Supplier(
                id,
                nameField.getText(),
                phoneField.getText(),
                emailField.getText(),
                itemField.getText()
        );

        if (SupplierController.updateSupplier(s)) {
            JOptionPane.showMessageDialog(this, "Supplier updated");
            loadSuppliers();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to update");
        }
    }

    private void deleteSupplier(ActionEvent e) {
        int row = supplierTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete");
            return;
        }

        int id = (int) tableModel.getValueAt(row, 0);
        if (SupplierController.deleteSupplier(id)) {
            JOptionPane.showMessageDialog(this, "Supplier deleted");
            loadSuppliers();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Delete failed");
        }
    }

    private void loadSuppliers() {
        tableModel.setRowCount(0);
        List<Supplier> list = SupplierController.getAllSuppliers();
        for (Supplier s : list) {
            tableModel.addRow(new Object[]{
                    s.getId(), s.getName(), s.getPhone(), s.getEmail(), s.getItemSupplied()
            });
        }
    }

    private void fillForm() {
        int row = supplierTable.getSelectedRow();
        if (row >= 0) {
            nameField.setText((String) tableModel.getValueAt(row, 1));
            phoneField.setText((String) tableModel.getValueAt(row, 2));
            emailField.setText((String) tableModel.getValueAt(row, 3));
            itemField.setText((String) tableModel.getValueAt(row, 4));
        }
    }

    private void clearForm() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        itemField.setText("");
        supplierTable.clearSelection();
    }
}
