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
