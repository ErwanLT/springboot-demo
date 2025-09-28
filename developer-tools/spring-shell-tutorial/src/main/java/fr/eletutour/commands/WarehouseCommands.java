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
        // lance la tâche asynchrone (ex : CompletableFuture)
        Future<String> future = asyncService.longRunningTask();

        // construit la ProgressView (tickStart=0, tickEnd=100 par défaut)
        ProgressView view = new ProgressView(
                ProgressView.ProgressViewItem.ofText(30, HorizontalAlign.LEFT),
                ProgressView.ProgressViewItem.ofSpinner(3, HorizontalAlign.LEFT),
                ProgressView.ProgressViewItem.ofPercent(0, HorizontalAlign.RIGHT)
        );
        view.setDescription("Vérification inventaire...");

        // wrap -> ViewComponent prêt à l'emploi (configure terminal/eventLoop pour toi)
        ViewComponent component = viewComponentBuilder.build(view);

        // exécute asynchrone la vue (ne bloque pas la commande)
        ViewComponent.ViewComponentRun run = component.runAsync();

        // démarre la logique interne du ProgressView (optionnel si la vue gère son propre tick)
        view.start();

        // boucle d'update : ici on simule / extrait l'avancement de ta tâche
        // remplace la logique ci-dessous par la progression réelle si dispo
        int simulated = 0;
        while (!future.isDone()) {
            // met à jour la barre de progression (thread-safe pour ce cas d'usage)
            view.setTickValue(Math.min(100, simulated));
            simulated += 2;               // ou calcul réel
            Thread.sleep(150);            // rafraîchissement raisonnable
        }

        // tâche terminée : garantir état final et arrêter la vue
        view.setTickValue(100);
        view.stop();

        // demander la fermeture du component + attendre la fin
        component.exit();
        run.await();

        // renvoyer résultat
        return future.get(); // ou future.join()
    }
}