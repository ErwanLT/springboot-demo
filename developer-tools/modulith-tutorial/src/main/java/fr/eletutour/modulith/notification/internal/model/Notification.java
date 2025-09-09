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
package fr.eletutour.modulith.notification.internal.model;

import java.util.Date;

/**
 * Represents a notification entity within the notification module.
 * This is an internal model class.
 */
public class Notification {

    private Date date;
    private NotificationType type;
    private String productname;


    /**
     * Constructor for Notification.
     *
     * @param date             the date of the notification
     * @param notificationType the type of the notification
     * @param name             the name of the product
     */
    public Notification(Date date, NotificationType notificationType, String name) {
        this.date = date;
        this.type = notificationType;
        this.productname=name;
    }

    /**
     * Gets the date of the notification.
     *
     * @return the notification date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the date of the notification.
     *
     * @param date the notification date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the type of the notification.
     *
     * @return the notification type
     */
    public NotificationType getType() {
        return type;
    }

    /**
     * Sets the type of the notification.
     *
     * @param type the notification type
     */
    public void setType(NotificationType type) {
        this.type = type;
    }

    /**
     * Gets the product name.
     *
     * @return the product name
     */
    public String getProductname() {
        return productname;
    }

    /**
     * Sets the product name.
     *
     * @param productname the product name
     */
    public void setProductname(String productname) {
        this.productname = productname;
    }
}
