package fr.eletutour.repository;

import fr.eletutour.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interface de repository pour la gestion des transactions bancaires.
 * Cette interface étend JpaRepository pour fournir les opérations CRUD
 * de base sur les entités Transaction.
 */
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}