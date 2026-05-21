package fr.eletutour.generictutorial.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response object representing an author")
public record AuthorResponse(
        @Schema(description = "The unique identifier of the author", example = "1")
        Long id,
        @Schema(description = "The name of the author", example = "Antoine de Saint-Exupéry")
        String name
) {}
