package fr.eletutour.elastic.service;

import fr.eletutour.elastic.model.Document;
import fr.eletutour.elastic.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    public List<Document> searchDocuments(String query) {
        return documentRepository.findByTitleContainingOrContentContaining(query, query);
    }

    public void deleteDocument(String id) {
        documentRepository.deleteById(id);
    }
}