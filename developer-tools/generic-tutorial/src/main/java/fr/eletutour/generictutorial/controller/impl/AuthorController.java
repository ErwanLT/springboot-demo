package fr.eletutour.generictutorial.controller.impl;

import fr.eletutour.generictutorial.controller.GenericCrudController;
import fr.eletutour.generictutorial.controller.dto.AuthorResponse;
import fr.eletutour.generictutorial.controller.dto.CreateAuthorRequest;
import fr.eletutour.generictutorial.service.CrudService;
import fr.eletutour.generictutorial.service.impl.AuthorService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authors")
@Tag(name = "Authors", description = "Operations related to authors")
public class AuthorController extends GenericCrudController<AuthorResponse, CreateAuthorRequest, CreateAuthorRequest, Long> {

    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @Override
    protected CrudService<AuthorResponse, CreateAuthorRequest, CreateAuthorRequest, Long> service() {
        return service;
    }
}
