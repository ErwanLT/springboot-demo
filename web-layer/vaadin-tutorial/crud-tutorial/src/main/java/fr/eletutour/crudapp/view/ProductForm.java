/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of CRUD Tutorial with Vaadin.
 *
 * CRUD Tutorial with Vaadin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * CRUD Tutorial with Vaadin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with CRUD Tutorial with Vaadin.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.crudapp.view;

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
import fr.eletutour.crudapp.entity.Product;
import fr.eletutour.crudapp.repository.ProductRepository;

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