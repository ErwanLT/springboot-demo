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

import fr.eletutour.modulith.notification.NotificationDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Service for managing products.
 */
@Service
public class ProductService {

    private final ApplicationEventPublisher events;

    /**
     * Constructor for ProductService.
     *
     * @param events the application event publisher
     */
    public ProductService(ApplicationEventPublisher events) {
        this.events = events;
    }

    /**
     * Creates a new product and publishes a notification event.
     *
     * @param product the product to create
     */
    public void create(Product product) {
        // Here you would typically save the product to a database
        // For this example, we just publish an event
        events.publishEvent(new NotificationDTO(new Date(), "SMS", product.getName()));
    }
}
