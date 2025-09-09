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
 * Exception levée lorsqu'un compte bancaire n'est pas trouvé dans le système.
 * Cette exception est utilisée pour signaler qu'une opération a été tentée
 * sur un compte qui n'existe pas.
 */
public class AccountNotFoundException extends RuntimeException {
    
    /**
     * Constructeur de l'exception avec un message d'erreur.
     *
     * @param message Le message décrivant l'erreur
     */
    public AccountNotFoundException(String message) {
        super(message);
    }
}