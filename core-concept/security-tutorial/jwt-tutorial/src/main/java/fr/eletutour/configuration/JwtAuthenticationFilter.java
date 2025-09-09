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

import fr.eletutour.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * Filtre d'authentification JWT.
 * Ce filtre :
 * <ul>
 *     <li>Intercepte chaque requête HTTP</li>
 *     <li>Vérifie la présence d'un token JWT valide</li>
 *     <li>Authentifie l'utilisateur si le token est valide</li>
 *     <li>Gère les erreurs d'authentification</li>
 * </ul>
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Constructeur du filtre d'authentification JWT.
     *
     * @param jwtService Le service de gestion des JWT
     * @param userDetailsService Le service de gestion des utilisateurs
     * @param handlerExceptionResolver Le gestionnaire d'exceptions
     */
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService, HandlerExceptionResolver handlerExceptionResolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.handlerExceptionResolver = handlerExceptionResolver;
    }

    /**
     * Traite chaque requête HTTP pour l'authentification JWT.
     * Cette méthode :
     * <ul>
     *     <li>Vérifie l'en-tête Authorization</li>
     *     <li>Extrait le token JWT</li>
     *     <li>Valide le token</li>
     *     <li>Authentifie l'utilisateur si le token est valide</li>
     *     <li>Gère les erreurs d'authentification</li>
     * </ul>
     *
     * @param request La requête HTTP
     * @param response La réponse HTTP
     * @param filterChain La chaîne de filtres
     * @throws ServletException Si une erreur survient lors du traitement de la requête
     * @throws IOException Si une erreur d'entrée/sortie survient
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            final String jwt = authHeader.substring(7);
            final String userEmail = jwtService.extractUsername(jwt);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (userEmail != null && authentication == null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            handlerExceptionResolver.resolveException(request, response, null, exception);
        }
    }
}
