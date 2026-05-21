package fr.eletutour.generictutorial.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object to create a new book")
public record CreateBookRequest(
        @Schema(description = "The title of the book", example = "The Little Prince", requiredMode = Schema.RequiredMode.REQUIRED)
        String title,
        @Schema(description = "The ID of the author", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
        Long authorId
) {}