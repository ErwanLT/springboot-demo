package fr.eletutour.designpattern.factory;


import fr.eletutour.designpattern.model.Book;
import fr.eletutour.designpattern.strategy.BookCoteStrategy;
import fr.eletutour.designpattern.strategy.BookCoteStrategyFactory;
import org.springframework.stereotype.Component;

@Component
public class BookFactory {

    private final BookCoteStrategyFactory coteStrategyFactory;

    public BookFactory(BookCoteStrategyFactory coteStrategyFactory) {
        this.coteStrategyFactory = coteStrategyFactory;
    }

    public Book createBook(long id, String title, String author, String type) {
        BookCoteStrategy strategy = coteStrategyFactory.getStrategy(type);
        String cote = strategy.generateCote(author);

        return new Book.Builder()
                .id(id)
                .title(title)
                .author(author)
                .type(type)
                .cote(cote)
                .build();
    }
}
