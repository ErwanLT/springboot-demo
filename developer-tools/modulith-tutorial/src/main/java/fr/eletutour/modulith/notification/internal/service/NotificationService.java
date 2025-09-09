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
