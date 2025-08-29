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
