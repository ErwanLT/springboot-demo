package fr.eletutour.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration de la sécurité de l'application.
 * Cette classe définit :
 * <ul>
 *     <li>La chaîne de filtres de sécurité</li>
 *     <li>La configuration des autorisations</li>
 *     <li>La gestion des sessions</li>
 *     <li>L'intégration du filtre JWT</li>
 * </ul>
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Constructeur de la configuration de sécurité.
     *
     * @param jwtAuthenticationFilter Le filtre d'authentification JWT
     * @param authenticationProvider Le fournisseur d'authentification
     */
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    /**
     * Configure la chaîne de filtres de sécurité.
     * Cette configuration :
     * <ul>
     *     <li>Désactive la protection CSRF</li>
     *     <li>Autorise l'accès public aux endpoints /auth/**</li>
     *     <li>Requiert une authentification pour tous les autres endpoints</li>
     *     <li>Configure une session sans état (stateless)</li>
     *     <li>Intègre le filtre JWT avant l'authentification par nom d'utilisateur/mot de passe</li>
     * </ul>
     *
     * @param http L'objet HttpSecurity à configurer
     * @return La chaîne de filtres de sécurité configurée
     * @throws Exception En cas d'erreur lors de la configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**").permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
