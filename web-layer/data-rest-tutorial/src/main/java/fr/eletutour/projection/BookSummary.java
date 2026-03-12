package fr.eletutour.projection;

import fr.eletutour.model.Author;
import fr.eletutour.model.Book;
import org.springframework.data.rest.core.config.Projection;

@Projection(name = "summary", types = Book.class)
public interface BookSummary {
    String getTitle();
    Author getAuthor();
}
