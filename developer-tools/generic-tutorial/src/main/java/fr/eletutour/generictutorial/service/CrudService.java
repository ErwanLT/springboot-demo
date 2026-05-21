package fr.eletutour.generictutorial.service;

import java.util.List;

public interface CrudService<
        RESPONSE,
        CREATE,
        UPDATE,
        ID> {

    RESPONSE getById(ID id);

    List<RESPONSE> getAll();

    RESPONSE create(CREATE request);

    RESPONSE update(ID id, UPDATE request);

    void delete(ID id);
}