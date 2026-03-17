package fr.eletutour.configuration;

import fr.eletutour.client.BookHttpExchangeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
@ConditionalOnProperty(name = "books.client", havingValue = "http-exchange")
public class HttpExchangeClientConfiguration {

    @Bean
    public BookHttpExchangeClient bookHttpExchangeClient(
            @Value("${bookManagement.url:http://localhost:8092}") String bookManagementUrl
    ) {
        RestClient restClient = RestClient.builder()
                .baseUrl(bookManagementUrl)
                .build();

        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(RestClientAdapter.create(restClient))
                .build();

        return factory.createClient(BookHttpExchangeClient.class);
    }
}
