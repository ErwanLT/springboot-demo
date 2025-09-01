package fr.eletutour.modulith.product;

import fr.eletutour.modulith.notification.NotificationDTO;
import fr.eletutour.modulith.notification.NotificationExternalAPI;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductService {

    private final NotificationExternalAPI notificationExternalAPI;

    public ProductService(NotificationExternalAPI notificationExternalAPI) {
        this.notificationExternalAPI = notificationExternalAPI;

    }

    public void create(Product product) {
        notificationExternalAPI.notificationEvent(new NotificationDTO(new Date(), "SMS", product.getName()));
    }
}