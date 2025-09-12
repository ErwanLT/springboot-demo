package fr.eletutour.mapper;

import fr.eletutour.dao.entity.AuthorEntity;
import fr.eletutour.model.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toAuthor(AuthorEntity authorEntity);

    AuthorEntity toAuthorEntity(Author author);

    List<Author> toAuthors(List<AuthorEntity> authorEntities);
}
