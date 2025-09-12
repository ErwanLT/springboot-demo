package fr.eletutour.dto;

public class ClientDTO {
    private String nom;
    private String prenom;
    private String nomComplet;
    private AdresseDTO adresse;
    private CommandeDTO commande;

    public ClientDTO(String nom, String prenom, String nomComplet, AdresseDTO adresse, CommandeDTO commande) {
        this.nom = nom;
        this.prenom = prenom;
        this.nomComplet = nomComplet;
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

    public AdresseDTO getAdresse() {
        return adresse;
    }

    public void setAdresse(AdresseDTO adresse) {
        this.adresse = adresse;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public CommandeDTO getCommande() {
        return commande;
    }

    public void setCommande(CommandeDTO commande) {
        this.commande = commande;
    }
}
