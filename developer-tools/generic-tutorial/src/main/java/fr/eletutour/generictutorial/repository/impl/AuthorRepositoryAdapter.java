package fr.eletutour.generictutorial.repository.impl;

import fr.eletutour.generictutorial.domain.Author;
import fr.eletutour.generictutorial.repository.AuthorJpaRepository;
import fr.eletutour.generictutorial.repository.AuthorRepository;
import fr.eletutour.generictutorial.repository.dao.AuthorEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepositoryAdapter implements AuthorRepository {

    private final AuthorJpaRepository jpa;

    public AuthorRepositoryAdapter(AuthorJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Optional<Author> findById(Long id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Author> findAll() {
        return jpa.findAll().stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public Author save(Author author) {
        AuthorEntity entity = toEntity(author);
        return toDomain(jpa.save(entity));
    }

    @Override
    public void deleteById(Long id) {
        jpa.deleteById(id);
    }

    private AuthorEntity toEntity(Author author) {
        return new AuthorEntity(author.getId(), author.getName());
    }

    private Author toDomain(AuthorEntity entity) {
        return new Author(entity.getId(), entity.getName());
    }
}
