package fr.eletutour.repository;

import fr.eletutour.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de repository pour la gestion des comptes bancaires.
 * Cette interface étend JpaRepository pour fournir les opérations CRUD
 * de base sur les entités Account.
 */
public interface AccountRepository extends JpaRepository<Account, String> {
}