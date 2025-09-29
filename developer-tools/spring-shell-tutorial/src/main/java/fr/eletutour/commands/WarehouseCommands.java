package fr.eletutour.commands;

import fr.eletutour.model.Product;
import fr.eletutour.service.AsyncService;
import fr.eletutour.service.WarehouseService;
import org.springframework.shell.component.ViewComponent;
import org.springframework.shell.component.ViewComponentBuilder;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.component.view.TerminalUI;
import org.springframework.shell.component.view.control.ProgressView;
import org.springframework.shell.geom.HorizontalAlign;
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
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@ShellComponent("Warehouse Commands")
public class WarehouseCommands {

    private final WarehouseService warehouseService;
    private final AsyncService asyncService;
    private final ViewComponentBuilder viewComponentBuilder;

    public WarehouseCommands(WarehouseService warehouseService, AsyncService asyncService, ComponentFlow componentFlow, ViewComponentBuilder viewComponentBuilder) {
        this.warehouseService = warehouseService;
        this.asyncService = asyncService;
        this.viewComponentBuilder = viewComponentBuilder;
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

    @ShellMethod(key = "run-inventory", value = "Run a long-running inventory check.")
    public String runInventory() throws Exception {
        Future<String> future = asyncService.longRunningTask();

        // Display a simple spinner while the task is running
        char[] spinner = {'|', '/', '-', '\\'};
        int i = 0;
        while (!future.isDone()) {
            System.out.print("\rRunning inventory check... " + spinner[i++ % spinner.length]);
            TimeUnit.MILLISECONDS.sleep(100);
        }
        System.out.print("\r                                 \r"); // Clear the line

        return future.get(); // Get the result from the future
    }

    @ShellMethod(key = "run-inventory-ui", value = "Run a long-running inventory check with a TUI progress bar.")
    public String runInventoryUi() throws Exception {
        // Start the asynchronous long-running task.
        Future<String> future = asyncService.longRunningTask();

        // Build the ProgressView component.
        ProgressView view = new ProgressView(
                ProgressView.ProgressViewItem.ofText(30, HorizontalAlign.LEFT),
                ProgressView.ProgressViewItem.ofSpinner(3, HorizontalAlign.LEFT),
                ProgressView.ProgressViewItem.ofPercent(0, HorizontalAlign.RIGHT)
        );
        view.setDescription("Running inventory check...");

        // Wrap the view in a ViewComponent to handle terminal and event loop.
        ViewComponent component = viewComponentBuilder.build(view);

        // Run the view component asynchronously so it doesn't block the command.
        ViewComponent.ViewComponentRun run = component.runAsync();

        try {
            // Run and manage the progress view until the future is done.
            runProgress(view, future);
        } finally {
            // Request the component to close and wait for it to terminate.
            component.exit();
            run.await();
        }

        // Return the result from the asynchronous task.
        return future.get();
    }

    private void runProgress(ProgressView view, Future<?> future) throws InterruptedException {
        // Start the ProgressView's internal logic (e.g., spinner animation).
        view.start();

        // Since the async task doesn't report progress, we simulate it.
        // The loop updates the progress bar until the task is complete.
        int progress = 0;
        while (!future.isDone()) {
            // Update the progress bar. This is thread-safe for this use case.
            view.setTickValue(Math.min(100, progress));
            progress += 2; // Simulate a 2% progress increment.
            Thread.sleep(150); // Refresh at a reasonable rate.
        }

        // Ensure the progress bar shows 100% and stop the view.
        view.setTickValue(100);
        view.stop();
    }
}