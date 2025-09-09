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
package fr.eletutour.service;

import fr.eletutour.dao.entities.User;
import fr.eletutour.dao.repository.UserRepository;
import fr.eletutour.model.LoginUserDto;
import fr.eletutour.model.RegisterUserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service de gestion de l'authentification.
 * Ce service gère :
 * <ul>
 *     <li>L'inscription des nouveaux utilisateurs</li>
 *     <li>L'authentification des utilisateurs</li>
 *     <li>Le hachage des mots de passe</li>
 * </ul>
 */
@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    /**
     * Constructeur du service d'authentification.
     *
     * @param userRepository Le repository de gestion des utilisateurs
     * @param passwordEncoder L'encodeur de mots de passe
     * @param authenticationManager Le gestionnaire d'authentification
     */
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Inscrit un nouvel utilisateur.
     * Cette méthode :
     * <ul>
     *     <li>Crée un nouvel utilisateur</li>
     *     <li>Encode le mot de passe</li>
     *     <li>Sauvegarde l'utilisateur</li>
     * </ul>
     *
     * @param input Les informations d'inscription
     * @return L'utilisateur créé
     */
    public User signup(RegisterUserDto input) {

        User user = new User()
                .setFullName(input.fullName())
                .setEmail(input.email())
                .setPassword(passwordEncoder.encode(input.password()));

        return userRepository.save(user);
    }

    /**
     * Authentifie un utilisateur.
     * Cette méthode :
     * <ul>
     *     <li>Vérifie les identifiants de connexion</li>
     *     <li>Authentifie l'utilisateur via le gestionnaire d'authentification</li>
     *     <li>Récupère les détails de l'utilisateur</li>
     * </ul>
     *
     * @param input Les informations de connexion
     * @return Les détails de l'utilisateur authentifié
     * @throws RuntimeException Si l'authentification échoue ou si l'utilisateur n'est pas trouvé
     */
    public UserDetails authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        return userRepository.findByEmail(input.email())
                .orElseThrow();
    }
}
