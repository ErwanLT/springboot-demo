/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of jwt-rbac-tutorial.
 *
 * jwt-rbac-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jwt-rbac-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jwt-rbac-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
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
 * Cette classe définit :
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
     * Ce service charge les utilisateurs depuis la base de données.
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
     * Ce gestionnaire est utilisé pour l'authentification des utilisateurs.
     *
     * @param config La configuration d'authentification
     * @return Le gestionnaire d'authentification
     * @throws Exception En cas d'erreur lors de la configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Configure le fournisseur d'authentification.
     * Ce fournisseur utilise :
     * <ul>
     *     <li>Le service de gestion des utilisateurs</li>
     *     <li>L'encodeur de mots de passe</li>
     * </ul>
     *
     * @return Le fournisseur d'authentification
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
}