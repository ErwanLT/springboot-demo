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
