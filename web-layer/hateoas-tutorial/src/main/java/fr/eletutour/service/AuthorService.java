package fr.eletutour.service;

import fr.eletutour.dao.AuthorRepository;
import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Author;
import fr.eletutour.utils.LinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service de gestion des auteurs.
 * Ce service fournit :
 * <ul>
 *     <li>La récupération des auteurs</li>
 *     <li>La création d'auteurs</li>
 *     <li>La suppression d'auteurs</li>
 *     <li>L'ajout de liens HATEOAS</li>
 * </ul>
 */
@Service
public class AuthorService {
    private static final String AUTHOR_NOT_FOUND_MESSAGE = "Author non trouvé pour l'id : ";
    private final AuthorRepository authorRepository;
    private final LinkBuilder linkBuilder;

    /**
     * Constructeur du service.
     *
     * @param authorRepository Le repository des auteurs
     * @param linkBuilder Le constructeur de liens HATEOAS
     */
    public AuthorService(AuthorRepository authorRepository, LinkBuilder linkBuilder) {
        this.authorRepository = authorRepository;
        this.linkBuilder = linkBuilder;
    }

    /**
     * Récupère la liste de tous les auteurs avec leurs liens HATEOAS.
     *
     * @return La liste des auteurs avec leurs liens
     * @throws AuthorNotFoundException Si aucun auteur n'est trouvé
     * @throws ArticleNotFoundException Si un article associé n'est pas trouvé
     */
    public List<Author> getAuthors() throws AuthorNotFoundException, ArticleNotFoundException {
        List<Author> authors = authorRepository.findAll();
        return authors.stream()
                .map(this::addAuthorLinks)
                .map(this::addArticleLinks)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un auteur par son identifiant avec ses liens HATEOAS.
     *
     * @param id L'identifiant de l'auteur
     * @return L'auteur trouvé avec ses liens
     * @throws AuthorNotFoundException Si l'auteur n'est pas trouvé
     */
    public Author getAuthorById(Long id) throws AuthorNotFoundException {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(AUTHOR_NOT_FOUND_MESSAGE + id));
        return addAuthorLinks(addArticleLinks(author)); // Ajout des liens
    }

    /**
     * Crée un nouvel auteur.
     *
     * @param name Le nom de l'auteur
     * @param bio La biographie de l'auteur
     */
    public void createAuthor(String name, String bio) {
        Author author = new Author();
        author.setName(name);
        author.setBio(bio);
        authorRepository.save(author);
    }

    /**
     * Ajoute les liens HATEOAS à un auteur.
     *
     * @param author L'auteur à enrichir
     * @return L'auteur avec ses liens
     * @throws AuthorNotFoundException Si l'auteur n'est pas trouvé
     * @throws ArticleNotFoundException Si un article associé n'est pas trouvé
     */
    private Author addAuthorLinks(Author author) throws AuthorNotFoundException, ArticleNotFoundException {
        author.add(linkBuilder.authorSelfLink(author.getId()));
        author.add(linkBuilder.authorsListLink());
        author.add(linkBuilder.authorDeleteLink(author.getId()));
        return author;
    }

    /**
     * Ajoute les liens HATEOAS aux articles d'un auteur.
     *
     * @param author L'auteur dont les articles doivent être enrichis
     * @return L'auteur avec les liens de ses articles
     */
    private Author addArticleLinks(Author author) {
        author.getArticles().forEach(article -> {
            article.add(linkBuilder.articleSelfLink(article.getId()));
            article.add(linkBuilder.articlesListLink());
            article.add(linkBuilder.articleDeleteLink(article.getId()));
        });
        return author;
    }

    /**
     * Supprime un auteur par son identifiant.
     *
     * @param id L'identifiant de l'auteur à supprimer
     * @throws AuthorNotFoundException Si l'auteur n'est pas trouvé
     */
    public void deleteAuthor(Long id) {
        var author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.delete(author.get());
        } else {
            throw new AuthorNotFoundException(AUTHOR_NOT_FOUND_MESSAGE + id);
        }
    }
}