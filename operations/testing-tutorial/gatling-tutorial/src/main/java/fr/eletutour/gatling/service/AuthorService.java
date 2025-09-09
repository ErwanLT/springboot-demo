/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of gatling-tutorial.
 *
 * gatling-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * gatling-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with gatling-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.gatling.service;

import fr.eletutour.gatling.exception.AuthorNotFoundException;
import fr.eletutour.gatling.model.Author;
import fr.eletutour.gatling.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service de gestion des auteurs.
 * Ce service gère :
 * <ul>
 *     <li>La récupération des auteurs</li>
 *     <li>La création d'auteurs</li>
 *     <li>La gestion des erreurs liées aux auteurs</li>
 * </ul>
 */
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    /**
     * Constructeur du service.
     *
     * @param authorRepository Le repository pour l'accès aux données des auteurs
     */
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Récupère la liste de tous les auteurs.
     *
     * @return La liste complète des auteurs
     */
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Récupère un auteur par son identifiant.
     *
     * @param id L'identifiant de l'auteur
     * @return L'auteur correspondant à l'identifiant
     * @throws AuthorNotFoundException Si l'auteur n'est pas trouvé
     */
    public Author getAuthorById(Long id) throws AuthorNotFoundException {
        return authorRepository.findById(id).orElseThrow( () -> new AuthorNotFoundException("Author non trouvé pour l'id : " + id));
    }

    /**
     * Crée un nouvel auteur.
     *
     * @param author L'auteur à créer
     * @return L'auteur créé
     */
    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }
}