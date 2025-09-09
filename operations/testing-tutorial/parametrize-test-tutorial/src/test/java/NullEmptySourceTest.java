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
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Classe de test démontrant l'utilisation des annotations @NullSource, @EmptySource et @NullAndEmptySource.
 * Cette classe illustre :
 * <ul>
 *     <li>L'utilisation de @NullSource pour tester les valeurs nulles</li>
 *     <li>L'utilisation de @EmptySource pour tester les chaînes vides</li>
 *     <li>L'utilisation de @NullAndEmptySource pour tester les valeurs nulles et vides</li>
 *     <li>La combinaison de @NullAndEmptySource avec @ValueSource</li>
 * </ul>
 */
public class NullEmptySourceTest {
    /**
     * Test vérifiant le comportement avec une valeur nulle.
     * Utilise @NullSource pour fournir une valeur nulle.
     *
     * @param value La valeur à tester (sera null)
     */
    @ParameterizedTest
    @NullSource
    void checkNull(String value) {
        assertTrue(StringTools.isEmpty(value));
    }

    /**
     * Test vérifiant le comportement avec une chaîne vide.
     * Utilise @EmptySource pour fournir une chaîne vide.
     *
     * @param value La valeur à tester (sera une chaîne vide)
     */
    @ParameterizedTest
    @EmptySource
    void checkEmpty(String value) {
        assertTrue(StringTools.isBlank(value));
    }

    /**
     * Test vérifiant le comportement avec des valeurs nulles et vides.
     * Utilise @NullAndEmptySource pour fournir à la fois des valeurs nulles et des chaînes vides.
     *
     * @param value La valeur à tester (sera null ou une chaîne vide)
     */
    @ParameterizedTest
    @NullAndEmptySource
    void checkNullAndEmpty(String value) {
        assertTrue(StringTools.isEmpty(value));
    }

    /**
     * Test vérifiant le comportement avec des valeurs nulles, vides et des espaces.
     * Combine @NullAndEmptySource avec @ValueSource pour tester différents cas de chaînes vides.
     *
     * @param value La valeur à tester (sera null, une chaîne vide, ou une chaîne contenant uniquement des espaces)
     */
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { " ", "   " })
    void checkNullEmptyAndBlank(String value) {
        assertTrue(StringTools.isBlank(value));
    }
}