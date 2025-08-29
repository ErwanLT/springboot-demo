package fr.eletutour.model;

import java.util.Date;

public class Commande {
    private String id;
    private Date dateCreation;

    public Commande(String id, Date dateCreation) {
        this.id = id;
        this.dateCreation = dateCreation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
}