package com.example.r2dbctutorial.router;

import com.example.r2dbctutorial.entity.Person;
import com.example.r2dbctutorial.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebFluxTest
@Import({RouterConfig.class, PersonHandler.class})
public class PersonRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockitoBean
    private PersonRepository personRepository;

    @Test
    void getAllPersons() {
        when(personRepository.findAll()).thenReturn(Flux.just(new Person(1, "John", 30), new Person(2, "Jane", 25)));

        webTestClient.get().uri("/api/persons")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Person.class).hasSize(2);
    }

    @Test
    void getPersonById() {
        when(personRepository.findById(1)).thenReturn(Mono.just(new Person(1, "John", 30)));

        webTestClient.get().uri("/api/persons/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class);
    }

    @Test
    void createPerson() {
        Person person = new Person("John", 30);
        when(personRepository.save(any(Person.class))).thenReturn(Mono.just(new Person(1, person.getName(), person.getAge())));

        webTestClient.post().uri("/api/persons")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(person), Person.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Person.class);
    }
}
