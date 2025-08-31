package fr.eletutour.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration de la sécurité de l'application.
 * Cette classe définit :
 * <ul>
 *     <li>La chaîne de filtres de sécurité</li>
 *     <li>La gestion des utilisateurs en mémoire</li>
 *     <li>L'encodeur de mots de passe</li>
 * </ul>
 */
@Configuration
public class SecurityConfig {

    /**
     * Configure la chaîne de filtres de sécurité.
     * Cette configuration :
     * <ul>
     *     <li>Désactive la protection CSRF</li>
     *     <li>Autorise l'accès public à /goodbye</li>
     *     <li>Requiert une authentification pour tous les autres endpoints</li>
     *     <li>Configure l'authentification basique</li>
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
                    auth.requestMatchers("/goodbye").permitAll()
                    .anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
                .build();
    }

    /**
     * Configure le service de gestion des utilisateurs.
     * Crée deux utilisateurs en mémoire :
     * <ul>
     *     <li>user/password avec le rôle USER</li>
     *     <li>admin/admin123 avec le rôle ADMIN</li>
     * </ul>
     *
     * @param passwordEncoder L'encodeur de mots de passe à utiliser
     * @return Le service de gestion des utilisateurs
     */
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        var user1 = User.builder()
            .username("user")
            .password(passwordEncoder.encode("password"))
            .roles("USER")
            .build();

        var user2 = User.builder()
            .username("admin")
            .password(passwordEncoder.encode("admin123"))
            .roles("ADMIN")
            .build();

        return new InMemoryUserDetailsManager(user1, user2);
    }

    /**
     * Configure l'encodeur de mots de passe.
     * Utilise BCrypt pour le hachage sécurisé des mots de passe.
     *
     * @return L'encodeur de mots de passe BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
