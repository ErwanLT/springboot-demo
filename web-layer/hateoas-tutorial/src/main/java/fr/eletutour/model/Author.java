package fr.eletutour.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un auteur dans le système.
 * Cette entité étend RepresentationModel pour supporter HATEOAS et fournit :
 * <ul>
 *     <li>Les informations de base de l'auteur (nom, biographie)</li>
 *     <li>La gestion des articles associés</li>
 *     <li>Les liens hypermedia pour la navigation</li>
 * </ul>
 */
@Entity
public class Author extends RepresentationModel<Author> {
    /**
     * Identifiant unique de l'auteur.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Nom de l'auteur.
     */
    private String name;
    
    /**
     * Biographie de l'auteur.
     */
    private String bio;
    
    /**
     * Liste des articles écrits par l'auteur.
     * Relation one-to-many avec cascade.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Article> articles = new ArrayList<>();

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Author() {
    }

    /**
     * Constructeur avec paramètres pour créer un nouvel auteur.
     *
     * @param name Le nom de l'auteur
     * @param bio La biographie de l'auteur
     * @param articles La liste des articles de l'auteur
     */
    public Author(String name, String bio, List<Article> articles) {
        this.name = name;
        this.bio = bio;
        this.articles = articles;
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
     * @param id Le nouvel identifiant
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
     * @param name Le nouveau nom
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
     * @param bio La nouvelle biographie
     */
    public void setBio(String bio) {
        this.bio = bio;
    }

    /**
     * Récupère la liste des articles de l'auteur.
     *
     * @return La liste des articles
     */
    public List<Article> getArticles() {
        return articles;
    }

    /**
     * Définit la liste des articles de l'auteur.
     *
     * @param articles La nouvelle liste d'articles
     */
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}