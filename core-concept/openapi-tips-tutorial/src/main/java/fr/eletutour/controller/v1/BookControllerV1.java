package fr.eletutour.controller.v1;

import fr.eletutour.model.Book;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Ancienne version de l'API Livres.
 * Maintenue pour les clients qui n'ont pas encore migré vers /api/v2.
 */
@Tag(name = "Catalogue", description = "Gestion du catalogue de livres (v1, dépréciée)")
@RestController
@RequestMapping("/api/v1/books")
public class BookControllerV1 {

    @GetMapping
    public List<Book> findAll() {
        return List.of(new Book(1L, "Le Guide du voyageur galactique", "978-2-070-41769-5"));
    }
}
