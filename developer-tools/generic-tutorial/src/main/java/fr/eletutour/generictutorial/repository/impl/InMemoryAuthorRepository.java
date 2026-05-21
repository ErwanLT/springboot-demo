package fr.eletutour.generictutorial.repository.impl;

import fr.eletutour.generictutorial.domain.Author;
import fr.eletutour.generictutorial.repository.AuthorRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryAuthorRepository implements AuthorRepository {
    private final Map<Long, Author> db = new HashMap<>();
    private long seq = 1;

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public List<Author> findAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            author = new Author(seq++, author.getName());
        }
        db.put(author.getId(), author);
        return author;
    }

    @Override
    public void deleteById(Long id) {
        db.remove(id);
    }
}
