package fr.eletutour.utils;

import org.springframework.hateoas.Link;

public interface LinkBuilder {
    Link authorSelfLink(Long authorId);
    Link authorsListLink();
    Link authorDeleteLink(Long authorId);
    Link articleSelfLink(Long articleId);
    Link articlesListLink();
    Link articleDeleteLink(Long articleId);
}