package model;

public class Supplier {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String itemSupplied;

    public Supplier(String name, String phone, String email, String itemSupplied) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.itemSupplied = itemSupplied;
    }

    public Supplier(int id, String name, String phone, String email, String itemSupplied) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.itemSupplied = itemSupplied;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getItemSupplied() { return itemSupplied; }
}
