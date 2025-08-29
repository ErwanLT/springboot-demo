package fr.eletutour.model;

public class Client {
    private String nom;
    private String prenom;
    private Adresse adresse;
    private Commande commande;

    public Client(String nom, String prenom, Adresse adresse, Commande commande) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.commande = commande;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }
}
