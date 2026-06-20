package fr.eletutour.controller.v2;

import fr.eletutour.model.Author;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Auteurs", description = "Gestion des auteurs")
@RestController
@RequestMapping("/api/v2/authors")
public class AuthorController {

    @GetMapping
    public List<Author> findAll() {
        return List.of(new Author(1L, "Douglas Adams", "Auteur du Guide du voyageur galactique"));
    }
}
