package fr.eletutour.archunit.service;

import fr.eletutour.archunit.exception.AuthorNotFoundException;
import fr.eletutour.archunit.model.Author;
import fr.eletutour.archunit.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service de gestion des auteurs.
 * Ce service gère :
 * - La récupération des auteurs
 * - La création d'auteurs
 * - La gestion des erreurs liées aux auteurs
 */
@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    /**
     * Constructeur du service.
     *
     * @param authorRepository Le repository pour l'accès aux données des auteurs
     */
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Récupère la liste de tous les auteurs.
     *
     * @return La liste complète des auteurs
     */
    public List<Author> getAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Récupère un auteur par son identifiant.
     *
     * @param id L'identifiant de l'auteur
     * @return L'auteur correspondant à l'identifiant
     * @throws AuthorNotFoundException Si l'auteur n'est pas trouvé
     */
    public Author getAuthorById(Long id) throws AuthorNotFoundException {
        return authorRepository.findById(id).orElseThrow( () -> new AuthorNotFoundException("Author non trouvé pour l'id : " + id));
    }

    /**
     * Crée un nouvel auteur.
     *
     * @param name Le nom de l'auteur
     * @param bio La biographie de l'auteur
     * @return L'auteur créé
     */
    public Author createAuthor(String name, String bio) {
        Author author = new Author();
        author.setName(name);
        author.setBio(bio);
        return authorRepository.save(author);
    }
}