package fr.eletutour.ia.library.tools;

import fr.eletutour.ia.library.dto.BookView;
import fr.eletutour.ia.library.entity.BookType;
import fr.eletutour.ia.library.entity.LiteraryGenre;
import fr.eletutour.ia.library.service.LibraryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.Collections;
import java.util.List;

@Component
public class LibraryTools {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LibraryService libraryService;

    public LibraryTools(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Tool(name = "list_authors", description = "Lister tous les auteurs de la bibliothèque")
    public List<String> listAuthors() {
        logger.info("Tool called: list_authors");

        var authors = libraryService.listAuthors();

        logger.info("list_authors returned {} authors", authors.size());
        return authors;
    }

    @Tool(name = "list_books", description = "Lister tous les livres de la bibliothèque")
    public List<BookView> listBooks() {
        logger.info("Tool called: list_books");

        var books = libraryService.listBooks();

        logger.info("list_books returned {} books", books.size());
        return books;
    }

    @Tool(name = "find_books_by_author", description = "Trouver les livres d'un auteur")
    public List<BookView> findBooksByAuthor(
            @ToolParam(description = "Nom complet ou partiel de l'auteur") String authorName) {

        logger.info("Tool called: find_books_by_author with authorName='{}'", authorName);

        var books = libraryService.findBooksByAuthor(authorName);

        logger.info("find_books_by_author returned {} books", books.size());
        return books;
    }

    @Tool(name = "find_books_by_title_keyword", description = "Trouver des livres par mot-clé de titre")
    public List<BookView> findBooksByTitleKeyword(
            @ToolParam(description = "Mot-clé à rechercher dans le titre") String keyword) {

        logger.info("Tool called: find_books_by_title_keyword with keyword='{}'", keyword);

        var books = libraryService.findBooksByTitleKeyword(keyword);

        logger.info("find_books_by_title_keyword returned {} books", books.size());
        return books;
    }

    @Tool(name = "find_books_by_genre", description = "Trouver des livres par genre littéraire")
    public List<BookView> findBooksByGenre(
            @ToolParam(description = "Genre littéraire, ex: FANTASY, ROMANCE, SCIENCE_FICTION, THRILLER, PHILOSOPHY") String genre) {

        logger.info("Tool called: find_books_by_genre with genre='{}'", genre);

        try {
            LiteraryGenre normalizedGenre = LiteraryGenre.valueOf(normalizeEnumToken(genre));
            logger.info("Normalized genre: {}", normalizedGenre);

            var books = libraryService.findBooksByGenre(normalizedGenre);

            logger.info("find_books_by_genre returned {} books", books.size());
            return books;

        } catch (Exception e) {
            logger.warn("Invalid genre '{}' - returning empty result", genre);
            return Collections.emptyList();
        }
    }

    @Tool(name = "find_books_by_type", description = "Trouver des livres par type")
    public List<BookView> findBooksByType(
            @ToolParam(description = "Type de livre, ex: ROMAN, BANDE_DESSINEE, NOUVELLE, ESSAI") String type) {

        logger.info("Tool called: find_books_by_type with type='{}'", type);

        try {
            BookType normalizedType = BookType.valueOf(normalizeEnumToken(type));
            logger.info("Normalized type: {}", normalizedType);

            var books = libraryService.findBooksByType(normalizedType);

            logger.info("find_books_by_type returned {} books", books.size());
            return books;

        } catch (Exception e) {
            logger.warn("Invalid type '{}' - returning empty result", type);
            return Collections.emptyList();
        }
    }

    @Tool(name = "count_books", description = "Compter le nombre total de livres")
    public long countBooks() {
        logger.info("Tool called: count_books");

        var count = libraryService.countBooks();

        logger.info("count_books returned {}", count);
        return count;
    }

    private String normalizeEnumToken(String value) {
        if (value == null || value.isBlank()) {
            logger.warn("normalizeEnumToken called with empty value");
            return "";
        }

        String ascii = Normalizer.normalize(value, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        String normalized = ascii.trim()
                .toUpperCase()
                .replace('-', '_')
                .replace(' ', '_');

        logger.debug("Normalized enum token '{}' -> '{}'", value, normalized);

        return normalized;
    }
}
