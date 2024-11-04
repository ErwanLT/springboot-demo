package fr.eletutour.service;

import fr.eletutour.dao.ArticleRepository;
import fr.eletutour.model.Article;
import fr.eletutour.model.Author;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    private final ArticleRepository articleRepository;

    private final AuthorService authorService;

    public ArticleService(ArticleRepository articleRepository, AuthorService authorService) {
        this.articleRepository = articleRepository;
        this.authorService = authorService;
    }

    public List<Article> getArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(String title, String content, Long authorId) {
        Author author = authorService.getAuthorById(authorId)
                                     .orElseThrow(() -> new RuntimeException("Author not found"));
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthor(author);
        return articleRepository.save(article);
    }
}