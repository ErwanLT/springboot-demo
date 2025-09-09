/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of archunit-tutorial.
 *
 * archunit-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * archunit-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with archunit-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.archunit.repository;

import fr.eletutour.archunit.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository pour l'accès aux données des auteurs.
 * Ce repository fournit :
 * <ul>
 *     <li>Les opérations CRUD de base pour les auteurs</li>
 *     <li>L'accès à la base de données via JPA</li>
 *     <li>La persistance des entités Author</li>
 * </ul>
 */
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
