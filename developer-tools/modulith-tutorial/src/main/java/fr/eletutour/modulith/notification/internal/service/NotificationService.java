package fr.eletutour.modulith.notification.internal.service;

import fr.eletutour.modulith.notification.NotificationDTO;
import fr.eletutour.modulith.notification.internal.model.Notification;
import fr.eletutour.modulith.notification.internal.model.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Service for handling notifications.
 * This service listens for notification events and processes them.
 */
@Service
public class NotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    /**
     * Handles the notification event.
     * This method is triggered when a {@link NotificationDTO} event is published.
     *
     * @param event the notification event
     */
    @EventListener
    public void notificationEvent(NotificationDTO event) {
        Notification notification = toEntity(event);
        LOG.info("Received notification by event for product {} in date {} by {}.",
                notification.getProductname(),
                notification.getDate(),
                notification.getType());
    }

    /**
     * Converts a {@link NotificationDTO} to a {@link Notification} entity.
     *
     * @param event the notification DTO
     * @return the notification entity
     */
    private Notification toEntity(NotificationDTO event) {
        return new Notification(event.getDate(), NotificationType.valueOf(event.getFormat()), event.getProductName());
    }
}
