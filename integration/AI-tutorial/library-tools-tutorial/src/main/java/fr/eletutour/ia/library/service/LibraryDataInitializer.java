package fr.eletutour.ia.library.service;

import fr.eletutour.ia.library.entity.Author;
import fr.eletutour.ia.library.entity.Book;
import fr.eletutour.ia.library.entity.BookType;
import fr.eletutour.ia.library.entity.LiteraryGenre;
import fr.eletutour.ia.library.repository.AuthorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class LibraryDataInitializer {

    @Bean
    CommandLineRunner initLibraryData(AuthorRepository authorRepository) {
        return args -> {
            if (authorRepository.count() > 0) {
                return;
            }

            AtomicInteger isbnSequence = new AtomicInteger(1);

            authorRepository.save(seedMartinFowler(isbnSequence));
            authorRepository.save(seedRobertMartin(isbnSequence));
            authorRepository.save(seedEricEvans(isbnSequence));
            authorRepository.save(seedTolkien(isbnSequence));
            authorRepository.save(seedHerbert(isbnSequence));
            authorRepository.save(seedChristie(isbnSequence));
            authorRepository.save(seedLeGuin(isbnSequence));
            authorRepository.save(seedAlanMoore(isbnSequence));
            authorRepository.save(seedAusten(isbnSequence));
            authorRepository.save(seedSatrapi(isbnSequence));
            authorRepository.save(seedGaiman(isbnSequence));

            for (int i = 1; i <= 10; i++) {
                Author demoAuthor = new Author("Auteur Démo %02d".formatted(i));
                for (int j = 1; j <= 4; j++) {
                    LiteraryGenre genre = LiteraryGenre.values()[(i + j) % LiteraryGenre.values().length];
                    BookType type = BookType.values()[(i + j) % BookType.values().length];
                    demoAuthor.addBook(new Book(
                            "Collection Démo %02d - Volume %d".formatted(i, j),
                            nextIsbn(isbnSequence),
                            genre,
                            type
                    ));
                }
                authorRepository.save(demoAuthor);
            }
        };
    }

    private Author seedMartinFowler(AtomicInteger isbnSequence) {
        Author author = new Author("Martin Fowler");
        author.addBook(new Book("Refactoring", nextIsbn(isbnSequence), LiteraryGenre.PHILOSOPHY, BookType.ESSAI));
        author.addBook(new Book("Patterns of Enterprise Application Architecture", nextIsbn(isbnSequence), LiteraryGenre.PHILOSOPHY, BookType.ESSAI));
        return author;
    }

    private Author seedRobertMartin(AtomicInteger isbnSequence) {
        Author author = new Author("Robert C. Martin");
        author.addBook(new Book("Clean Code", nextIsbn(isbnSequence), LiteraryGenre.PHILOSOPHY, BookType.ESSAI));
        author.addBook(new Book("Clean Architecture", nextIsbn(isbnSequence), LiteraryGenre.PHILOSOPHY, BookType.ESSAI));
        return author;
    }

    private Author seedEricEvans(AtomicInteger isbnSequence) {
        Author author = new Author("Eric Evans");
        author.addBook(new Book("Domain-Driven Design", nextIsbn(isbnSequence), LiteraryGenre.PHILOSOPHY, BookType.ESSAI));
        return author;
    }

    private Author seedTolkien(AtomicInteger isbnSequence) {
        Author author = new Author("J.R.R. Tolkien");
        author.addBook(new Book("Le Hobbit", nextIsbn(isbnSequence), LiteraryGenre.FANTASY, BookType.ROMAN));
        author.addBook(new Book("Le Seigneur des Anneaux", nextIsbn(isbnSequence), LiteraryGenre.FANTASY, BookType.ROMAN));
        return author;
    }

    private Author seedHerbert(AtomicInteger isbnSequence) {
        Author author = new Author("Frank Herbert");
        author.addBook(new Book("Dune", nextIsbn(isbnSequence), LiteraryGenre.SCIENCE_FICTION, BookType.ROMAN));
        author.addBook(new Book("Le Messie de Dune", nextIsbn(isbnSequence), LiteraryGenre.SCIENCE_FICTION, BookType.ROMAN));
        return author;
    }

    private Author seedChristie(AtomicInteger isbnSequence) {
        Author author = new Author("Agatha Christie");
        author.addBook(new Book("Le Crime de l'Orient-Express", nextIsbn(isbnSequence), LiteraryGenre.THRILLER, BookType.ROMAN));
        author.addBook(new Book("Dix petits nègres", nextIsbn(isbnSequence), LiteraryGenre.THRILLER, BookType.ROMAN));
        return author;
    }

    private Author seedLeGuin(AtomicInteger isbnSequence) {
        Author author = new Author("Ursula K. Le Guin");
        author.addBook(new Book("La Main gauche de la nuit", nextIsbn(isbnSequence), LiteraryGenre.SCIENCE_FICTION, BookType.ROMAN));
        author.addBook(new Book("Les Dépossédés", nextIsbn(isbnSequence), LiteraryGenre.SCIENCE_FICTION, BookType.NOUVELLE));
        return author;
    }

    private Author seedAlanMoore(AtomicInteger isbnSequence) {
        Author author = new Author("Alan Moore");
        author.addBook(new Book("Watchmen", nextIsbn(isbnSequence), LiteraryGenre.THRILLER, BookType.BANDE_DESSINEE));
        author.addBook(new Book("V for Vendetta", nextIsbn(isbnSequence), LiteraryGenre.THRILLER, BookType.BANDE_DESSINEE));
        return author;
    }

    private Author seedAusten(AtomicInteger isbnSequence) {
        Author author = new Author("Jane Austen");
        author.addBook(new Book("Orgueil et Préjugés", nextIsbn(isbnSequence), LiteraryGenre.ROMANCE, BookType.ROMAN));
        author.addBook(new Book("Raison et Sentiments", nextIsbn(isbnSequence), LiteraryGenre.ROMANCE, BookType.ROMAN));
        return author;
    }

    private Author seedSatrapi(AtomicInteger isbnSequence) {
        Author author = new Author("Marjane Satrapi");
        author.addBook(new Book("Persepolis", nextIsbn(isbnSequence), LiteraryGenre.PHILOSOPHY, BookType.BANDE_DESSINEE));
        return author;
    }

    private Author seedGaiman(AtomicInteger isbnSequence) {
        Author author = new Author("Neil Gaiman");
        author.addBook(new Book("American Gods", nextIsbn(isbnSequence), LiteraryGenre.FANTASY, BookType.ROMAN));
        author.addBook(new Book("Coraline", nextIsbn(isbnSequence), LiteraryGenre.FANTASY, BookType.NOUVELLE));
        return author;
    }

    private String nextIsbn(AtomicInteger sequence) {
        return "978" + String.format("%010d", sequence.getAndIncrement());
    }
}
