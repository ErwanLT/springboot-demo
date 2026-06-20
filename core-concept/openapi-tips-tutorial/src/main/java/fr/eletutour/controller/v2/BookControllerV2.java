package fr.eletutour.controller.v2;

import fr.eletutour.model.Book;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Catalogue", description = "Gestion du catalogue de livres")
@RestController
@RequestMapping("/api/v2/books")
public class BookControllerV2 {

    @GetMapping
    public List<Book> findAll() {
        return List.of(
                new Book(1L, "Le Guide du voyageur galactique", "978-2-070-41769-5"),
                new Book(2L, "Hook", "978-2-070-00000-0")
        );
    }

    @GetMapping("/{id}")
    public Book findById(@PathVariable Long id) {
        return new Book(id, "Le Guide du voyageur galactique", "978-2-070-41769-5");
    }

    @PostMapping
    public Book create(@RequestBody Book book) {
        return book;
    }
}
