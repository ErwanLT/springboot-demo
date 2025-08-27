package fr.eletutour.ldap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.ldap.server.UnboundIdContainer;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UnboundIdContainer ldapContainer() {
        UnboundIdContainer container = new UnboundIdContainer("dc=example,dc=com", "classpath:users.ldif");
        container.setPort(0); // Use a random available port
        return container;
    }

    @Bean
    public LdapContextSource contextSource(UnboundIdContainer container) {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost:" + container.getPort());
        contextSource.setUserDn("cn=admin,dc=example,dc=com");
        contextSource.setPassword("admin-password");
        contextSource.setBase("dc=example,dc=com");
        contextSource.afterPropertiesSet();
        return contextSource;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(LdapContextSource contextSource) {
        LdapBindAuthenticationManagerFactory factory = new LdapBindAuthenticationManagerFactory(contextSource);
        factory.setUserSearchFilter("(uid={0})");
        factory.setUserSearchBase("ou=users");

        DefaultLdapAuthoritiesPopulator authoritiesPopulator = new DefaultLdapAuthoritiesPopulator(contextSource, "ou=groups");
        authoritiesPopulator.setGroupSearchFilter("uniqueMember={0}");
        factory.setLdapAuthoritiesPopulator(authoritiesPopulator);

        return factory.createAuthenticationManager();
    }
}