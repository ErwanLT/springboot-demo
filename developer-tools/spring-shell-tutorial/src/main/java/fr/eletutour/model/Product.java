package fr.eletutour.model;

public class Product {
    private final String id;
    private final String name;
    private int quantity;

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
        this.quantity = 0;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{"
                + "id='" + id + "'"
                + ", name='" + name + "'"
                + ", quantity=" + quantity
                + "}";
    }
}
