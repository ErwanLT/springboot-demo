package fr.eletutour.service;

import fr.eletutour.dao.AuthorRepository;
import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Author;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import fr.eletutour.utils.LinkBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private static final String AUTHOR_NOT_FOUND_MESSAGE = "Author non trouvé pour l'id : ";
    private final AuthorRepository authorRepository;
    private final LinkBuilder linkBuilder;


    public AuthorService(AuthorRepository authorRepository, LinkBuilder linkBuilder) {
        this.authorRepository = authorRepository;
        this.linkBuilder = linkBuilder;
    }

    public List<Author> getAuthors() throws AuthorNotFoundException, ArticleNotFoundException {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(this::addAuthorLinks)
                .map(this::addArticleLinks)
                .collect(Collectors.toList());
    }

    public Author getAuthorById(Long id) throws AuthorNotFoundException {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(AUTHOR_NOT_FOUND_MESSAGE + id));
    }

    public void createAuthor(String name, String bio) {
        Author author = new Author();
        author.setName(name);
        author.setBio(bio);
        authorRepository.save(author);
    }

    @PostConstruct
    private void initAuthors(){
        createAuthor("J.K. Rowling", "J.K. Rowling is the author of the much-loved series of seven Harry Potter novels.");
        createAuthor("Stephen King", "Stephen King is the author of more than sixty books, all of them worldwide bestsellers.");
        createAuthor("Agatha Christie", "Agatha Christie is known throughout the world as the Queen of Crime.");
    }

    private Author addAuthorLinks(Author author) throws AuthorNotFoundException, ArticleNotFoundException {
        author.add(linkBuilder.authorSelfLink(author.getId()));
        author.add(linkBuilder.authorsListLink());
        return author;
    }

    private Author addArticleLinks(Author author) {
        author.getArticles().forEach(article -> {
            article.add(linkBuilder.articleSelfLink(article.getId()));
            article.add(linkBuilder.articlesListLink());
        });
        return author;
    }
}