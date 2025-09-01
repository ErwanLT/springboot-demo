package fr.eletutour.modulith.notification.internal.model;

import java.util.Date;

public class Notification {

    private Date date;
    private NotificationType type;
    private String productname;


    public Notification(Date date, NotificationType notificationType, String name) {
        this.date = date;
        this.type = notificationType;
        this.productname=name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }
}
