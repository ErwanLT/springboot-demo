package fr.eletutour.gatling.controller;

import fr.eletutour.gatling.exception.AuthorNotFoundException;
import fr.eletutour.gatling.model.Author;
import fr.eletutour.gatling.service.AuthorService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public List<Author> getAuthors() throws AuthorNotFoundException {
        return authorService.getAuthors();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) throws AuthorNotFoundException {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public Author createAuthor(
            @Parameter(description = "Objet auteur à créer", required = true)
            @RequestBody Author author
    ) {
        return authorService.createAuthor(author);
    }
}