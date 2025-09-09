/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of archunit-tutorial.
 *
 * archunit-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * archunit-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with archunit-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.archunit.controller;

import fr.eletutour.archunit.exception.AuthorNotFoundException;
import fr.eletutour.archunit.model.Author;
import fr.eletutour.archunit.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des auteurs.
 * Ce contrôleur expose les endpoints pour :
 * - Récupérer la liste de tous les auteurs
 * - Récupérer un auteur par son identifiant
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    /**
     * Constructeur du contrôleur.
     *
     * @param authorService Le service de gestion des auteurs
     */
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Récupère la liste de tous les auteurs.
     *
     * @return La liste des auteurs
     * @throws AuthorNotFoundException Si aucun auteur n'est trouvé
     */
    @GetMapping
    public List<Author> getAuthors() throws AuthorNotFoundException {
        return authorService.getAuthors();
    }

    /**
     * Récupère un auteur par son identifiant.
     *
     * @param id L'identifiant de l'auteur
     * @return L'auteur correspondant à l'identifiant
     * @throws AuthorNotFoundException Si l'auteur n'est pas trouvé
     */
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) throws AuthorNotFoundException {
        return authorService.getAuthorById(id);
    }
}