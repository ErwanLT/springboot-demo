package com.example.r2dbctutorial.router;

import com.example.r2dbctutorial.entity.Person;
import com.example.r2dbctutorial.repository.PersonRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class PersonHandler {

    private final PersonRepository personRepository;

    public PersonHandler(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Mono<ServerResponse> getAllPersons(ServerRequest request) {
        return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(personRepository.findAll(), Person.class);
    }

    public Mono<ServerResponse> getPersonById(ServerRequest request) {
        int personId = Integer.parseInt(request.pathVariable("id"));
        return personRepository.findById(personId)
                .flatMap(person -> ServerResponse.ok().contentType(APPLICATION_JSON).bodyValue(person))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createPerson(ServerRequest request) {
        Mono<Person> personMono = request.bodyToMono(Person.class);
        return personMono.flatMap(person ->
                ServerResponse.ok().contentType(APPLICATION_JSON)
                        .body(personRepository.save(person), Person.class));
    }
}
