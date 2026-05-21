package fr.eletutour.generictutorial.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object representing a book")
public record BookResponse(
        @Schema(description = "The unique identifier of the book", example = "1")
        Long id,
        @Schema(description = "The title of the book", example = "The Little Prince")
        String title,
        @Schema(description = "The author of the book")
        AuthorResponse author
) {}