package fr.eletutour.reactive.controller;

import fr.eletutour.reactive.model.Post;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;


@RestController
@RequestMapping("/api")
public class ReactiveController {

    private final WebClient webClient = WebClient.create("https://jsonplaceholder.typicode.com");

    @GetMapping("/posts/{id}")
    public Mono<Post> getPost(@PathVariable String id) {
        return webClient.get()
                .uri("/posts/{id}", id)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new ResponseStatusException(response.statusCode(), "API call failed")))
                .bodyToMono(Post.class);
    }

    @GetMapping("/posts")
    public Flux<Post> getPosts() {
        return webClient.get()
                .uri("/posts")
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new ResponseStatusException(response.statusCode(), "API call failed")))
                .bodyToFlux(Post.class);
    }


    @GetMapping("/posts/{id}/enriched")
    public Mono<Post> getEnrichedPost(@PathVariable String id) {
        return getPost(id)
                .flatMap(post -> Mono.fromCallable(() -> enrichPostBlocking(post))
                        .subscribeOn(Schedulers.boundedElastic())
                );
    }


    private Post enrichPostBlocking(Post post) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new Post(
                post.userId(),
                post.id(),
                "[Enriched] " + post.title(),
                post.body()
        );
    }
}
