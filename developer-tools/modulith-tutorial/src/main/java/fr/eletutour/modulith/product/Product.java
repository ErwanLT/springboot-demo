/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of modulith-tutorial.
 *
 * modulith-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * modulith-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with modulith-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
