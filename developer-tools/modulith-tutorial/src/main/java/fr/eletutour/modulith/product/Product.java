package fr.eletutour.modulith.product;

/**
 * Represents a product in the system.
 */
public class Product {

    private String name;
    private String description;
    private int price;

    /**
     * Constructor for Product.
     *
     * @param name        the name of the product
     * @param description the description of the product
     * @param price       the price of the product
     */
    public Product(String name, String description, int price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    /**
     * Gets the name of the product.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name the product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description of the product.
     *
     * @return the product description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the product.
     *
     * @param description the product description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the price of the product.
     *
     * @return the product price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     *
     * @param price the product price
     */
    public void setPrice(int price) {
        this.price = price;
    }
}
