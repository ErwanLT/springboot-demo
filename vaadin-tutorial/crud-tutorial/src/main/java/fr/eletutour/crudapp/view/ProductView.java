package fr.eletutour.crudtutorial.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import fr.eletutour.crudtutorial.entity.Product;
import fr.eletutour.crudtutorial.repository.ProductRepository;
import jakarta.annotation.PostConstruct;

@Route("")
public class ProductView extends VerticalLayout {

    private final ProductRepository repository;
    private final Grid<Product> grid = new Grid<>(Product.class);
    private final TextField filterText = new TextField();
    private final ProductForm form;
    private final Button addProductButton;

    public ProductView(ProductRepository repository) {
        this.repository = repository;
        this.form = new ProductForm(this, repository);
        this.addProductButton = new Button("Add Product", e -> addProduct());
        
        setSizeFull();
        configureGrid();
        configureFilter();
        configureForm();

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addProductButton);
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();

        add(toolbar, mainContent);
        updateList();
        closeEditor();
    }

    private void configureForm() {
        form.setWidth("25em");
        form.setVisible(false);
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("name", "price", "stockQuantity");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.asSingleSelect().addValueChangeListener(e -> editProduct(e.getValue()));
    }

    private void configureFilter() {
        filterText.setPlaceholder("Filter by name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
    }

    private void addProduct() {
        grid.asSingleSelect().clear();
        editProduct(new Product());
    }

    private void updateList() {
        grid.setItems(repository.findAll());
    }

    public void editProduct(Product product) {
        if (product == null) {
            closeEditor();
        } else {
            form.setProduct(product);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void closeEditor() {
        form.setProduct(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    public void refreshGrid() {
        grid.setItems(repository.findAll());
    }
} 