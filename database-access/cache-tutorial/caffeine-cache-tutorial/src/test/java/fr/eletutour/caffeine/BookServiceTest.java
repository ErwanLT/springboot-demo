package fr.eletutour.caffeine;

import fr.eletutour.caffeine.model.Book;
import fr.eletutour.caffeine.repository.BookRepository;
import fr.eletutour.caffeine.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private CacheManager cacheManager;

    @Test
    void testFindBookByIsbn_shouldUseCache() {
        String isbn = "12345";
        Book book = new Book(isbn, "Test Book");

        // Mock the repository call
        when(bookRepository.findByIsbn(isbn)).thenReturn(book);

        // First call - should hit the repository
        Book result1 = bookService.findBookByIsbn(isbn);
        assertThat(result1).isEqualTo(book);
        verify(bookRepository, times(1)).findByIsbn(isbn);

        // Second call - should be served from cache
        Book result2 = bookService.findBookByIsbn(isbn);
        assertThat(result2).isEqualTo(book);
        // Verify that the repository method was NOT called a second time
        verify(bookRepository, times(1)).findByIsbn(isbn);

        // Optional: Verify cache content
        assertThat(cacheManager.getCache("books").get(isbn).get()).isEqualTo(book);
    }
}
