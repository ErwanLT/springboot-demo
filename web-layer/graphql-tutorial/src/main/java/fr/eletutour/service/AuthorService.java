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
package fr.eletutour.service;

import fr.eletutour.dao.AuthorRepository;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) throws AuthorNotFoundException {
        return authorRepository.findById(id).orElseThrow( () -> new AuthorNotFoundException("Author non trouvé pour l'id : " + id));
    }

    public Author createAuthor(String name, String bio) {
        Author author = new Author();
        author.setName(name);
        author.setBio(bio);
        return authorRepository.save(author);
    }
}