package fr.eletutour.controller;

import fr.eletutour.model.Author;
import fr.eletutour.service.AuthorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class RestControllerDemo {

    private final AuthorService authorService;

    public RestControllerDemo(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAll(){
        return authorService.getAuthors();
    }
}
