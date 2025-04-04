package fr.eletutour.service;

import fr.eletutour.dao.ArticleRepository;
import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import fr.eletutour.model.Article;
import fr.eletutour.model.Author;
import fr.eletutour.utils.LinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    private static final String ARTICLE_NOT_FOUND_MESSAGE = "Article non trouvé pour l'id : ";
    private final ArticleRepository articleRepository;
    private final AuthorService authorService;
    private final LinkBuilder linkBuilder;

    public ArticleService(ArticleRepository articleRepository,
                          AuthorService authorService,
                          LinkBuilder linkBuilder) {
        this.articleRepository = articleRepository;
        this.authorService = authorService;
        this.linkBuilder = linkBuilder;
    }

    public List<Article> getArticles() {
        List<Article> articles = articleRepository.findAll();
        return articles.stream()
                .map(this::addArticleLinks)
                .collect(Collectors.toList());
    }

    public Article getArticleById(Long id) throws ArticleNotFoundException {
        var article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(ARTICLE_NOT_FOUND_MESSAGE + id));
        return addArticleLinks(article);
    }

    public void createArticle(String title, String content, Long authorId) throws AuthorNotFoundException {
        Author author = authorService.getAuthorById(authorId);
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setAuthor(author);
        articleRepository.save(article);
    }

    private Article addArticleLinks(Article article) {
        article.add(linkBuilder.articleSelfLink(article.getId()));
        article.add(linkBuilder.articlesListLink());
        article.add(linkBuilder.authorSelfLink(article.getAuthor().getId()));
        article.add(linkBuilder.articleDeleteLink(article.getId()));
        return article;
    }

    public void deleteArticle(Long id) {
        var article = articleRepository.findById(id);
        if (article.isPresent()) {
            articleRepository.delete(article.get());
        } else {
            throw new ArticleNotFoundException(ARTICLE_NOT_FOUND_MESSAGE + id);
        }
    }
}