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