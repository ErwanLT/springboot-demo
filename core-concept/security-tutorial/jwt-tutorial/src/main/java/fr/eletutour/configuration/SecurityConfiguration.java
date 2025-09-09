/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of jwt-tutorial.
 *
 * jwt-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * jwt-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with jwt-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration de la sécurité de l'application.
 * Cette classe définit les règles de sécurité, les filtres d'authentification
 * et la gestion des sessions pour l'application.
 */
@Configuration
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
     * Configure la chaîne de filtres de sécurité pour l'application.
     * Cette configuration :
     * - Désactive la protection CSRF
     * - Permet l'accès public aux endpoints d'authentification
     * - Requiert l'authentification pour tous les autres endpoints
     * - Configure la gestion des sessions en mode stateless
     * - Ajoute le filtre JWT avant le filtre d'authentification par défaut
     *
     * @param http L'objet HttpSecurity à configurer
     * @return La chaîne de filtres de sécurité configurée
     * @throws Exception Si une erreur survient lors de la configuration
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
