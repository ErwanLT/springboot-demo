/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of quartz-tutorial.
 *
 * quartz-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * quartz-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with quartz-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.quartz.tutorial.repository;

import fr.eletutour.quartz.tutorial.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour la gestion des utilisateurs.
 * Cette interface fournit :
 * <ul>
 *     <li>Les opérations CRUD de base héritées de JpaRepository</li>
 *     <li>Des méthodes de recherche personnalisées</li>
 *     <li>L'accès aux données utilisateur</li>
 * </ul>
 */
public interface UserRepository extends JpaRepository<User, Long> {

    int deleteByStatus(String status);
}