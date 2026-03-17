package fr.eletutour.client;

import fr.eletutour.book.consumer.api.BookManagementApi;
import fr.eletutour.books.consumer.model.Book;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(name = "books.client", havingValue = "feign", matchIfMissing = true)
public class FeignBookClientAdapter implements BookClient {

    private final BookManagementApi bookManagementApi;

    public FeignBookClientAdapter(BookManagementApi bookManagementApi) {
        this.bookManagementApi = bookManagementApi;
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) throws Exception {
        return bookManagementApi.getBooksByAuthorId(authorId);
    }

    @Override
    public Book createBook(Book book) throws Exception {
        return bookManagementApi.createBook(book);
    }
}
