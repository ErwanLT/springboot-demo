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
package fr.eletutour.controller;

import fr.eletutour.dao.entities.User;
import fr.eletutour.model.RegisterUserDto;
import fr.eletutour.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur de gestion des utilisateurs.
 * Ce contrôleur expose les endpoints :
 * <ul>
 *     <li>/users/me : Récupération des informations de l'utilisateur authentifié</li>
 *     <li>/users : Liste de tous les utilisateurs (ADMIN, SUPER_ADMIN)</li>
 *     <li>/users (POST) : Création d'un administrateur (SUPER_ADMIN)</li>
 * </ul>
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructeur du contrôleur des utilisateurs.
     *
     * @param userService Le service de gestion des utilisateurs
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Endpoint pour récupérer les informations de l'utilisateur authentifié.
     * Cette méthode :
     * <ul>
     *     <li>Nécessite une authentification JWT valide</li>
     *     <li>Récupère l'utilisateur depuis le contexte de sécurité</li>
     *     <li>Retourne les informations de l'utilisateur</li>
     * </ul>
     *
     * @return L'utilisateur authentifié
     */
    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public User authenticatedUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    /**
     * Endpoint pour récupérer la liste de tous les utilisateurs.
     * Cette méthode :
     * <ul>
     *     <li>Nécessite le rôle ADMIN ou SUPER_ADMIN</li>
     *     <li>Récupère la liste complète des utilisateurs</li>
     *     <li>Retourne la liste des utilisateurs</li>
     * </ul>
     *
     * @return La liste des utilisateurs
     */
    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN','SUPER_ADMIN')")
    public List<User> allUsers() {
        return userService.allUsers();
    }

    /**
     * Endpoint pour créer un nouvel administrateur.
     * Cette méthode :
     * <ul>
     *     <li>Nécessite le rôle SUPER_ADMIN</li>
     *     <li>Crée un nouvel utilisateur avec le rôle ADMIN</li>
     *     <li>Retourne l'administrateur créé</li>
     * </ul>
     *
     * @param registerUserDto Les informations de l'administrateur à créer
     * @return L'administrateur créé
     */
    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public User createAdministrator(@RequestBody RegisterUserDto registerUserDto) {
        return userService.createAdministrator(registerUserDto);
    }
}
