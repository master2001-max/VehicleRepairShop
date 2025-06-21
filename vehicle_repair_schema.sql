-- Create database
CREATE DATABASE IF NOT EXISTS vehicle_repair;
USE vehicle_repair;

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- Insert default users
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'Admin'),
('staff1', 'staff123', 'Staff');

-- Orders table
CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100),
    contact VARCHAR(20),
    vehicle_model VARCHAR(50),
    problem TEXT,
    cost DOUBLE,
    status VARCHAR(20)
);

-- Suppliers table
CREATE TABLE IF NOT EXISTS suppliers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    phone VARCHAR(20),
    email VARCHAR(100),
    item_supplied VARCHAR(100)
);

-- Employees table
CREATE TABLE IF NOT EXISTS employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    role VARCHAR(50)
);

-- Inventory table
CREATE TABLE IF NOT EXISTS inventory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    part_name VARCHAR(100),
    quantity INT,
    threshold INT
);