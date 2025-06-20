package view;

import controller.ReportController;
import model.Order;
import model.Inventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ReportForm extends JFrame {

    private JTable orderTable;
    private JTable inventoryTable;
    private JLabel incomeLabel;

    public ReportForm() {
        setTitle("📊 Reports");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // 1. Order Report
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderTable = new JTable(new DefaultTableModel(
                new String[]{"Date", "Orders", "Total Income"}, 0
        ));
        orderPanel.add(new JScrollPane(orderTable), BorderLayout.CENTER);
        incomeLabel = new JLabel("Total Income: Rs. 0.00", SwingConstants.RIGHT);
        incomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        orderPanel.add(incomeLabel, BorderLayout.SOUTH);
        tabbedPane.addTab("📅 Orders Report", orderPanel);

        // 2. Inventory Report
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        inventoryTable = new JTable(new DefaultTableModel(
                new String[]{"Part Name", "Quantity", "Threshold", "Status"}, 0
        ));
        inventoryPanel.add(new JScrollPane(inventoryTable), BorderLayout.CENTER);
        tabbedPane.addTab("📦 Inventory Status", inventoryPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // Load data
        loadOrderReport();
        loadInventoryReport();
    }

    private void loadOrderReport() {
        DefaultTableModel model = (DefaultTableModel) orderTable.getModel();
        model.setRowCount(0);
        Map<String, ReportController.OrderSummary> data = ReportController.getOrderSummaryByDate();

        double totalIncome = 0;

        for (String date : data.keySet()) {
            ReportController.OrderSummary summary = data.get(date);
            model.addRow(new Object[]{date, summary.getOrderCount(), summary.getTotalIncome()});
            totalIncome += summary.getTotalIncome();
        }

        incomeLabel.setText("Total Income: Rs. " + totalIncome);
    }

    private void loadInventoryReport() {
        DefaultTableModel model = (DefaultTableModel) inventoryTable.getModel();
        model.setRowCount(0);

        List<Inventory> items = ReportController.getInventoryReport();

        for (Inventory item : items) {
            String status = item.getQuantity() < item.getThreshold() ? "⚠️ Low" : "OK";
            model.addRow(new Object[]{
                    item.getPartName(), item.getQuantity(), item.getThreshold(), status
            });
        }
    }
}
