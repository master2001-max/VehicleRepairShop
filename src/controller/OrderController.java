package controller;

import db.DBConnection;
import model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderController {

    public static boolean addOrder(Order order) {
        String sql = "INSERT INTO orders (customer_name, contact, vehicle_model, problem, cost, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, order.getCustomerName());
            stmt.setString(2, order.getContact());
            stmt.setString(3, order.getVehicleModel());
            stmt.setString(4, order.getProblem());
            stmt.setDouble(5, order.getCost());
            stmt.setString(6, order.getStatus());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getString("customer_name"),
                        rs.getString("contact"),
                        rs.getString("vehicle_model"),
                        rs.getString("problem"),
                        rs.getDouble("cost"),
                        rs.getString("status")
                );
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    public static boolean updateOrder(Order order) {
        String sql = "UPDATE orders SET customer_name=?, contact=?, vehicle_model=?, problem=?, cost=?, status=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, order.getCustomerName());
            stmt.setString(2, order.getContact());
            stmt.setString(3, order.getVehicleModel());
            stmt.setString(4, order.getProblem());
            stmt.setDouble(5, order.getCost());
            stmt.setString(6, order.getStatus());
            stmt.setInt(7, order.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteOrder(int id) {
        String sql = "DELETE FROM orders WHERE id=?";
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
