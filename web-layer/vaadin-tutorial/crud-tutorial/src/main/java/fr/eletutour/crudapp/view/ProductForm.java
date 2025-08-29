package fr.eletutour.crudtutorial.view;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import fr.eletutour.crudtutorial.entity.Product;
import fr.eletutour.crudtutorial.repository.ProductRepository;

public class ProductForm extends FormLayout {
    private final ProductRepository repository;
    private final ProductView productView;

    TextField name = new TextField("Name");
    NumberField price = new NumberField("Price");
    TextArea description = new TextArea("Description");
    IntegerField stockQuantity = new IntegerField("Stock Quantity");

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button cancel = new Button("Cancel");

    Binder<Product> binder = new BeanValidationBinder<>(Product.class);
    private Product product;

    public ProductForm(ProductView productView, ProductRepository repository) {
        this.productView = productView;
        this.repository = repository;

        addClassName("product-form");
        binder.bindInstanceFields(this);

        add(
            name,
            price,
            description,
            stockQuantity,
            createButtonLayout()
        );

        configureButtons();
    }

    private void configureButtons() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> deleteProduct());
        cancel.addClickListener(event -> productView.editProduct(null));

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);
    }

    private HorizontalLayout createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        return new HorizontalLayout(save, delete, cancel);
    }

    public void setProduct(Product product) {
        this.product = product;
        binder.setBean(product);
    }

    private void validateAndSave() {
        if (binder.isValid()) {
            repository.save(product);
            productView.refreshGrid();
            productView.editProduct(null);
            Notification.show("Product saved successfully!");
        }
    }

    private void deleteProduct() {
        if (this.product != null) {
            repository.delete(this.product);
            productView.refreshGrid();
            productView.editProduct(null);
            Notification.show("Product deleted successfully!");
        }
    }
} 