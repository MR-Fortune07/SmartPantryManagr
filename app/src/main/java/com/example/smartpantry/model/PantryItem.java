package com.example.smartpantry.model;

/**An ingredient that the user currently has at home.*/
public class PantryItem {
    private long id;
    private String name;
    private double quantity;
    private String unit;

    /**This is the expiry date in ISO format (yyyy-MM-dd), or null. it is optional*/
    private String expiry;

    public PantryItem(long id, String name, double quantity, String unit, String expiry) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiry = expiry;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public double getQuantity() { return quantity; }
    public String getUnit() { return unit; }
    public String getExpiry() { return expiry; }
}