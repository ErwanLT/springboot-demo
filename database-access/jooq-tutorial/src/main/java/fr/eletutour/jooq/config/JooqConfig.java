package fr.eletutour.jooq.config;

import fr.eletutour.jooq.model.tables.daos.BookDao;
import org.jooq.DSLContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JooqConfig {

    @Bean
    public BookDao bookDao(DSLContext dslContext) {
        return new BookDao(dslContext.configuration());
    }
}
