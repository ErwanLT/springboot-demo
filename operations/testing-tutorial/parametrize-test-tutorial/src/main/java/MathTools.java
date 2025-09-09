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
/**
 * Classe utilitaire pour les opérations mathématiques.
 * Cette classe fournit :
 * <ul>
 *     <li>Des méthodes de calcul</li>
 *     <li>Des vérifications de propriétés mathématiques</li>
 *     <li>Des utilitaires mathématiques</li>
 * </ul>
 */
public class MathTools {

    /**
     * Vérifie si un nombre est pair.
     *
     * @param i Le nombre à vérifier
     * @return 0 si le nombre est pair, 1 sinon
     */
    public static int isEven(int i){
        return i % 2;
    }
}