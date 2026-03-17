package fr.eletutour.client;

import fr.eletutour.books.consumer.model.Book;

import java.util.List;

public interface BookClient {
    List<Book> getBooksByAuthorId(Long authorId) throws Exception;

    Book createBook(Book book) throws Exception;
}
