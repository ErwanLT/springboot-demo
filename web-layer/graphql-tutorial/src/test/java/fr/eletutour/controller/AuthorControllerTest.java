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

import fr.eletutour.model.Author;
import fr.eletutour.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@GraphQlTest(AuthorController.class)
public class AuthorControllerTest {
    @Autowired
    private GraphQlTester graphQlTester;

    @MockitoBean
    private AuthorService authorService;

    @Test
    void givenNewAuthorData_whenExecuteMutation_thenNewAuthorCreated() {

        when(authorService.createAuthor(anyString(), anyString())).thenReturn(mockAuthor());

        String documentName = "create_author";

        graphQlTester.documentName(documentName)
                .variable("name", "Erwan")
                .variable("bio", "J'aime le code")
                .execute()
                .path("createAuthor.id").hasValue()
                .path("createAuthor.name").entity(String.class).isEqualTo("Erwan")
                .path("createAuthor.bio").entity(String.class).isEqualTo("J'aime le code");
    }

    private Author mockAuthor() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Erwan");
        author.setBio("J'aime le code");
        return author;
    }
}
