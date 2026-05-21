package fr.eletutour.generictutorial.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object to update an existing book")
public record UpdateBookRequest(
        @Schema(description = "The new title of the book", example = "1984", requiredMode = Schema.RequiredMode.REQUIRED)
        String title
) {}