/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of drools-tutorial.
 *
 * drools-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * drools-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with drools-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
package fr.eletutour.exception;

/**
 * Exception levée lorsqu'une transaction bancaire échoue.
 * Cette exception est utilisée pour signaler qu'une opération de dépôt ou de retrait
 * n'a pas pu être effectuée pour diverses raisons (solde insuffisant, limite dépassée, etc.).
 */
public class TransactionFailedException extends RuntimeException {
    
    /**
     * Constructeur de l'exception avec un message d'erreur.
     *
     * @param message Le message décrivant la raison de l'échec de la transaction
     */
    public TransactionFailedException(String message) {
        super(message);
    }
}