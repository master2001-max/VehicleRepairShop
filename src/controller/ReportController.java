package controller;

import db.DBConnection;
import model.Inventory;

import java.sql.*;
import java.util.*;

public class ReportController {

    // Inner class to hold summary
    public static class OrderSummary {
        private int orderCount;
        private double totalIncome;

        public OrderSummary(int orderCount, double totalIncome) {
            this.orderCount = orderCount;
            this.totalIncome = totalIncome;
        }

        public int getOrderCount() { return orderCount; }
        public double getTotalIncome() { return totalIncome; }
    }

    // Returns Map of date → OrderSummary
    public static Map<String, OrderSummary> getOrderSummaryByDate() {
        Map<String, OrderSummary> map = new LinkedHashMap<>();

        String sql = "SELECT DATE(created_at) AS order_date, COUNT(*) AS count, SUM(cost) AS total " +
                "FROM orders GROUP BY DATE(created_at) ORDER BY order_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String date = rs.getString("order_date");
                int count = rs.getInt("count");
                double total = rs.getDouble("total");
                map.put(date, new OrderSummary(count, total));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }

    public static List<Inventory> getInventoryReport() {
        List<Inventory> items = new ArrayList<>();
        String sql = "SELECT * FROM inventory";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                items.add(new Inventory(
                        rs.getInt("id"),
                        rs.getString("part_name"),
                        rs.getInt("quantity"),
                        rs.getInt("threshold")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }
}
