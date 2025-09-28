package fr.eletutour.commands;

import fr.eletutour.model.Product;
import fr.eletutour.service.WarehouseService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;
import org.springframework.shell.table.TableModel;

import java.util.Collection;
import java.util.LinkedHashMap;

@ShellComponent("Warehouse Commands")
public class WarehouseCommands {

    private final WarehouseService warehouseService;

    public WarehouseCommands(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @ShellMethod(key = "add-product", value = "Add a new product to the warehouse.")
    public String addProduct(
            @ShellOption(help = "The unique ID of the product.") String id,
            @ShellOption(help = "The name of the product.") String name) {
        try {
            Product product = warehouseService.addProduct(id, name);
            return "Product added: " + product.getName() + " (ID: " + product.getId() + ")";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod(key = "list-products", value = "List all products in the warehouse.")
    public Table listProducts() {
        Collection<Product> products = warehouseService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products in warehouse.");
            return null;
        }

        LinkedHashMap<String, Object> headers = new LinkedHashMap<>();
        headers.put("id", "ID");
        headers.put("name", "Name");
        headers.put("quantity", "Quantity");

        TableModel model = new BeanListTableModel<>(products, headers);
        TableBuilder tableBuilder = new TableBuilder(model);
        tableBuilder.addHeaderAndVerticalsBorders(BorderStyle.fancy_light);
        // Set a width for the 'Name' column (index 1) to prevent wrapping
        tableBuilder.on(org.springframework.shell.table.CellMatchers.column(1)).addSizer(new org.springframework.shell.table.AbsoluteWidthSizeConstraints(20));
        return tableBuilder.build();
    }

    @ShellMethod(key = "add-stock", value = "Add stock for a specific product.")
    public String addStock(
            @ShellOption(help = "The ID of the product to update.") String id,
            @ShellOption(help = "The quantity to add (must be positive).") int quantity) {
        if (quantity <= 0) {
            return "Error: Quantity to add must be positive.";
        }
        try {
            Product product = warehouseService.updateStock(id, quantity);
            return "Stock updated for " + product.getName() + ". New quantity: " + product.getQuantity();
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }

    @ShellMethod(key = "remove-stock", value = "Remove stock for a specific product (e.g., an order).")
    public String removeStock(
            @ShellOption(help = "The ID of the product to update.") String id,
            @ShellOption(help = "The quantity to remove (must be positive).") int quantity) {
        if (quantity <= 0) {
            return "Error: Quantity to remove must be positive.";
        }
        try {
            Product product = warehouseService.updateStock(id, -quantity);
            return "Stock removed for " + product.getName() + ". New quantity: " + product.getQuantity();
        } catch (IllegalArgumentException | IllegalStateException e) {
            return "Error: " + e.getMessage();
        }
    }
}