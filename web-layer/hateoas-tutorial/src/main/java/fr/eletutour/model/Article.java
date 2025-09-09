/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of hateoas-tutorial.
 *
 * hateoas-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * hateoas-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with hateoas-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.hateoas.RepresentationModel;

/**
 * Entité représentant un article dans le système.
 * Cette entité étend RepresentationModel pour supporter HATEOAS et fournit :
 * <ul>
 *     <li>Les informations de base de l'article (titre, contenu)</li>
 *     <li>La relation avec l'auteur</li>
 *     <li>Les liens hypermedia pour la navigation</li>
 * </ul>
 */
@Entity
public class Article extends RepresentationModel<Article> {
    /**
     * Identifiant unique de l'article.
     * Généré automatiquement par la base de données.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Titre de l'article.
     */
    private String title;
    
    /**
     * Contenu de l'article.
     */
    private String content;
    
    /**
     * Auteur de l'article.
     * Relation many-to-one avec l'entité Author.
     */
    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author author;

    /**
     * Constructeur par défaut requis par JPA.
     */
    public Article() {
    }

    /**
     * Constructeur avec paramètres pour créer un nouvel article.
     *
     * @param title Le titre de l'article
     * @param content Le contenu de l'article
     * @param author L'auteur de l'article
     */
    public Article(String title, String content, Author author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    /**
     * Récupère l'identifiant de l'article.
     *
     * @return L'identifiant de l'article
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant de l'article.
     *
     * @param id Le nouvel identifiant
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère le titre de l'article.
     *
     * @return Le titre de l'article
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit le titre de l'article.
     *
     * @param title Le nouveau titre
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Récupère le contenu de l'article.
     *
     * @return Le contenu de l'article
     */
    public String getContent() {
        return content;
    }

    /**
     * Définit le contenu de l'article.
     *
     * @param content Le nouveau contenu
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Récupère l'auteur de l'article.
     *
     * @return L'auteur de l'article
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Définit l'auteur de l'article.
     *
     * @param author Le nouvel auteur
     */
    public void setAuthor(Author author) {
        this.author = author;
    }
}