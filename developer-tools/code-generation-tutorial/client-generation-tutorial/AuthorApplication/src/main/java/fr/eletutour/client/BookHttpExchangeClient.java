package fr.eletutour.client;

import fr.eletutour.books.consumer.model.Book;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@HttpExchange
public interface BookHttpExchangeClient {

    @GetExchange("/books/author/{id}")
    List<Book> getBooksByAuthorId(@PathVariable("id") Long authorId);

    @PostExchange("/books")
    Book createBook(@RequestBody Book book);
}
