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
