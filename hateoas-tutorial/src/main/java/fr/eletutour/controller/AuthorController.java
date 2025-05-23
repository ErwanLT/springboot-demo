package fr.eletutour.controller;

import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Author;
import fr.eletutour.service.AuthorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour la gestion des auteurs.
 * Ce contrôleur fournit :
 * <ul>
 *     <li>La récupération de tous les auteurs</li>
 *     <li>La récupération d'un auteur par son ID</li>
 *     <li>La suppression d'un auteur</li>
 *     <li>Des liens HATEOAS pour la navigation</li>
 * </ul>
 */
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    /**
     * Constructeur du contrôleur.
     *
     * @param authorService Le service de gestion des auteurs
     */
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Récupère la liste de tous les auteurs.
     * Les réponses incluent des liens HATEOAS pour la navigation.
     *
     * @return La liste des auteurs
     * @throws AuthorNotFoundException Si aucun auteur n'est trouvé
     * @throws ArticleNotFoundException Si un article associé n'est pas trouvé
     */
    @GetMapping
    public List<Author> getAuthors() throws AuthorNotFoundException, ArticleNotFoundException {
        return authorService.getAuthors();
    }

    /**
     * Récupère un auteur par son identifiant.
     * La réponse inclut des liens HATEOAS pour la navigation.
     *
     * @param id L'identifiant de l'auteur
     * @return L'auteur trouvé
     * @throws AuthorNotFoundException Si l'auteur n'est pas trouvé
     */
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) throws AuthorNotFoundException {
        return authorService.getAuthorById(id);
    }

    /**
     * Supprime un auteur par son identifiant.
     *
     * @param id L'identifiant de l'auteur à supprimer
     * @return Une réponse vide avec le code 204 (No Content)
     * @throws AuthorNotFoundException Si l'auteur n'est pas trouvé
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) throws AuthorNotFoundException {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }
}
