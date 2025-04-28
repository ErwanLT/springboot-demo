package fr.eletutour.elastic.repository;

import fr.eletutour.elastic.model.Document;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DocumentRepository extends ElasticsearchRepository<Document, String> {
    List<Document> findByTitleContainingOrContentContaining(String title, String content);
}