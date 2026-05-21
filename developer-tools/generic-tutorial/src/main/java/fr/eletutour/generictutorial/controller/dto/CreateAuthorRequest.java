package fr.eletutour.generictutorial.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request object to create a new author")
public record CreateAuthorRequest(
        @Schema(description = "The name of the author", example = "Antoine de Saint-Exupéry", requiredMode = Schema.RequiredMode.REQUIRED)
        String name
) {}
