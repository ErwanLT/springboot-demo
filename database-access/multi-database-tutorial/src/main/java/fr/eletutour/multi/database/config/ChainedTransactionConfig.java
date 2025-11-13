package fr.eletutour.multi.database.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ChainedTransactionConfig {

    @Bean(name = "chainedTransactionManager")
    public PlatformTransactionManager chainedTransactionManager(
            @Qualifier("userTransactionManager") PlatformTransactionManager userTxManager,
            @Qualifier("orderTransactionManager") PlatformTransactionManager orderTxManager) {

        return new ChainedTransactionManager(userTxManager, orderTxManager);
    }
}
