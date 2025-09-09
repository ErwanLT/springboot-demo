/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of BookApplication.
 *
 * BookApplication is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BookApplication is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BookApplication.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.controller;

import fr.eletutour.exception.BookException;
import fr.eletutour.exception.ErrorResponse;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    public List<Book> getBooks () throws BookException {
        return bookService.getBooks();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un livre par ID", description = "Récupère un livre spécifique en fonction de son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auteur trouvé avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "Aucun livre avec cet ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    public Book getBookById(
            @Parameter(description = "Identifiant du livre à récupérer", required = true)
            @PathVariable Long id
    ) throws BookException {
        return bookService.getBookById(id);
    }

    @GetMapping("/author/{id}")
    @Operation(summary = "Récupérer les livres par ID de l'auteur", description = "Récupère des livres spécifique en fonction de son author")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des livres récupérée avec succès",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Book.class)))}),
            @ApiResponse(responseCode = "404", description = "Aucun livre avec cet ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    public List<Book> getBooksByAuthorId(
            @Parameter(description = "Identifiant de l'auteur des livres à récupérer", required = true)
            @PathVariable Long id
    ) throws BookException {
        return bookService.getBooksByAuthor(id);
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
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
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
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Données du livre invalides",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
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
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    public void deleteBook(
            @Parameter(description = "Identifiant du livre à supprimer", required = true)
            @PathVariable Long id
    ) throws BookException {
        bookService.deleteBook(id);
    }
}