package fr.eletutour.querydsl.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import fr.eletutour.querydsl.model.Book;
import fr.eletutour.querydsl.model.QBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final JPAQueryFactory queryFactory;

    public BookService(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Page<Book> searchBooks(String title,
                                  String author,
                                  Integer minYear,
                                  Integer maxYear,
                                  LocalDate publishedAfter,
                                  LocalDate publishedBefore,
                                  Double minPrice,
                                  Double maxPrice,
                                  Pageable pageable) {

        QBook book = QBook.book;
        BooleanBuilder builder = new BooleanBuilder();

        // Filtres dynamiques
        if (title != null && !title.isEmpty()) {
            builder.and(book.title.containsIgnoreCase(title));
        }
        if (author != null && !author.isEmpty()) {
            builder.and(book.author.equalsIgnoreCase(author));
        }
        if (minYear != null) {
            builder.and(book.year.goe(minYear));
        }
        if (maxYear != null) {
            builder.and(book.year.loe(maxYear));
        }
        if (publishedAfter != null) {
            builder.and(book.publicationDate.after(publishedAfter));
        }
        if (publishedBefore != null) {
            builder.and(book.publicationDate.before(publishedBefore));
        }
        if (minPrice != null) {
            builder.and(book.price.goe(minPrice));
        }
        if (maxPrice != null) {
            builder.and(book.price.loe(maxPrice));
        }

        // Tri dynamique
        List<OrderSpecifier<?>> orders = new ArrayList<>();
        pageable.getSort().forEach(order -> {
            PathBuilder<Book> path = new PathBuilder<>(Book.class, "book");
            orders.add(new OrderSpecifier(
                    order.isAscending() ? Order.ASC : Order.DESC,
                    path.get(order.getProperty(), Comparable.class)
            ));
        });

        // Résultats paginés
        List<Book> results = queryFactory.selectFrom(book)
                .where(builder)
                .orderBy(orders.toArray(new OrderSpecifier[0]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(book)
                .where(builder)
                .fetchCount();

        return new PageImpl<>(results, pageable, total);

    }
}
