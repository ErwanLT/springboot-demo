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
package fr.eletutour.modulith.notification;

import java.util.Date;

/**
 * Data Transfer Object for notifications.
 * This class is used to transfer notification data between modules,
 * specifically from the product module to the notification module.
 */
public class NotificationDTO {
    private Date date;
    private String format;
    private String productName;

    /**
     * Constructor for NotificationDTO.
     *
     * @param date        the date of the notification
     * @param format      the format of the notification (e.g., "SMS")
     * @param productName the name of the product associated with the notification
     */
    public NotificationDTO(Date date, String format, String productName) {
        this.date = date;
        this.format = format;
        this.productName = productName;
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
     * Gets the format of the notification.
     *
     * @return the notification format
     */
    public String getFormat() {
        return format;
    }

    /**
     * Sets the format of the notification.
     *
     * @param format the notification format
     */
    public void setFormat(String format) {
        this.format = format;
    }

    /**
     * Gets the product name.
     *
     * @return the product name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * Sets the product name.
     *
     * @param productName the product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
}
