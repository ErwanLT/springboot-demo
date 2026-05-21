package fr.eletutour.generictutorial.controller.impl;

import fr.eletutour.generictutorial.controller.GenericCrudController;
import fr.eletutour.generictutorial.controller.dto.BookResponse;
import fr.eletutour.generictutorial.controller.dto.CreateBookRequest;
import fr.eletutour.generictutorial.controller.dto.UpdateBookRequest;
import fr.eletutour.generictutorial.service.CrudService;
import fr.eletutour.generictutorial.service.impl.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@Tag(name = "Books", description = "Operations related to books")
public class BookController extends GenericCrudController<
        BookResponse,
        CreateBookRequest,
        UpdateBookRequest,
        Long> {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @Override
    protected CrudService<
                BookResponse,
                CreateBookRequest,
                UpdateBookRequest,
                Long> service() {
        return service;
    }
}