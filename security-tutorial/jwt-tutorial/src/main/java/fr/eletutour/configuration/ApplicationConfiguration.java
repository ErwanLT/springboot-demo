package fr.eletutour.configuration;

import fr.eletutour.dao.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration générale de l'application.
 * Cette classe configure :
 * <ul>
 *     <li>Le service de gestion des utilisateurs</li>
 *     <li>L'encodeur de mots de passe</li>
 *     <li>Le gestionnaire d'authentification</li>
 *     <li>Le fournisseur d'authentification</li>
 * </ul>
 */
@Configuration
public class ApplicationConfiguration {
    private final UserRepository userRepository;

    /**
     * Constructeur de la configuration de l'application.
     *
     * @param userRepository Le repository de gestion des utilisateurs
     */
    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Configure le service de gestion des utilisateurs.
     * Ce service :
     * <ul>
     *     <li>Recherche les utilisateurs par email</li>
     *     <li>Lève une exception si l'utilisateur n'est pas trouvé</li>
     * </ul>
     *
     * @return Le service de gestion des utilisateurs
     */
    @Bean
    UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    /**
     * Configure l'encodeur de mots de passe.
     * Utilise BCrypt pour le hachage sécurisé des mots de passe.
     *
     * @return L'encodeur de mots de passe BCrypt
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configure le gestionnaire d'authentification.
     * Ce gestionnaire :
     * <ul>
     *     <li>Gère le processus d'authentification</li>
     *     <li>Valide les identifiants des utilisateurs</li>
     * </ul>
     *
     * @param config La configuration d'authentification
     * @return Le gestionnaire d'authentification
     * @throws Exception Si une erreur survient lors de la configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configure le fournisseur d'authentification.
     * Ce fournisseur :
     * <ul>
     *     <li>Utilise le service de gestion des utilisateurs</li>
     *     <li>Utilise l'encodeur de mots de passe</li>
     *     <li>Gère l'authentification via la base de données</li>
     * </ul>
     *
     * @return Le fournisseur d'authentification
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}