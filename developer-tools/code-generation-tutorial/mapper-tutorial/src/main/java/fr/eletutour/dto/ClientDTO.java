/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of mapper-tutorial.
 *
 * mapper-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * mapper-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with mapper-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
