package fr.eletutour.generictutorial.controller;

import fr.eletutour.generictutorial.service.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class GenericCrudController<
        RESPONSE,
        CREATE,
        UPDATE,
        ID> {

    protected abstract CrudService<
                RESPONSE,
                CREATE,
                UPDATE,
                ID> service();

    @Operation(summary = "Get an entity by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entity found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema())), // SpringDoc resolves this via RESPONSE
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public RESPONSE getById(@PathVariable ID id) {
        return service().getById(id);
    }

    @Operation(summary = "Get all entities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of entities retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema()))) // SpringDoc resolves this via List<RESPONSE>
    })
    @GetMapping
    public List<RESPONSE> getAll() {
        return service().getAll();
    }

    @Operation(summary = "Create a new entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entity created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema())),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public RESPONSE create(@RequestBody CREATE request) {
        return service().create(request);
    }

    @Operation(summary = "Update an existing entity")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entity updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/{id}")
    public RESPONSE update(@PathVariable ID id,
                           @RequestBody UPDATE request) {
        return service().update(id, request);
    }

    @Operation(summary = "Delete an entity by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entity deleted"),
            @ApiResponse(responseCode = "404", description = "Entity not found",
                    content = @Content(mediaType = "application/problem+json",
                            schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {
        service().delete(id);
    }
}