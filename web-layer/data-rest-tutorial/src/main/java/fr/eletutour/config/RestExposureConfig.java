package fr.eletutour.config;

import fr.eletutour.model.Author;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class RestExposureConfig implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        config.exposeIdsFor(Author.class);

        config.getExposureConfiguration()
                .forDomainType(Author.class)
                .withItemExposure((metadata, httpMethods) -> httpMethods
                        .disable(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE))
                .withCollectionExposure((metadata, httpMethods) -> httpMethods
                        .disable(HttpMethod.POST, HttpMethod.PUT, HttpMethod.PATCH, HttpMethod.DELETE));
    }
}
