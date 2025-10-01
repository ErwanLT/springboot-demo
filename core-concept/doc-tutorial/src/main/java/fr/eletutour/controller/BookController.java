package fr.eletutour.controller;

import fr.eletutour.exception.BookException;
import fr.eletutour.model.Book;
import fr.eletutour.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Book management", description = "API pour la gestion des livres")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les livres", description = "Récupère la liste complète des livres")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des livres récupérée avec succès",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Book.class)))}),
            @ApiResponse(responseCode = "404", description = "Aucun livre trouvé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    public List<Book> getBooks () throws BookException {
        return bookService.findAllBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un livre par ID", description = "Récupère un livre spécifique en fonction de son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auteur trouvé avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Aucun livre avec cet ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    public Book getBookById(
            @Parameter(description = "Identifiant du livre à récupérer", required = true)
            @PathVariable Long id
    ) throws BookException {
        return bookService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un nouveau livre", description = "Ajoute un nouveau livre dans le système")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Auteur créé avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "400", description = "Données du livre invalides",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    public Book createBook(
            @Parameter(description = "Objet livre à créer", required = true)
            @RequestBody Book book
    ) {
        return bookService.createBook(book);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un livre", description = "Met à jour les informations d'un livre existant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auteur mis à jour avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Aucun livre avec cet ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "400", description = "Données du livre invalides",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    public Book updateBook(
            @Parameter(description = "Identifiant du livre à mettre à jour", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objet livre mis à jour", required = true)
            @RequestBody Book book
    ) throws BookException {
        book.setId(id);
        return bookService.updateBook(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un livre", description = "Supprime un livre en fonction de son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Auteur supprimé avec succès",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Aucun livre avec cet ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProblemDetail.class))})
    })
    public void deleteBook(
            @Parameter(description = "Identifiant du livre à supprimer", required = true)
            @PathVariable Long id
    ) throws BookException {
        bookService.deleteBook(id);
    }
}
