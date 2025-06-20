package controller;

import db.DBConnection;
import model.Inventory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryController {

    public static boolean addItem(Inventory item) {
        String sql = "INSERT INTO inventory (part_name, quantity, threshold) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getPartName());
            stmt.setInt(2, item.getQuantity());
            stmt.setInt(3, item.getThreshold());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Inventory> getAllItems() {
        List<Inventory> items = new ArrayList<>();
        String sql = "SELECT * FROM inventory";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Inventory item = new Inventory(
                        rs.getInt("id"),
                        rs.getString("part_name"),
                        rs.getInt("quantity"),
                        rs.getInt("threshold")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;
    }

    public static boolean deleteItem(int id) {
        String sql = "DELETE FROM inventory WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
