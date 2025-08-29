package fr.eletutour.repository;


import fr.eletutour.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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
    /**
     * Recherche les utilisateurs par leur statut.
     *
     * @param active Le statut des utilisateurs à rechercher
     * @return Une liste d'utilisateurs ayant le statut spécifié
     */
    List<User> findByStatus(String active);
}