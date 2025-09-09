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
package fr.eletutour.utils;

import org.springframework.hateoas.Link;

/**
 * Interface définissant les méthodes de construction des liens HATEOAS.
 * Cette interface fournit :
 * <ul>
 *     <li>La création de liens pour les auteurs (self, list, delete)</li>
 *     <li>La création de liens pour les articles (self, list, delete)</li>
 * </ul>
 */
public interface LinkBuilder {
    /**
     * Crée un lien vers un auteur spécifique.
     *
     * @param authorId L'identifiant de l'auteur
     * @return Le lien HATEOAS vers l'auteur
     */
    Link authorSelfLink(Long authorId);

    /**
     * Crée un lien vers la liste des auteurs.
     *
     * @return Le lien HATEOAS vers la liste des auteurs
     */
    Link authorsListLink();

    /**
     * Crée un lien pour supprimer un auteur.
     *
     * @param authorId L'identifiant de l'auteur à supprimer
     * @return Le lien HATEOAS pour la suppression
     */
    Link authorDeleteLink(Long authorId);

    /**
     * Crée un lien vers un article spécifique.
     *
     * @param articleId L'identifiant de l'article
     * @return Le lien HATEOAS vers l'article
     */
    Link articleSelfLink(Long articleId);

    /**
     * Crée un lien vers la liste des articles.
     *
     * @return Le lien HATEOAS vers la liste des articles
     */
    Link articlesListLink();

    /**
     * Crée un lien pour supprimer un article.
     *
     * @param articleId L'identifiant de l'article à supprimer
     * @return Le lien HATEOAS pour la suppression
     */
    Link articleDeleteLink(Long articleId);
}