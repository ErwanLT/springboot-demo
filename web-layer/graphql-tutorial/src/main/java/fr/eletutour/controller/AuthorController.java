/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of graphql-tutorial.
 *
 * graphql-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * graphql-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with graphql-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.controller;

import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Author;
import fr.eletutour.service.AuthorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @MutationMapping
    public Author createAuthor(@Argument String name, @Argument String bio) {
        return authorService.createAuthor(name, bio);
    }

    @QueryMapping
    public List<Author> getAuthors() {
        return authorService.getAuthors();
    }

    @QueryMapping
    public Author getAuthorById(@Argument Long id) throws AuthorNotFoundException {
        return authorService.getAuthorById(id);
    }
}
