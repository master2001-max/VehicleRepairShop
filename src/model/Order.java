package model;

public class Order {
    private int id;
    private String customerName;
    private String contact;
    private String vehicleModel;
    private String problem;
    private double cost;
    private String status;

    public Order(int id, String customerName, String contact, String vehicleModel, String problem, double cost, String status) {
        this.id = id;
        this.customerName = customerName;
        this.contact = contact;
        this.vehicleModel = vehicleModel;
        this.problem = problem;
        this.cost = cost;
        this.status = status;
    }

    public Order(String customerName, String contact, String vehicleModel, String problem, double cost, String status) {
        this(0, customerName, contact, vehicleModel, problem, cost, status);
    }

    // Getters and setters...

    public int getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getContact() { return contact; }
    public String getVehicleModel() { return vehicleModel; }
    public String getProblem() { return problem; }
    public double getCost() { return cost; }
    public String getStatus() { return status; }

    public void setId(int id) { this.id = id; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setContact(String contact) { this.contact = contact; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }
    public void setProblem(String problem) { this.problem = problem; }
    public void setCost(double cost) { this.cost = cost; }
    public void setStatus(String status) { this.status = status; }
}
