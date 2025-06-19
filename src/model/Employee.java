package model;

public class Employee {
    private int id;
    private String name;
    private String role;
    private String phone;
    private String email;

    public Employee(String name, String role, String phone, String email) {
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
    }

    public Employee(int id, String name, String role, String phone, String email) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.phone = phone;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}
