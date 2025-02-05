package fr.eletutour.service;

import fr.eletutour.dao.AuthorRepository;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Author;
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

    public Author createAuthor(String name, String bio) {
        Author author = new Author();
        author.setName(name);
        author.setBio(bio);
        return authorRepository.save(author);
    }
}