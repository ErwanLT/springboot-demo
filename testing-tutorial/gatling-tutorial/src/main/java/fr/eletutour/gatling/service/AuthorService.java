package fr.eletutour.gatling.service;

import fr.eletutour.gatling.exception.AuthorNotFoundException;
import fr.eletutour.gatling.model.Author;
import fr.eletutour.gatling.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) throws AuthorNotFoundException {
        return authorRepository.findById(id).orElseThrow( () -> new AuthorNotFoundException("Author non trouvé pour l'id : " + id));
    }

    public Author createAuthor(Author author) {
        return authorRepository.save(author);
    }
}