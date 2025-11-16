package fr.eletutour.jooq.repository;

import fr.eletutour.jooq.model.tables.daos.BookDao;
import fr.eletutour.jooq.model.tables.pojos.Book;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static fr.eletutour.jooq.model.Tables.BOOK;

@Repository
public class BookRepository {

    private final BookDao bookDao;
    private final DSLContext dsl;

    public BookRepository(BookDao bookDao, DSLContext dsl) {
        this.bookDao = bookDao;
        this.dsl = dsl;
    }

    public List<Book> findAll() {
        return bookDao.findAll();
    }

    public Book findById(int id) {
        return bookDao.findById(id);
    }

    public void save(Book book) {
        bookDao.insert(book);
    }

    public List<Book> findByAuthor(String author) {
        return dsl.selectFrom(BOOK)
                .where(BOOK.AUTHOR.likeIgnoreCase("%" + author + "%"))
                .fetchInto(Book.class);
    }
}
