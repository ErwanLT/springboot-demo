package com.example.r2dbctutorial.router;

import com.example.r2dbctutorial.entity.Person;
import com.example.r2dbctutorial.repository.PersonRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class PersonHandler {

    private final PersonRepository personRepository;

    public PersonHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Mono<ServerResponse> getAllPersons(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(personRepository.findAll(), Person.class);
    }

    public Mono<ServerResponse> getPersonById(ServerRequest request) {
        String idStr = request.pathVariable("id");
        int personId;

        try {
            personId = Integer.parseInt(idStr);
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest()
                    .bodyValue("Invalid ID format: " + idStr);
        }

        return personRepository.findById(personId)
                .flatMap(person -> ServerResponse.ok()
                        .contentType(APPLICATION_JSON)
                        .bodyValue(person))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createPerson(ServerRequest request) {
        return request.bodyToMono(Person.class)
                .flatMap(personRepository::save)
                .flatMap(savedPerson ->
                        ServerResponse.created(URI.create("/api/persons/" + savedPerson.getId()))
                                .contentType(APPLICATION_JSON)
                                .bodyValue(savedPerson)
                );
    }
}
