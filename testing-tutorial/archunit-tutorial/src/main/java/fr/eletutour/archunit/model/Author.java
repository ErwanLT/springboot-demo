package fr.eletutour.archunit.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un auteur dans le système.
 * Cette classe contient :
 * - Les informations de base d'un auteur (nom, biographie)
 * - L'identifiant unique de l'auteur
 * - Les relations avec d'autres entités
 */
@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String bio;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Author() {
    }

    /**
     * Constructeur avec paramètres.
     *
     * @param name Le nom de l'auteur
     * @param bio La biographie de l'auteur
     */
    public Author(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    /**
     * Récupère l'identifiant de l'auteur.
     *
     * @return L'identifiant de l'auteur
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'auteur.
     *
     * @param id L'identifiant à définir
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère le nom de l'auteur.
     *
     * @return Le nom de l'auteur
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de l'auteur.
     *
     * @param name Le nom à définir
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Récupère la biographie de l'auteur.
     *
     * @return La biographie de l'auteur
     */
    public String getBio() {
        return bio;
    }

    /**
     * Définit la biographie de l'auteur.
     *
     * @param bio La biographie à définir
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

}