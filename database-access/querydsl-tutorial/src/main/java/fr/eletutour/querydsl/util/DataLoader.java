package fr.eletutour.querydsl.util;

import fr.eletutour.querydsl.model.Book;
import fr.eletutour.querydsl.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final BookRepository bookRepository;

    public DataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Book book1 = new Book();
        book1.setTitle("The Lord of the Rings");
        book1.setAuthor("J.R.R. Tolkien");
        book1.setYear(1954);
        book1.setPublicationDate(LocalDate.of(1954, 7, 29));
        book1.setPrice(22.50);

        Book book2 = new Book();
        book2.setTitle("The Hobbit");
        book2.setAuthor("J.R.R. Tolkien");
        book2.setYear(1937);
        book2.setPublicationDate(LocalDate.of(1937, 9, 21));
        book2.setPrice(15.00);

        Book book3 = new Book();
        book3.setTitle("A Game of Thrones");
        book3.setAuthor("George R.R. Martin");
        book3.setYear(1996);
        book3.setPublicationDate(LocalDate.of(1996, 8, 6));
        book3.setPrice(25.00);

        Book book4 = new Book();
        book4.setTitle("The Hitchhiker's Guide to the Galaxy");
        book4.setAuthor("Douglas Adams");
        book4.setYear(1979);
        book4.setPublicationDate(LocalDate.of(1979, 10, 12));
        book4.setPrice(12.50);

        Book book5 = new Book();
        book5.setTitle("1984");
        book5.setAuthor("George Orwell");
        book5.setYear(1949);
        book5.setPublicationDate(LocalDate.of(1949, 6, 8));
        book5.setPrice(10.00);

        Book book6 = new Book();
        book6.setTitle("To Kill a Mockingbird");
        book6.setAuthor("Harper Lee");
        book6.setYear(1960);
        book6.setPublicationDate(LocalDate.of(1960, 7, 11));
        book6.setPrice(14.00);

        Book book7 = new Book();
        book7.setTitle("The Great Gatsby");
        book7.setAuthor("F. Scott Fitzgerald");
        book7.setYear(1925);
        book7.setPublicationDate(LocalDate.of(1925, 4, 10));
        book7.setPrice(11.50);

        Book book8 = new Book();
        book8.setTitle("Pride and Prejudice");
        book8.setAuthor("Jane Austen");
        book8.setYear(1813);
        book8.setPublicationDate(LocalDate.of(1813, 1, 28));
        book8.setPrice(9.00);

        Book book9 = new Book();
        book9.setTitle("The Catcher in the Rye");
        book9.setAuthor("J.D. Salinger");
        book9.setYear(1951);
        book9.setPublicationDate(LocalDate.of(1951, 7, 16));
        book9.setPrice(13.50);

        Book book10 = new Book();
        book10.setTitle("Brave New World");
        book10.setAuthor("Aldous Huxley");
        book10.setYear(1932);
        book10.setPublicationDate(LocalDate.of(1932, 1, 1));
        book10.setPrice(12.00);

        bookRepository.saveAll(Arrays.asList(book1, book2, book3, book4, book5, book6, book7, book8, book9, book10));
    }
}
