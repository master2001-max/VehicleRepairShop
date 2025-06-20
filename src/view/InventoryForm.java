package view;

import controller.InventoryController;
import model.Inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventoryForm extends JFrame {

    private JTextField partField, quantityField, thresholdField;
    private JTable inventoryTable;
    private DefaultTableModel tableModel;

    public InventoryForm() {
        setTitle("Inventory Management");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Form Panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        partField = new JTextField();
        quantityField = new JTextField();
        thresholdField = new JTextField();

        formPanel.add(new JLabel("Part Name:"));
        formPanel.add(partField);
        formPanel.add(new JLabel("Quantity:"));
        formPanel.add(quantityField);
        formPanel.add(new JLabel("Threshold:"));
        formPanel.add(thresholdField);

        JButton addBtn = new JButton("Add Part");
        formPanel.add(new JLabel());
        formPanel.add(addBtn);

        add(formPanel, BorderLayout.NORTH);

        // Table
        tableModel = new DefaultTableModel(new String[]{"ID", "Part Name", "Quantity", "Threshold", "Status"}, 0);
        inventoryTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(inventoryTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load data
        loadInventory();

        // Button action
        addBtn.addActionListener(e -> addPart());
    }

    private void addPart() {
        String part = partField.getText();
        String qtyStr = quantityField.getText();
        String thresStr = thresholdField.getText();

        if (part.isEmpty() || qtyStr.isEmpty() || thresStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields.");
            return;
        }

        try {
            int quantity = Integer.parseInt(qtyStr);
            int threshold = Integer.parseInt(thresStr);

            Inventory item = new Inventory(part, quantity, threshold);
            if (InventoryController.addItem(item)) {
                JOptionPane.showMessageDialog(this, "✅ Item added!");
                loadInventory();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Failed to add item.");
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Quantity and Threshold must be numbers.");
        }
    }

    private void loadInventory() {
        tableModel.setRowCount(0);
        List<Inventory> items = InventoryController.getAllItems();
        for (Inventory i : items) {
            String status = i.getQuantity() < i.getThreshold() ? "⚠️ Low Stock" : "OK";
            tableModel.addRow(new Object[]{
                    i.getId(), i.getPartName(), i.getQuantity(), i.getThreshold(), status
            });
        }
    }

    private void clearForm() {
        partField.setText("");
        quantityField.setText("");
        thresholdField.setText("");
    }
}
