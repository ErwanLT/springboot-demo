package fr.eletutour.utils;

import fr.eletutour.exception.ArticleNotFoundException;
import fr.eletutour.exception.AuthorNotFoundException;
import org.springframework.hateoas.Link;

public interface LinkBuilder {
    Link authorSelfLink(Long authorId);
    Link authorsListLink();
    Link authorDeleteLink(Long authorId); // Nouveau lien pour DELETE
    Link articleSelfLink(Long articleId);
    Link articlesListLink();
    Link articleDeleteLink(Long articleId); // Nouveau lien pour DELETE
}