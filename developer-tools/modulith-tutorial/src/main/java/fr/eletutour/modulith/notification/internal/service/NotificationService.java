package fr.eletutour.modulith.notification.internal.service;

import fr.eletutour.modulith.notification.NotificationDTO;
import fr.eletutour.modulith.notification.NotificationExternalAPI;
import fr.eletutour.modulith.notification.internal.model.Notification;
import fr.eletutour.modulith.notification.internal.model.NotificationType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class NotificationService implements NotificationExternalAPI {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationService.class);

    @Override
    public void notificationEvent(NotificationDTO event) {
        Notification notification = toEntity(event);
        LOG.info("Received notification by event for product {} in date {} by {}.",
                notification.getProductname(),
                notification.getDate(),
                notification.getType());
    }

    private Notification toEntity(NotificationDTO event) {
        return new Notification(event.getDate(), NotificationType.valueOf(event.getFormat()), event.getProductName());
    }


}