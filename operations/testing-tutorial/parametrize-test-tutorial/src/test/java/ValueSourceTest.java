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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de test démontrant l'utilisation de @ValueSource pour les tests paramétrés.
 * Cette classe illustre :
 * <ul>
 *     <li>L'utilisation de @ValueSource avec des valeurs primitives</li>
 *     <li>Les tests paramétrés avec des entiers</li>
 *     <li>Les tests paramétrés avec des chaînes de caractères</li>
 * </ul>
 */
public class ValueSourceTest {
    /**
     * Test vérifiant si les nombres fournis sont pairs.
     * Utilise @ValueSource pour fournir une liste de nombres à tester.
     *
     * @param number Le nombre à tester
     */
    @ParameterizedTest
    @ValueSource(ints = { 2, 4 })
    void checkPairNumber(int number) {
        assertEquals(0, MathTools.isEven(number),
                "Le nombre fourni n'est pas un nombre pair");
    }

    /**
     * Test vérifiant si les chaînes fournies sont alphanumériques.
     * Utilise @ValueSource pour fournir une liste de chaînes à tester.
     *
     * @param word La chaîne à tester
     */
    @ParameterizedTest
    @ValueSource(strings = { "a1", "b2" })
    void checkAlphanumeric(String word) {
        assertTrue(StringTools.isAlphanumeric(word),
                "Le mot fourni n'est pas alphanumérique");
    }
}