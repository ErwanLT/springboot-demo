/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of AuthorApplication.
 *
 * AuthorApplication is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AuthorApplication is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AuthorApplication.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.service;

import fr.eletutour.book.consumer.api.BookManagementApi;
import fr.eletutour.books.consumer.model.Book;
import fr.eletutour.dao.entity.AuthorEntity;
import fr.eletutour.dao.repository.AuthorRepository;
import fr.eletutour.exception.AuthorException;
import fr.eletutour.mapper.AuthorMapper;
import fr.eletutour.model.Author;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);
    private final AuthorRepository authorRepository;
    private final BookManagementApi bookManagementApiClient;
    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    public AuthorService(AuthorRepository authorRepository, BookManagementApi bookManagementApiClient) {
        this.authorRepository = authorRepository;
        this.bookManagementApiClient = bookManagementApiClient;
    }

    /**
     * Récupère tous les auteurs avec leurs livres associés.
     * @return Liste des auteurs enrichis avec leurs livres
     */
    public List<Author> getAllAuthors() {
        logger.info("Récupération de tous les auteurs");
        List<AuthorEntity> authorEntities = authorRepository.findAll();
        List<Author> authors = authorMapper.toAuthors(authorEntities);

        return authors.stream()
                .map(this::enrichAuthorWithBooks)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un auteur par son ID.
     * @param id ID de l'auteur
     * @return Auteur correspondant
     * @throws AuthorException si l'auteur n'est pas trouvé
     */
    public Author getAuthorById(Long id) throws AuthorException {
        logger.info("Récupération de l'auteur avec l'ID {}", id);
        AuthorEntity authorEntity = authorRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Auteur non trouvé pour l'ID {}", id);
                    return new AuthorException(AuthorException.AuthorError.AUTHOR_NOT_FOUND, "Auteur non trouvé pour l'ID : " + id);
                });
        Author author = authorMapper.toAuthor(authorEntity);
        return enrichAuthorWithBooks(author);
    }

    /**
     * Crée un nouvel auteur et sauvegarde ses livres via l'API externe.
     * @param author Auteur à créer avec ses livres
     * @return Auteur créé avec les livres sauvegardés
     */
    public Author createAuthor(Author author) {
        logger.info("Création d'un nouvel auteur : {}", author.getName());

        // Étape 1 : Sauvegarde de l'auteur dans la base locale
        AuthorEntity authorEntity = authorMapper.toAuthorEntity(author);
        AuthorEntity savedEntity = authorRepository.save(authorEntity);
        Author createdAuthor = authorMapper.toAuthor(savedEntity);

        // Étape 2 : Sauvegarde des livres via l'API externe
        List<Book> books = author.getBooks();
        if (books != null && !books.isEmpty()) {
            logger.info("Sauvegarde de {} livres pour l'auteur avec l'ID {}", books.size(), createdAuthor.getId());
            List<Book> savedBooks = saveBooksForAuthor(books, createdAuthor.getId());
            createdAuthor.setBooks(savedBooks);
            logger.debug("Livres sauvegardés avec succès pour l'auteur {}", createdAuthor.getId());
        } else {
            logger.debug("Aucun livre à sauvegarder pour l'auteur {}", createdAuthor.getId());
            createdAuthor.setBooks(new ArrayList<>()); // Assure une liste vide si null
        }

        return createdAuthor;
    }

    /**
     * Sauvegarde une liste de livres pour un auteur donné via l'API externe.
     * @param books Liste des livres à sauvegarder
     * @param authorId ID de l'auteur associé
     * @return Liste des livres sauvegardés
     */
    private List<Book> saveBooksForAuthor(List<Book> books, Long authorId) {
        List<Book> savedBooks = new ArrayList<>();

        for (Book book : books) {
            try {
                book.setAuthorId(authorId);
                savedBooks.add(bookManagementApiClient.createBook(book));
            } catch (Exception e) {
                logger.warn("Échec de la sauvegarde du livre pour l'auteur {}, titre : {}", authorId, book.getName(), e);
            }
        }

        return savedBooks;
    }

    /**
     * Met à jour un auteur existant.
     * @param author Auteur avec les nouvelles données
     * @return Auteur mis à jour
     * @throws AuthorException si l'auteur n'existe pas
     */
    public Author updateAuthor(Author author) throws AuthorException {
        logger.info("Mise à jour de l'auteur avec l'ID {}", author.getId());
        if (author.getId() == null || !authorRepository.existsById(author.getId())) {
            logger.warn("Tentative de mise à jour d'un auteur inexistant avec l'ID {}", author.getId());
            throw new AuthorException(AuthorException.AuthorError.AUTHOR_NOT_FOUND, "Auteur non trouvé pour l'ID : " + author.getId());
        }
        AuthorEntity authorEntity = authorMapper.toAuthorEntity(author);
        AuthorEntity updatedEntity = authorRepository.save(authorEntity);
        return authorMapper.toAuthor(updatedEntity);
    }

    /**
     * Supprime un auteur par son ID.
     * @param id ID de l'auteur à supprimer
     * @throws AuthorException si l'auteur n'existe pas
     */
    public void deleteAuthor(Long id) throws AuthorException {
        logger.info("Suppression de l'auteur avec l'ID {}", id);
        if (!authorRepository.existsById(id)) {
            logger.warn("Tentative de suppression d'un auteur inexistant avec l'ID {}", id);
            throw new AuthorException(AuthorException.AuthorError.AUTHOR_NOT_FOUND, "Auteur non trouvé pour l'ID : " + id);
        }
        authorRepository.deleteById(id);
        logger.info("Auteur avec l'ID {} supprimé avec succès", id);
    }

    /**
     * Enrichit un auteur avec ses livres en appelant l'API externe.
     * @param author Auteur à enrichir
     * @return Auteur avec la liste des livres
     */
    private Author enrichAuthorWithBooks(Author author) {
        try {
            logger.debug("Récupération des livres pour l'auteur avec l'ID {}", author.getId());
            List<Book> books = bookManagementApiClient.getBooksByAuthorId(author.getId());
            author.setBooks(books);
            logger.debug("Ajout de {} livres à l'auteur avec l'ID {}", books.size(), author.getId());
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des livres pour l'auteur avec l'ID {}", author.getId(), e);
            throw new RuntimeException("Erreur lors de la récupération des livres pour l'auteur " + author.getId(), e);
        }
        return author;
    }
}