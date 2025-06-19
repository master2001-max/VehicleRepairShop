package model;

public class Order {
    private int id;
    private String customerName;
    private String contact;
    private String vehicleModel;
    private String problem;
    private double cost;
    private String status;

    // For new orders (no ID yet)
    public Order(String customerName, String contact, String vehicleModel, String problem, double cost, String status) {
        this.customerName = customerName;
        this.contact = contact;
        this.vehicleModel = vehicleModel;
        this.problem = problem;
        this.cost = cost;
        this.status = status;
    }

    // For existing orders with ID
    public Order(int id, String customerName, String contact, String vehicleModel, String problem, double cost, String status) {
        this.id = id;
        this.customerName = customerName;
        this.contact = contact;
        this.vehicleModel = vehicleModel;
        this.problem = problem;
        this.cost = cost;
        this.status = status;
    }

    // Getters
    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getContact() { return contact; }
    public String getVehicleModel() { return vehicleModel; }
    public String getProblem() { return problem; }
    public double getCost() { return cost; }
    public String getStatus() { return status; }

    // Setters (optional if needed)
}
