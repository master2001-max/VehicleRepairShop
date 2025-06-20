package model;

public class Inventory {
    private int id;
    private String partName;
    private int quantity;
    private int threshold;

    public Inventory(String partName, int quantity, int threshold) {
        this.partName = partName;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    public Inventory(int id, String partName, int quantity, int threshold) {
        this.id = id;
        this.partName = partName;
        this.quantity = quantity;
        this.threshold = threshold;
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getPartName() { return partName; }
    public int getQuantity() { return quantity; }
    public int getThreshold() { return threshold; }

    public void setId(int id) { this.id = id; }
    public void setPartName(String partName) { this.partName = partName; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setThreshold(int threshold) { this.threshold = threshold; }
}
