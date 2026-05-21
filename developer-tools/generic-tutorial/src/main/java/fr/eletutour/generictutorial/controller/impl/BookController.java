package fr.eletutour.generictutorial.controller.impl;

import fr.eletutour.generictutorial.controller.GenericCrudController;
import fr.eletutour.generictutorial.controller.dto.BookResponse;
import fr.eletutour.generictutorial.controller.dto.CreateBookRequest;
import fr.eletutour.generictutorial.controller.dto.UpdateBookRequest;
import fr.eletutour.generictutorial.service.CrudService;
import fr.eletutour.generictutorial.service.impl.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
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