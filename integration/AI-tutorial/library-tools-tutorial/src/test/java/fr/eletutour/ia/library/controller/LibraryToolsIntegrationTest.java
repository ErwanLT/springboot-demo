package fr.eletutour.ia.library.controller;

import fr.eletutour.ia.library.dto.BookView;
import fr.eletutour.ia.library.entity.BookType;
import fr.eletutour.ia.library.entity.LiteraryGenre;
import fr.eletutour.ia.library.service.LibraryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LibraryToolsIntegrationTest {

    @Autowired
    private LibraryService libraryService;

    @Test
    void shouldFindBooksByAuthor() {
        List<BookView> books = libraryService.findBooksByAuthor("Martin Fowler");

        assertThat(books).extracting(BookView::title)
                .contains("Refactoring", "Patterns of Enterprise Application Architecture");
    }

    @Test
    void shouldFindBooksByTypeAndGenre() {
        List<BookView> romans = libraryService.findBooksByType(BookType.ROMAN);
        List<BookView> fantasy = libraryService.findBooksByGenre(LiteraryGenre.FANTASY);
        List<BookView> bandesDessinees = libraryService.findBooksByType(BookType.BANDE_DESSINEE);

        assertThat(romans).extracting(BookView::title)
                .contains("Le Hobbit", "Dune", "Orgueil et Préjugés");
        assertThat(fantasy).extracting(BookView::title)
                .contains("Le Hobbit", "Le Seigneur des Anneaux", "American Gods");
        assertThat(bandesDessinees).extracting(BookView::title)
                .contains("Watchmen", "Persepolis");
    }

    @Test
    void shouldCountBooksFromSeedData() {
        assertThat(libraryService.countBooks()).isEqualTo(60);
    }
}
