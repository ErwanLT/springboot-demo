package fr.eletutour.client;

import fr.eletutour.books.consumer.model.Book;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ConditionalOnProperty(name = "books.client", havingValue = "http-exchange")
public class HttpExchangeBookClientAdapter implements BookClient {

    private final BookHttpExchangeClient bookHttpExchangeClient;

    public HttpExchangeBookClientAdapter(BookHttpExchangeClient bookHttpExchangeClient) {
        this.bookHttpExchangeClient = bookHttpExchangeClient;
    }

    @Override
    public List<Book> getBooksByAuthorId(Long authorId) {
        return bookHttpExchangeClient.getBooksByAuthorId(authorId);
    }

    @Override
    public Book createBook(Book book) {
        return bookHttpExchangeClient.createBook(book);
    }
}
