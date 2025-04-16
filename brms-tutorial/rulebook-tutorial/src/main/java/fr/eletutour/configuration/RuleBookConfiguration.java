package fr.eletutour.configuration;

import com.deliveredtechnologies.rulebook.spring.SpringAwareRuleBookRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleBookConfiguration {

    @Bean
    public SpringAwareRuleBookRunner accountCreationRuleBook() {
        return new SpringAwareRuleBookRunner("fr.eletutour.rules.account.creation");
    }

    @Bean
    public SpringAwareRuleBookRunner withdrawalRuleBook() {
        return new SpringAwareRuleBookRunner("fr.eletutour.rules.account.operation.withdraw");
    }

    @Bean
    public SpringAwareRuleBookRunner depositRuleBook() {
        return new SpringAwareRuleBookRunner("fr.eletutour.rules.account.operation.deposit");
    }
}