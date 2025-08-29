package fr.eletutour.service;

import fr.eletutour.dao.AuthorRepository;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Author;
import jakarta.annotation.PostConstruct;
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
        return authorRepository.findById(id).orElseThrow( () -> new AuthorNotFoundException("Author non trouvé pour l'id : " + id, id));
    }

    public Author createAuthor(String name, String bio) {
        Author author = new Author();
        author.setName(name);
        author.setBio(bio);
        return authorRepository.save(author);
    }

    @PostConstruct
    private void initAuthors(){
        createAuthor("J.K. Rowling", "J.K. Rowling is the author of the much-loved series of seven Harry Potter novels.");
        createAuthor("Stephen King", "Stephen King is the author of more than sixty books, all of them worldwide bestsellers.");
        createAuthor("Agatha Christie", "Agatha Christie is known throughout the world as the Queen of Crime.");
    }
}