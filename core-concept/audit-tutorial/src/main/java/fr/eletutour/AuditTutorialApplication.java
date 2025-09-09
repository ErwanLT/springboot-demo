/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of audit-tutorial.
 *
 * audit-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * audit-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with audit-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Application principale de démonstration pour l'authentification basique Spring Security.
 * Cette application illustre :
 * <ul>
 *     <li>La configuration de l'authentification basique</li>
 *     <li>La gestion des utilisateurs en mémoire</li>
 *     <li>La sécurisation des endpoints REST</li>
 * </ul>
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class AuditTutorialApplication {
    /**
     * Point d'entrée principal de l'application.
     * Démarre l'application Spring Boot avec la configuration de sécurité.
     *
     * @param args Les arguments de ligne de commande
     */
    public static void main(String[] args) {
        SpringApplication.run(AuditTutorialApplication.class, args);
    }
}