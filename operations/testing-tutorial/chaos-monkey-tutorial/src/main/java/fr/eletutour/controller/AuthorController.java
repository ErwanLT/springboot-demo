/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of chaos-monkey-tutorial.
 *
 * chaos-monkey-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * chaos-monkey-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with chaos-monkey-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.controller;

import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Author;
import fr.eletutour.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAuthors() throws AuthorNotFoundException, ArticleNotFoundException {
        return authorService.getAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) throws AuthorNotFoundException {
        return authorService.getAuthorById(id);
    }
}
