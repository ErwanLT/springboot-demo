/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of AuthorApplication.
 *
 * AuthorApplication is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AuthorApplication is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AuthorApplication.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.controller;

import fr.eletutour.exception.AuthorException;
import fr.eletutour.exception.ErrorResponse;
import fr.eletutour.model.Author;
import fr.eletutour.service.AuthorService;
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
@RequestMapping("/authors")
@Tag(name = "Author Management", description = "API pour la gestion des auteurs")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @Operation(summary = "Récupérer tous les auteurs", description = "Récupère la liste complète des auteurs")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des auteurs récupérée avec succès",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Author.class)))}),
            @ApiResponse(responseCode = "404", description = "Aucun auteur trouvé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    public List<Author> getAuthors() throws AuthorException {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un auteur par ID", description = "Récupère un auteur spécifique en fonction de son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auteur trouvé avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "404", description = "Aucun auteur avec cet ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    public Author getAuthorById(
            @Parameter(description = "Identifiant de l'auteur à récupérer", required = true)
            @PathVariable Long id
    ) throws AuthorException {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un nouvel auteur", description = "Ajoute un nouvel auteur dans le système")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Auteur créé avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "400", description = "Données de l'auteur invalides",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    public Author createAuthor(
            @Parameter(description = "Objet auteur à créer", required = true)
            @RequestBody Author author
    ) {
        return authorService.createAuthor(author);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un auteur", description = "Met à jour les informations d'un auteur existant")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Auteur mis à jour avec succès",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Author.class))}),
            @ApiResponse(responseCode = "404", description = "Aucun auteur avec cet ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Données de l'auteur invalides",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    public Author updateAuthor(
            @Parameter(description = "Identifiant de l'auteur à mettre à jour", required = true)
            @PathVariable Long id,
            @Parameter(description = "Objet auteur mis à jour", required = true)
            @RequestBody Author author
    ) throws AuthorException {
        author.setId(id);
        return authorService.updateAuthor(author);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un auteur", description = "Supprime un auteur en fonction de son identifiant")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Auteur supprimé avec succès",
                    content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", description = "Aucun auteur avec cet ID",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erreur lors du traitement",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})
    })
    public void deleteAuthor(
            @Parameter(description = "Identifiant de l'auteur à supprimer", required = true)
            @PathVariable Long id
    ) throws AuthorException {
        authorService.deleteAuthor(id);
    }
}