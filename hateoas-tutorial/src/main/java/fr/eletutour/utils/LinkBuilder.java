package fr.eletutour.utils;

import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import org.springframework.hateoas.Link;

public interface LinkBuilder {
    Link authorSelfLink(Long authorId) throws AuthorNotFoundException;
    Link authorsListLink() throws AuthorNotFoundException, ArticleNotFoundException;
    Link articleSelfLink(Long articleId) throws ArticleNotFoundException;
    Link articlesListLink();
}