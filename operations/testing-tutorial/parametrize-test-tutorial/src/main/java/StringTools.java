/*
 * Copyright (C) 2025 LE TUTOUR Erwan
 *
 * This file is part of parametrize-test-tutorial.
 *
 * parametrize-test-tutorial is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * parametrize-test-tutorial is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with parametrize-test-tutorial.  If not, see <http://www.gnu.org/licenses/>.
 */
import org.apache.commons.lang3.StringUtils;

/**
 * Classe utilitaire pour la manipulation des chaînes de caractères.
 * Cette classe fournit :
 * <ul>
 *     <li>Des méthodes de validation de chaînes</li>
 *     <li>Des vérifications de format</li>
 *     <li>Des utilitaires de test de chaînes</li>
 * </ul>
 */
public class StringTools {

    /**
     * Vérifie si une chaîne est alphanumérique.
     *
     * @param s La chaîne à vérifier
     * @return true si la chaîne ne contient que des lettres et des chiffres, false sinon
     */
    public static boolean isAlphanumeric(String s){
        return StringUtils.isAlphanumeric(s);
    }

    /**
     * Vérifie si une chaîne est vide ou ne contient que des espaces.
     *
     * @param s La chaîne à vérifier
     * @return true si la chaîne est vide ou ne contient que des espaces, false sinon
     */
    public static boolean isBlank(String s){
        return StringUtils.isBlank(s);
    }

    /**
     * Vérifie si une chaîne est vide.
     *
     * @param s La chaîne à vérifier
     * @return true si la chaîne est vide, false sinon
     */
    public static boolean isEmpty(String s){
        return StringUtils.isEmpty(s);
    }
}