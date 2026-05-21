package fr.eletutour.generictutorial.controller;

import fr.eletutour.generictutorial.service.CrudService;
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

    @GetMapping("/{id}")
    public RESPONSE getById(@PathVariable ID id) {
        return service().getById(id);
    }

    @GetMapping
    public List<RESPONSE> getAll() {
        return service().getAll();
    }

    @PostMapping
    public RESPONSE create(@RequestBody CREATE request) {
        return service().create(request);
    }

    @PutMapping("/{id}")
    public RESPONSE update(@PathVariable ID id,
                           @RequestBody UPDATE request) {
        return service().update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable ID id) {
        service().delete(id);
    }
}