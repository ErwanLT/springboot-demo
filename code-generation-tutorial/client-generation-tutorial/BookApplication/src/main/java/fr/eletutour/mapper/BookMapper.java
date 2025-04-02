package fr.eletutour.mapper;

import fr.eletutour.dao.entity.BookEntity;
import fr.eletutour.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toBook(BookEntity bookEntity);
    BookEntity toBookEntity(Book book);
    List<Book> toBooks(List<BookEntity> bookEntities);
}
