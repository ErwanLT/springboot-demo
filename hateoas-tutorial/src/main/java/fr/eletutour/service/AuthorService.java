package fr.eletutour.service;

import fr.eletutour.dao.AuthorRepository;
import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Author;
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
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(AUTHOR_NOT_FOUND_MESSAGE + id));
        return addAuthorLinks(addArticleLinks(author)); // Ajout des liens
    }

    public void createAuthor(String name, String bio) {
        Author author = new Author();
        author.setName(name);
        author.setBio(bio);
        authorRepository.save(author);
    }

    private Author addAuthorLinks(Author author) throws AuthorNotFoundException, ArticleNotFoundException {
        author.add(linkBuilder.authorSelfLink(author.getId()));
        author.add(linkBuilder.authorsListLink());
        author.add(linkBuilder.authorDeleteLink(author.getId()));
        return author;
    }

    private Author addArticleLinks(Author author) {
        author.getArticles().forEach(article -> {
            article.add(linkBuilder.articleSelfLink(article.getId()));
            article.add(linkBuilder.articlesListLink());
            article.add(linkBuilder.articleDeleteLink(article.getId()));
        });
        return author;
    }

    public void deleteAuthor(Long id) {
        var author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.delete(author.get());
        } else {
            throw new AuthorNotFoundException(AUTHOR_NOT_FOUND_MESSAGE + id);
        }
    }
}